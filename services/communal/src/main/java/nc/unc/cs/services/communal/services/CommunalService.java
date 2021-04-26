package nc.unc.cs.services.communal.services;

import java.util.List;
import nc.unc.cs.services.communal.controllers.payloads.CreationUtilitiesPriceList;
import nc.unc.cs.services.communal.controllers.payloads.UtilitiesPayload;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import nc.unc.cs.services.communal.entities.UtilityBill;
import nc.unc.cs.services.communal.exceptions.PropertyNotFoundException;
import nc.unc.cs.services.communal.exceptions.UtilitiesPriceListNotFoundException;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.UtilitiesPriceListRepository;
import nc.unc.cs.services.communal.repositories.UtilityBillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sun.plugin2.applet.context.InitialJNLPExecutionContext;

@Service
public class CommunalService {

  private static final Logger logger = LoggerFactory.getLogger(CommunalService.class);

  /** Идентификатор сервиса, поставляеющего услугу. */
  public static final Long SERVICE_ID = 21L;
  /** Налоговый процент. */
  public static final Integer TAX_PERCENT = 10;

  private final PropertyRepository propertyRepository;
  private final UtilityBillRepository utilityBillRepository;
  private final UtilitiesPriceListRepository utilitiesPriceListRepository;
  private final BankIntegrationService bankIntegrationService;

  @Autowired
  public CommunalService(
      final PropertyRepository propertyRepository,
      final UtilityBillRepository utilityBillRepository,
      final UtilitiesPriceListRepository utilitiesPriceListRepository,
      final BankIntegrationService bankIntegrationService) {
    this.propertyRepository = propertyRepository;
    this.utilityBillRepository = utilityBillRepository;
    this.utilitiesPriceListRepository = utilitiesPriceListRepository;
    this.bankIntegrationService = bankIntegrationService;
  }

  /**
   * Возвращает прейскурант для рассчёта стоимости затраченных коммунальных услуг.
   *
   * @param region наименование региона
   * @return прейскурант
   * @throws UtilitiesPriceListNotFoundException если не удасться найти прейскурант по заданному
   *     региону
   */
  public UtilitiesPriceList findPriceListByRegion(final String region) {
    final UtilitiesPriceList priceList =
        this.utilitiesPriceListRepository.findUtilitiesPriceListByRegion(region);
    if (priceList == null) {
      throw new UtilitiesPriceListNotFoundException(region);
    }
    return priceList;
  }

  /**
   * Возвращает данные о объект недвижимость.
   *
   * @param propertyId идентификатор недвижимости
   * @return объект недвижимость
   * @throws PropertyNotFoundException если не удасться найти недвижимость с заданным
   *     идентификатором
   */
  public Property findPropertyById(final Long propertyId) {
    final Property property = this.propertyRepository.findPropertyByPropertyId(propertyId);
    if (property == null) {
      throw new PropertyNotFoundException(propertyId);
    }
    return property;
  }

  /**
   * Возвращает все прейскуранты из БД.
   *
   * @return список прейскурантов
   */
  public List<UtilitiesPriceList> getAllUtilitiesPriceList() {
    return this.utilitiesPriceListRepository.findAll();
  }

  /**
   * Добовляет/Обнавляет прейскурант.
   *
   * @param newPriceList информация о прейскуранте
   * @return http-ответ, в теле которого находится сохранённый прейскурант
   */
  public ResponseEntity<UtilitiesPriceList> addUtilitiesPriceList(
      final CreationUtilitiesPriceList newPriceList) {
    final ResponseEntity<UtilitiesPriceList> response;
    final UtilitiesPriceList utilitiesPriceList =
        UtilitiesPriceList.builder()
            .region(newPriceList.getRegion())
            .coldWaterPrice(newPriceList.getColdWaterPrice())
            .hotWaterPrice(newPriceList.getHotWaterPrice())
            .electricityPrice(newPriceList.getElectricityPrice())
            .build();
    final UtilitiesPriceList lastPriceList = // пришлось использовать метод из напрямую репозитория
        this.utilitiesPriceListRepository.findUtilitiesPriceListByRegion(newPriceList.getRegion());
    if (lastPriceList == null) { // пришлось сделать так, а не через catch
      this.utilitiesPriceListRepository.save(utilitiesPriceList);
      logger.info("UtilitiesPriceList has been created!");

      response = ResponseEntity.ok(utilitiesPriceList);
    } else {
      lastPriceList.setElectricityPrice(newPriceList.getElectricityPrice());
      lastPriceList.setColdWaterPrice(newPriceList.getColdWaterPrice());
      lastPriceList.setHotWaterPrice(newPriceList.getHotWaterPrice());

      this.utilitiesPriceListRepository.save(lastPriceList);
      logger.info("UtilitiesPriceList has been updated!");

      response = ResponseEntity.ok(lastPriceList);
    }
    return response;
  }

  /**
   * Рассчитывает коммунальные затраты и вписывает их в квитанцию.
   *
   * @param region наименование региона.
   * @param utilitiesPayload входные данные с идентификатором имущества, и кол-вом затраченных
   *     коммунальных услуг
   * @return квитанция с заполненными данными о коммунальных затратах
   */
  public UtilityBill calculateUtilityCosts(
      final String region, final UtilitiesPayload utilitiesPayload) {
    final UtilitiesPriceList utilitiesPriceList = this.findPriceListByRegion(region);
    final UtilityBill utilityBill =
        UtilityBill.builder()
            .coldWater(utilitiesPayload.getColdWater())
            .hotWater(utilitiesPayload.getHotWater())
            .electricity(utilitiesPayload.getElectricity())
            .propertyId(utilitiesPayload.getPropertyId())
            .coldWaterAmount(
                utilitiesPayload.getColdWater() * utilitiesPriceList.getColdWaterPrice())
            .hotWaterAmount(utilitiesPayload.getHotWater() * utilitiesPriceList.getHotWaterPrice())
            .electricityAmount(
                utilitiesPayload.getElectricity() * utilitiesPriceList.getElectricityPrice())
            .build();
    utilityBill.setUtilityAmount(
        utilityBill.getColdWaterAmount()
            + utilityBill.getHotWaterAmount()
            + utilityBill.getElectricityAmount());

    logger.info("SSS Initial Utility Bill: {}", utilityBill);
    return utilityBill;
  }

  /**
   * Создание квитанцию на затраченные коммунальные услуги.
   *
   * @param utilitiesPayload входные данные с идентификатором имущества, и кол-вом затраченных
   *     коммунальных услуг
   * @return ответ со статусм 200 и созданная квитанция
   */
  public ResponseEntity<UtilityBill> calculateUtilityBill(final UtilitiesPayload utilitiesPayload) {
    final Property property = this.findPropertyById(utilitiesPayload.getPropertyId());
    final UtilityBill utilityBill =
        this.calculateUtilityCosts(property.getRegion(), utilitiesPayload);
    utilityBill.setCitizenId(property.getCitizenId());
    final Long paymentRequestId =
        this.bankIntegrationService.bankRequest(
            SERVICE_ID,
            utilityBill.getCitizenId(),
            utilityBill.getUtilityAmount(),
            utilityBill.getUtilityAmount() / TAX_PERCENT);
    utilityBill.setPaymentRequestId(paymentRequestId);
    this.utilityBillRepository.save(utilityBill);

    logger.info("Utility Bill has been created");
    return ResponseEntity.ok(utilityBill);
  }

  /**
   * Возвращает все коммунальные квитанции.
   *
   * @return список коммунальных квитанций
   */
  public List<UtilityBill> getAllUtilityBills() {
    return this.utilityBillRepository.findAll();
  }

  public List<UtilityBill> getCitizenUtilityBills(final Long citizenId) {
    return this.utilityBillRepository.findUtilityBillsByCitizenId(citizenId);
  }
}
