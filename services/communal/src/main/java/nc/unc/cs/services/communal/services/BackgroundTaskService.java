package nc.unc.cs.services.communal.services;

import java.util.Date;
import java.util.List;
import feign.FeignException;
import nc.unc.cs.services.common.clients.logging.LogEntry;
import nc.unc.cs.services.common.clients.logging.LoggingService;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.entities.UtilityBill;
import nc.unc.cs.services.communal.exceptions.PropertyNotFoundException;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxValueRepository;
import nc.unc.cs.services.communal.repositories.UtilityBillRepository;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BackgroundTaskService {

  /** Логгер. */
  private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundTaskService.class);

  /** Налоговый процент от стоимости платежа. */
  @Value("${communal.property.tax-percent}")
  private Integer taxPercent;

  /** Номер сервиса. */
  @Value("${communal.service-id.property-tax}")
  private Long serviceId;

  /** Налоговый период. */
  @Value("${communal.background.job.tax-period}")
  private Integer taxPeriod;

  /** Длина выборки налогов. */
  @Value("${communal.background.job.property-tax.page-size}")
  private Integer propertyTaxPageSize;

  /** Длина выборки квитанций. */
  @Value("${communal.background.job.utility-bill.page-size}")
  private Integer utilityBillPageSize;

  private final PropertyRepository propertyRepository;
  private final PropertyTaxRepository propertyTaxRepository;
  private final PropertyTaxValueRepository propertyTaxValueRepository;
  private final UtilityBillRepository utilityBillRepository;
  private final BankIntegrationService bankIntegrationService;
  /** Сервис логгирования. */
  private final LoggingService logging;

  public BackgroundTaskService(
      final PropertyRepository propertyRepository,
      final PropertyTaxRepository propertyTaxRepository,
      final PropertyTaxValueRepository propertyTaxValueRepository,
      final UtilityBillRepository utilityBillRepository,
      final BankIntegrationService bankIntegrationService,
      final LoggingService logging
  ) {
    this.propertyRepository = propertyRepository;
    this.propertyTaxRepository = propertyTaxRepository;
    this.propertyTaxValueRepository = propertyTaxValueRepository;
    this.utilityBillRepository = utilityBillRepository;
    this.bankIntegrationService = bankIntegrationService;
    this.logging = logging;
  }

  // 1. Извлекается первая попавшаяся недвижимость, с *доступной датой для налогооблажения;
  // 2. Берётся область полученного объекта;
  // 3. Происходит выборка недвижимости (20-50 шт) с *доступной датой и идентичной областью;
  // 4. Происходит налоговый расчёт и сохранение квитанций и новой налоговой даты в таблице
  // Property;
  // 5. Переход к пункту 1;
  // ! В случае отсутствия прейскуранта для конвретной области,
  // расчёт происходит по стандартному прейскуранту
  // *доступная дата - дата превышающая налоговый период
  @Scheduled(fixedRateString = "${communal.background.job.timeout}")
  public void reportDate() {
    final Date beforeDate = DateUtils.addDays(new Date(), -taxPeriod);
    LOGGER.info("Before Date: {}", beforeDate);
    try {
      final Property property = this.getPropertyByTaxDateBefore(beforeDate);
      final List<Property> properties =
          this.propertyRepository.findFirst3ByPropertyTaxDateBeforeAndRegion(
              beforeDate, property.getRegion());
      final PropertyTaxValue priceList =
          this.propertyTaxValueRepository.findPropertyTaxValueByRegion(property.getRegion());
      final PropertyTaxValue propertyTaxValue;
      if (priceList == null) {
        this.logging.addLog(
            LogEntry.builder()
                .service("Communal")
                .created(new Date())
                .message(
                    String.format(
                        "Property tax value not found for region = %s. \n"
                            + "The default price list will be used.",
                        property.getRegion()))
                .build());
        propertyTaxValue =
            PropertyTaxValue.builder()
                .region("default")
                .pricePerSquareMeter(50000)
                .cadastralValue(15)
                .build();
      } else {
        propertyTaxValue = priceList;
      }
      properties.forEach(p -> this.createPropertyTax(p, propertyTaxValue));
    } catch (final FeignException fe) {
      LOGGER.error("Logging service unavailable!", fe);
    }
  }

  public Property getPropertyByTaxDateBefore(final Date beforeDate)
      throws FeignException, PropertyNotFoundException {
    final Property property = this.propertyRepository.findFirstByPropertyTaxDateBefore(beforeDate);
    if (property == null) {
      this.logging.addLog(
          LogEntry.builder()
              .service("Communal")
              .created(new Date())
              .message("No tax creation is required.")
              .build());
      throw new PropertyNotFoundException(beforeDate);
    } else {
      return property;
    }
  }

  private void createPropertyTax(final Property property, final PropertyTaxValue propertyTaxValue)
      throws FeignException {
    try {
      final Integer amount =
          this.calculatePropertyTaxAmount(
              Double.valueOf(property.getApartmentSize()),
              Double.valueOf(propertyTaxValue.getPricePerSquareMeter()),
              Double.valueOf(propertyTaxValue.getCadastralValue()));
      final PropertyTax propertyTax =
          PropertyTax.builder()
              .propertyId(property.getPropertyId())
              .citizenId(property.getCitizenId())
              .taxAmount(amount)
              .paymentRequestId(
                  this.bankIntegrationService.bankRequest(
                      serviceId, property.getCitizenId(), amount, taxPercent))
              .build();
      this.propertyTaxRepository.save(propertyTax);
      property.setPropertyTaxDate(new Date());
      this.propertyRepository.save(property);
      LOGGER.info("PropertyTax successfully created");
      this.addLogg(propertyTaxValue);
    } catch (final FeignException fe) {
      LOGGER.error("Bank service unavailable!", fe);
      this.logging.addLog(
          LogEntry.builder()
              .service("Communal")
              .created(new Date())
              .message(
                  String.format(
                      "Bank service unavailable!\n"
                          + "Failed to create tax for property with ID = %d",
                      property.getPropertyId()))
              .build());
    }
  }

  public void addLogg(final PropertyTaxValue propertyTaxValue) {
    try {
      this.logging.addLog(
          LogEntry.builder()
              .service("Communal")
              .created(new Date())
              .message(
                  String.format("Property tax has been created! %s", propertyTaxValue.toString()))
              .build());
    } catch (final FeignException fe) {
      LOGGER.error("Logging service unavailable!", fe);
    }
  }

  public Integer calculatePropertyTaxAmount(
      final Double apartmentSize, final Double pricePerSquareMeter, final Double cadastralValue) {
    return (int) (apartmentSize * pricePerSquareMeter / 100.0 * (cadastralValue / 100.0));
  }

  /**
   * Фоновый процесс валидации статуса налога на недвижимость.
   */
  @Scheduled(fixedRateString = "${communal.background.job.property-tax.period}")
  public void checkPropertyTaxPaymentStatus() {
    final Pageable pageable = PageRequest.of(0, propertyTaxPageSize);
    final Page<PropertyTax> pagePropertyTaxes
        = this.propertyTaxRepository.findAllByIsPaid(false, pageable);
    if (pagePropertyTaxes == null) {
      LOGGER.info("Property Tax not found!");
    } else {
      final List<PropertyTax> propertyTaxes = pagePropertyTaxes.getContent();
      propertyTaxes.forEach(this::changePaymentStatus);
    }
  }


  /**
   * Валидирует статус оплаты.
   *
   * @param propertyTax налог на недвижимость
   */
  public void changePaymentStatus(final PropertyTax propertyTax) {
    try {
      if (this.checkPaymentStatus(propertyTax.getPaymentRequestId())) {
        propertyTax.setIsPaid(true);
        this.propertyTaxRepository.save(propertyTax);
        this.logging.addLog(
            LogEntry.builder()
                .service("Communal")
                .created(new Date())
                .message(
                    String.format(
                        "PropertyTax with ID = %d has been paid",
                        propertyTax.getPropertyTaxId()))
                .build());
      }
    } catch (FeignException fe) {
      LOGGER.error("Failed to check payment status!", fe);
    }
  }

  /**
   * Фоновый процесс валидации статуса нкоммунальных квитанций.
   */
  @Scheduled(fixedRateString = "${communal.background.job.property-tax.period}")
  public void checkUtilityBillPaymentStatus() {
    final Pageable pageable = PageRequest.of(0, utilityBillPageSize);
    final Page<UtilityBill> pageUtilityBills
        = this.utilityBillRepository.findAllByIsPaid(false, pageable);
    if (pageUtilityBills == null) {
      LOGGER.info("Utility Bill not found!");
    } else {
      final List<UtilityBill> utilityBills = pageUtilityBills.getContent();
      utilityBills.forEach(this::changePaymentStatus);
    }
  }

  /**
   * Валидирует статус оплаты.
   *
   * @param utilityBill налог на недвижимость
   */
  public void changePaymentStatus(final UtilityBill utilityBill) {
    try {
      if (this.checkPaymentStatus(utilityBill.getPaymentRequestId())) {
        utilityBill.setIsPaid(true);
        this.utilityBillRepository.save(utilityBill);
        this.logging.addLog(
            LogEntry.builder()
                .service("Communal")
                .created(new Date())
                .message(
                    String.format(
                        "UtilityBill with ID = %d has been paid",
                        utilityBill.getUtilityBillId()))
                .build());
      }
    } catch (FeignException fe) {
      LOGGER.error("Failed to check payment status!", fe);
    }
  }

  /**
   * Проверяет статус оплаты в банковском сервисе.
   *
   * @param paymentRequestId идентификатор выставленно счёта
   * @return статус оплаты
   * @throws FeignException если сервис не отвечает
   */
  public Boolean checkPaymentStatus(final Long paymentRequestId) throws FeignException {
    return bankIntegrationService.checkPaymentStatus(paymentRequestId);
  }
}
