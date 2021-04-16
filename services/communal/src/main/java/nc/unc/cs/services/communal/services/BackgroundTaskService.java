package nc.unc.cs.services.communal.services;

import java.util.Date;
import java.util.List;
import feign.FeignException;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
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
  //  private final ThreadPoolTaskScheduler scheduler;
  private final BankIntegrationService bankIntegrationService;

  public BackgroundTaskService(
      final PropertyRepository propertyRepository,
      final PropertyTaxRepository propertyTaxRepository,
      final PropertyTaxValueRepository propertyTaxValueRepository,
      final BankIntegrationService bankIntegrationService
  ) {
    this.propertyRepository = propertyRepository;
    this.propertyTaxRepository = propertyTaxRepository;
    this.propertyTaxValueRepository = propertyTaxValueRepository;
    this.bankIntegrationService = bankIntegrationService;
  }

  // 1. Извлекается первая попавшаяся недвижимость, с *доступной датой для налогооблажения;
  // 2. Берётся область полученного объекта;
  // 3. Происходит выборка недвижимости (20-50 шт) с *доступной датой и идентичной областью;
  // 4. Происходит налоговый расчёт и сохранение квитанций и новой налоговой даты в таблице Property;
  // 5. Переход к пункту 1;
  // ! В случае отсутствия прейскуранта для конвретной области,
  // расчёт происходит по стандартному прейскуранту
  // *доступная дата - дата превышающая налоговый период
  @Scheduled(fixedDelay = 1000000) // вынести в проперти
  public void reportDate() {
    final Date beforeDate = DateUtils.addDays(new Date(), -taxPeriod);
    LOGGER.info("BeforeDate: \t{}", beforeDate);
    final Property property =
        this.propertyRepository.findPropertyByPropertyTaxDateBefore(beforeDate);
    LOGGER.info("PROPERTY: {}", property);
    if (property == null) {
      LOGGER.info("No tax creation is required.");
//      this.scheduler.shutdown(); // Выключает полностью
    } else {
      final List<Property> properties =
          this.propertyRepository
              .findPropertiesByPropertyTaxDateBeforeAndRegion(beforeDate, property.getRegion());
      final PropertyTaxValue propertyTaxValue =
          this.propertyTaxValueRepository.findPropertyTaxValueByRegion(property.getRegion());
      if (propertyTaxValue == null) {
        // пока выключаем, но нужно добавить дефолтный прейскурант
      } else {
        try {
          properties.forEach(p -> this.createPropertyTax(p, propertyTaxValue));
        } catch (final FeignException fe) {
          LOGGER.error("Failed to create property tax!", fe);
        }
      }
    }
  }

  private void createPropertyTax(
      final Property property,
      final PropertyTaxValue propertyTaxValue
  ) throws FeignException { // todo: как-то обрабатывать фоновые ошибки
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
    LOGGER.info("PropertyTax successfully created");

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

