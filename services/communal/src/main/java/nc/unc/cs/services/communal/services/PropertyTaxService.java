package nc.unc.cs.services.communal.services;

import java.util.List;
import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PropertyTaxService {
    private static final Logger logger = LoggerFactory.getLogger(PropertyTaxService.class);

    private final PropertyRepository propertyRepository;
    private final PropertyTaxRepository propertyTaxRepository;
    private final PropertyTaxValueRepository propertyTaxValueRepository;
    private final BankService bankService;

    @Autowired
    public PropertyTaxService(
        final PropertyRepository propertyRepository,
        final PropertyTaxRepository propertyTaxRepository,
        final PropertyTaxValueRepository propertyTaxValueRepository,
        final BankService bankService
    ) {
        this.propertyRepository = propertyRepository;
        this.propertyTaxRepository = propertyTaxRepository;
        this.propertyTaxValueRepository = propertyTaxValueRepository;
        this.bankService = bankService;
    }

    public List<PropertyTaxValue> getListPropertyTaxValue() {
        return this.propertyTaxValueRepository.findAll();
    }

    public PropertyTaxValue getPropertyTaxValueById(final Long propertyTaxValueId) {
        return this.propertyTaxValueRepository.findPropertyTaxValueByPropertyTaxValueId(propertyTaxValueId);
    }

    public PropertyTaxValue getPropertyTaxValueByRegion(final String region) {
        return this.propertyTaxValueRepository.findPropertyTaxValueByRegion(region.toUpperCase());
    }

    public ResponseEntity<PropertyTaxValue> addPropertyTaxValue(final PropertyTaxValue propertyTaxValue) {
        if (
            propertyTaxValue.getCadastralValue() != null
                && propertyTaxValue.getRegion() != null
                && propertyTaxValue.getCadastralValue() > 0
                && !propertyTaxValue.getRegion().isEmpty()
        ) {
            try {
                propertyTaxValue.setRegion(propertyTaxValue.getRegion().toUpperCase());
                this.propertyTaxValueRepository.save(propertyTaxValue);
                logger.info("Property Tax Value has been created");

                return ResponseEntity.ok(propertyTaxValue);
            } catch (Exception e) {
                logger.error("The region was entered incorrectly.");
                e.printStackTrace();
                return ResponseEntity.status(400).body(propertyTaxValue);
            }

        } else {
            logger.error("Invalid input data!");
            return ResponseEntity.status(400).body(propertyTaxValue);
        }
    }

    public ResponseEntity<PropertyTaxValue> updatePropertyTaxValue(
        final Long propertyTaxValueId,
        final PropertyTaxValue newPropertyTaxValue
    ) {
        // переделать этот ужас
        PropertyTaxValue propertyTaxValue =
            this.propertyTaxValueRepository
                .findPropertyTaxValueByPropertyTaxValueId(propertyTaxValueId);

        if (propertyTaxValue == null) {
            propertyTaxValue = this.propertyTaxValueRepository
                .findPropertyTaxValueByPropertyTaxValueId(newPropertyTaxValue.getPropertyTaxValueId());
        }

        if (propertyTaxValue == null) {
            logger.error("PropertyTaxValue with ID = {} not found", propertyTaxValueId);
            return ResponseEntity.status(400).body(newPropertyTaxValue);
        }

        propertyTaxValue.setCadastralValue(newPropertyTaxValue.getCadastralValue());
        this.propertyTaxValueRepository.save(propertyTaxValue); // регион не меняется

        return ResponseEntity.ok(propertyTaxValue);
    }
}
