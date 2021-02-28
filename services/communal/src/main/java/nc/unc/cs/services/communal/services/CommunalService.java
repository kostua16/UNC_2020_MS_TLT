package nc.unc.cs.services.communal.services;

import java.util.Date;
import java.util.List;
import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import nc.unc.cs.services.communal.controllers.payloads.UtilitiesPayload;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import nc.unc.cs.services.communal.entities.UtilityBill;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.UtilitiesPriceListRepository;
import nc.unc.cs.services.communal.repositories.UtilityBillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommunalService {

  private static final Logger logger =
      LoggerFactory.getLogger(CommunalService.class);

  private final PropertyRepository propertyRepository;
  private final UtilityBillRepository utilityBillRepository;
  private final UtilitiesPriceListRepository utilitiesPriceListRepository;
  private final BankService bankService;

  @Autowired
  public CommunalService(final PropertyRepository propertyRepository,
                         final UtilityBillRepository utilityBillRepository,
                         final UtilitiesPriceListRepository
                             utilitiesPriceListRepository,
                         final BankService bankService) {
    this.propertyRepository = propertyRepository;
    this.utilityBillRepository = utilityBillRepository;
    this.utilitiesPriceListRepository = utilitiesPriceListRepository;
    this.bankService = bankService;
  }

  // стоит ли добавлять и обнавлять прейскурант в одной функции???
  public ResponseEntity<UtilitiesPriceList>
  addUtilitiesPriceList(UtilitiesPriceList utilitiesPriceList) {
    if (utilitiesPriceList.getRegion() != null &&
        utilitiesPriceList.getElectricityPrice() != null &&
        utilitiesPriceList.getColdWaterPrice() != null &&
        utilitiesPriceList.getHotWaterPrice() != null &&
        !utilitiesPriceList.getRegion().trim().isEmpty() &&
        utilitiesPriceList.getElectricityPrice() > 0 &&
        utilitiesPriceList.getColdWaterPrice() > 0 &&
        utilitiesPriceList.getHotWaterPrice() > 0) {
      UtilitiesPriceList newPriceList =
          this.utilitiesPriceListRepository.findUtilitiesPriceListByRegion(
              utilitiesPriceList.getRegion());
      if (newPriceList == null) {
        this.utilitiesPriceListRepository.save(utilitiesPriceList);
        logger.info("UtilitiesPriceList has been created!");

        return ResponseEntity.ok(utilitiesPriceList);
      } else {
        newPriceList.setElectricityPrice(
            utilitiesPriceList.getElectricityPrice());
        newPriceList.setColdWaterPrice(utilitiesPriceList.getColdWaterPrice());
        newPriceList.setHotWaterPrice(utilitiesPriceList.getHotWaterPrice());

        this.utilitiesPriceListRepository.save(newPriceList);
        logger.info("UtilitiesPriceList has been updated!");

        return ResponseEntity.ok(newPriceList);
      }
    } else {
      logger.error("Invalid input data!");
      return ResponseEntity.status(400).body(utilitiesPriceList);
    }
  }

  public List<UtilitiesPriceList> getAllUtilitiesPriceList() {
    return this.utilitiesPriceListRepository.findAll();
  }

  /**
   * Создание квитанции на затраченные коммунальные услуги
   *
   * @param utilitiesPayload входные данные с идентификатором имущества, и
   *     кол-вом затраченных коммунальных услуг
   * @return Ответ со статусм 200 и созданная квитанция
   */
  public ResponseEntity<UtilityBill>
  calculateUtilityBill(UtilitiesPayload utilitiesPayload) {
    UtilityBill utilityBill = new UtilityBill();

    Property property = this.propertyRepository.findPropertyByPropertyId(
        utilitiesPayload.getPropertyId());
    if (property == null) {
      logger.error("Property with ID = {} not found",
                   utilitiesPayload.getPropertyId());
      return ResponseEntity.status(400).body(utilityBill);
    }

    UtilitiesPriceList utilitiesPriceList =
        this.utilitiesPriceListRepository.findUtilitiesPriceListByRegion(
            property.getRegion());
    if (utilitiesPriceList ==
        null) { // сделать в скрипте общий прайс-лист и юзать его, если не
                // находится региональный
      logger.error("UtilitiesPriceList with region {} not found",
                   property.getRegion());
      return ResponseEntity.status(503).body(utilityBill);
    }

    utilityBill.setCitizenId(property.getCitizenId());
    utilityBill.setPropertyId(property.getPropertyId());
    utilityBill.setDate(new Date());
    utilityBill.setColdWater(utilitiesPayload.getColdWater());
    utilityBill.setHotWater(utilitiesPayload.getHotWater());
    utilityBill.setElectricity(utilitiesPayload.getElectricity());
    utilityBill.setIsPaid(false);
    utilityBill.setColdWaterAmount(utilitiesPayload.getColdWater() *
                                   utilitiesPriceList.getHotWaterPrice());
    utilityBill.setHotWaterAmount(utilitiesPayload.getHotWater() *
                                  utilitiesPriceList.getHotWaterPrice());
    utilityBill.setElectricityAmount(utilitiesPayload.getElectricity() *
                                     utilitiesPriceList.getElectricityPrice());
    utilityBill.setUtilityAmount(utilityBill.getColdWaterAmount() +
                                 utilityBill.getHotWaterAmount() +
                                 utilityBill.getElectricityAmount());

    try {
      Long paymentRequestId = this.bankService
                                  .requestPayment(new PaymentPayload(
                                      21L, utilityBill.getCitizenId(),
                                      utilityBill.getUtilityAmount(),
                                      utilityBill.getUtilityAmount() / 10))
                                  .getBody();
      utilityBill.setPaymentRequestId(paymentRequestId);
      this.utilityBillRepository.save(utilityBill);

      logger.info("Utility Bill has been created");
      return ResponseEntity.ok(utilityBill);
    } catch (Exception e) {
      logger.error("Failed to create PropertyTax!");
      e.printStackTrace();

      return ResponseEntity.status(503).body(utilityBill);
    }
  }

  public List<UtilityBill> getAllUtilityBills() {
    return this.utilityBillRepository.findAll();
  }
}
