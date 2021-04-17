package nc.unc.cs.services.communal.services;

import java.util.Date;
import java.util.List;
import feign.FeignException;
import nc.unc.cs.services.common.clients.logging.LogEntry;
import nc.unc.cs.services.common.clients.logging.LoggingService;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.exceptions.PropertyNotFoundException;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxValueRepository;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BackgroundTaskService {

  /** Логгер. */
  private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundTaskService.class);
  /** Процентный делитель. */
  public static final Double PERCENT_DIVISOR = 100.0;
  /** Налоговый процент от стоимости платежа. */
  public static final Integer TAX_PERCENT = 10;
  /** Номер сервиса. */
  public static final Long SERVICE_ID = 20L;

  /** Налоговый период.*/
  // @Value
  private final Integer taxPeriod = 1; // todo: change period

  private final PropertyRepository propertyRepository;
  private final PropertyTaxRepository propertyTaxRepository;
  private final PropertyTaxValueRepository propertyTaxValueRepository;
  private final BankIntegrationService bankIntegrationService;
  /** Сервис логгирования. */
  private final LoggingService logging;

  public BackgroundTaskService(
      final PropertyRepository propertyRepository,
      final PropertyTaxRepository propertyTaxRepository,
      final PropertyTaxValueRepository propertyTaxValueRepository,
      final BankIntegrationService bankIntegrationService,
      final LoggingService logging
  ) {
    this.propertyRepository = propertyRepository;
    this.propertyTaxRepository = propertyTaxRepository;
    this.propertyTaxValueRepository = propertyTaxValueRepository;
    this.bankIntegrationService = bankIntegrationService;
    this.logging = logging;
  }

  // 1. Извлекается первая попавшаяся недвижимость, с *доступной датой для налогооблажения;
  // 2. Берётся область полученного объекта;
  // 3. Происходит выборка недвижимости (20-50 шт) с *доступной датой и идентичной областью;
  // 4. Происходит налоговый расчёт и сохранение квитанций и новой налоговой даты в таблице Property;
  // 5. Переход к пункту 1;
  // ! В случае отсутствия прейскуранта для конвретной области,
  // расчёт происходит по стандартному прейскуранту
  // *доступная дата - дата превышающая налоговый период
  @Scheduled(fixedDelay = 60000) // вынести в проперти
  public void reportDate() {
    final Date beforeDate = DateUtils.addDays(new Date(), -taxPeriod);
    try {
      final Property property = this.getPropertyByTaxDateBefore(beforeDate);
      final List<Property> properties =
          this.propertyRepository
              .findFirst3ByPropertyTaxDateBeforeAndRegion(beforeDate, property.getRegion());
      final PropertyTaxValue priceList
          = this.propertyTaxValueRepository.findPropertyTaxValueByRegion(property.getRegion());
      final PropertyTaxValue propertyTaxValue;
      if (priceList == null) {
        this.logging.addLog(
            LogEntry
                .builder()
                .service("Communal")
                .created(new Date())
                .message(String.format(
                    "Property tax value not found for region = %s. \n" +
                        "The default price list will be used.",
                    property.getRegion()))
                .build()
        );
        propertyTaxValue = PropertyTaxValue
            .builder()
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
//    } catch (final PropertyNotFoundException pe) {
//      LOGGER.info("No tax creation is required.", pe);
    }
  }

  public Property getPropertyByTaxDateBefore(
      final Date beforeDate
  ) throws FeignException, PropertyNotFoundException {
    final Property property =
        this.propertyRepository.findPropertyByPropertyTaxDateBefore(beforeDate);
    if (property == null) {
      this.logging.addLog(
          LogEntry
              .builder()
              .service("Communal")
              .created(new Date())
              .message("No tax creation is required.")
              .build()
      );
      throw new PropertyNotFoundException(beforeDate);
    } else {
      return property;
    }
  }

  private void createPropertyTax(
      final Property property,
      final PropertyTaxValue propertyTaxValue
  ) throws FeignException {
    try {
      final Integer amount =
          this.calculatePropertyTaxAmount(
              Double.valueOf(property.getApartmentSize()),
              Double.valueOf(propertyTaxValue.getPricePerSquareMeter()),
              Double.valueOf(propertyTaxValue.getCadastralValue())
          );
      final PropertyTax propertyTax =
          PropertyTax.builder()
              .propertyId(property.getPropertyId())
              .citizenId(property.getCitizenId())
              .taxAmount(amount)
              .paymentRequestId(
                  this.bankIntegrationService.bankRequest(
                      SERVICE_ID, property.getCitizenId(), amount, TAX_PERCENT))
              .build();
      this.propertyTaxRepository.save(propertyTax);
      property.setPropertyTaxDate(new Date());
      this.propertyRepository.save(property);
      LOGGER.info("PropertyTax successfully created");
      this.addLogg(propertyTaxValue);
    } catch (final FeignException fe) {
      LOGGER.error("Bank service unavailable!", fe);
      this.logging.addLog(
          LogEntry
              .builder()
              .service("Communal")
              .created(new Date())
              .message(String.format("Bank service unavailable!\n" +
                  "Failed to create tax for property with ID = %d",
                  property.getPropertyId()))
              .build()
      );
    }
  }

  public void addLogg(final PropertyTaxValue propertyTaxValue) {
    try {
      this.logging.addLog(
          LogEntry
              .builder()
              .service("Communal")
              .created(new Date())
              .message(String.format("Property tax has been created! %s",
                  propertyTaxValue.toString()))
              .build()
      );
    } catch (final FeignException fe) {
      LOGGER.error("Logging service unavailable!", fe);
    }
  }

  public Integer calculatePropertyTaxAmount(
      final Double apartmentSize, final Double pricePerSquareMeter, final Double cadastralValue) {
    return (int) (
        apartmentSize
            * pricePerSquareMeter
            / PERCENT_DIVISOR
            * (cadastralValue / PERCENT_DIVISOR)
    );
  }
}

