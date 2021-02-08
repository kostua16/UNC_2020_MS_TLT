package nc.unc.cs.services.communal.services;

import java.util.List;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import nc.unc.cs.services.communal.repositories.UtilitiesPriceListRepository;
import nc.unc.cs.services.communal.repositories.UtilityBillRepository;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommunalService {

    private static final Logger logger = LoggerFactory.getLogger(CommunalService.class);

    private final PropertyRepository propertyRepository;
    private final UtilityBillRepository utilityBillRepository;
    private final UtilitiesPriceListRepository utilitiesPriceListRepository;

    @Autowired
    public CommunalService(
        final PropertyRepository propertyRepository,
        final UtilityBillRepository utilityBillRepository,
        final UtilitiesPriceListRepository utilitiesPriceListRepository
    ) {
        this.propertyRepository = propertyRepository;
        this.utilityBillRepository = utilityBillRepository;
        this.utilitiesPriceListRepository = utilitiesPriceListRepository;
    }

    // стоит ли добавлять и обнавлять прейскурант в одной функции???
    public ResponseEntity<UtilitiesPriceList> addUtilitiesPriceList(UtilitiesPriceList utilitiesPriceList) {
        if (
            utilitiesPriceList.getRegion() != null
            && utilitiesPriceList.getElectricityPrice() != null
            && utilitiesPriceList.getColdWaterPrice() != null
            && utilitiesPriceList.getHotWaterPrice() != null
            && !utilitiesPriceList.getRegion().trim().isEmpty()
            && utilitiesPriceList.getElectricityPrice() > 0
            && utilitiesPriceList.getColdWaterPrice() > 0
            && utilitiesPriceList.getHotWaterPrice() > 0
        ) {
            UtilitiesPriceList newPriceList
                = this.utilitiesPriceListRepository.findUtilitiesPriceListByRegion(utilitiesPriceList.getRegion());
            if (newPriceList == null) {
                this.utilitiesPriceListRepository.save(utilitiesPriceList);
                logger.info("UtilitiesPriceList has been created!");

                return ResponseEntity.ok(utilitiesPriceList);
            } else {
                newPriceList.setElectricityPrice(utilitiesPriceList.getElectricityPrice());
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

}
