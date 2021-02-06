package nc.unc.cs.services.communal.services;

import java.util.Date;
import java.util.List;
import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTax;
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

    // процент от суммы, который уходит в налоговый сервис // изменить!!!
    private final Integer TAX_PERCENT = 10;
    private final Long SERVICE_ID = 20L;

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

    /**
     * Расчёт налога на имущество
     *
     * @param apartmentSize кол-во кв.м. в помещении
     * @param pricePerSquareMeter стоимость кв.м. в данном регионе
     * @param cadastralValue кадастровый значение (процент) в данном регионе
     * @return налог
     */
    private Integer calculatePropertyTaxAmount(
        final Double apartmentSize,
        final Double pricePerSquareMeter,
        final Double cadastralValue
    ) {
        return (int) (apartmentSize * pricePerSquareMeter / 100 * (cadastralValue / 100));
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

    // написать тесты
    public ResponseEntity<PropertyTaxValue> addPropertyTaxValue(final PropertyTaxValue propertyTaxValue) {
        if (
            propertyTaxValue.getCadastralValue() != null
                && propertyTaxValue.getPricePerSquareMeter() != null
                && propertyTaxValue.getRegion() != null
                && propertyTaxValue.getCadastralValue() > 0
                && propertyTaxValue.getPricePerSquareMeter() > 0
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

    public ResponseEntity<PropertyTax> calculatePropertyTax(final Long propertyId) {
        PropertyTax propertyTax = new PropertyTax();
        final Property property = this.propertyRepository.findPropertyByPropertyId(propertyId);

        if (property == null) {
            logger.error("Property with ID = {} not found", propertyId);
            return ResponseEntity.status(400).body(propertyTax);
        }

        PropertyTaxValue propertyTaxValue
            = this.propertyTaxValueRepository.findPropertyTaxValueByRegion(property.getRegion());

        if (propertyTaxValue == null) {
            logger.error("PropertyTaxValue with region = {} not found", property.getRegion());
            return ResponseEntity.status(503).body(propertyTax);
        }

        propertyTax.setPropertyId(property.getPropertyId());
        propertyTax.setCitizenId(property.getCitizenId());
        propertyTax.setIsPaid(false);
        propertyTax.setDate(new Date());
        propertyTax.setTaxAmount(
            this.calculatePropertyTaxAmount(
                Double.valueOf(property.getApartmentSize()),
                Double.valueOf(propertyTaxValue.getPricePerSquareMeter()),
                Double.valueOf(propertyTaxValue.getCadastralValue()))
        );

        try {
            Long paymentRequestId = this.bankService
                .requestPayment(new PaymentPayload(
                    SERVICE_ID,
                    propertyTax.getCitizenId(),
                    propertyTax.getTaxAmount(),
                    propertyTax.getTaxAmount() / TAX_PERCENT
                )).getBody();

            propertyTax.setPaymentRequestId(paymentRequestId);
            this.propertyTaxRepository.save(propertyTax);

            logger.info("PropertyTax successfully created");
            return ResponseEntity.ok(propertyTax);
        } catch (Exception e) {

            logger.error("Failed to create PropertyTax!");
            e.printStackTrace();
            return ResponseEntity.status(503).body(propertyTax);
        }
    }

    // придумать как будут синхронизироваться оплата в банке и статус в платёжках на сервисах
    public ResponseEntity<PropertyTax> changePropertyTaxStatus(final Long propertyTaxId) {
        PropertyTax propertyTax
            = this.getPropertyTaxById(propertyTaxId);

        if (propertyTax == null) {
            logger.error("PropertyTax with ID = {} not found", propertyTaxId);
            return ResponseEntity.status(400).body(null);
        } else {
            try {
                if (this.bankService.checkPaymentStatus(propertyTax.getPaymentRequestId())) {

                    propertyTax.setIsPaid(true);
                    this.propertyTaxRepository.save(propertyTax);
                    logger.info("PropertyTax with ID = {} has been paid", propertyTaxId);

                    return ResponseEntity.ok(propertyTax);
                } else {
                    logger.error("PropertyTax with ID = {} was not paid", propertyTax.getPropertyTaxId());
                    return ResponseEntity.status(400).body(propertyTax);
                }
            } catch (Exception e) {

                e.printStackTrace();
                logger.info("Failed to verify payment");
                return ResponseEntity.status(503).body(propertyTax);
            }
        }
    }

    public List<PropertyTax> getPropertyTaxesByCitizenId(final Long citizenId) {
        return this.propertyTaxRepository.findPropertyTaxByCitizenId(citizenId);
    }

    public List<PropertyTax> getDebtPropertyTaxesByProperty(final Long propertyId) {
        return this.propertyTaxRepository.findPropertyTaxesByPropertyIdAndIsPaid(propertyId, false);
    }

    public List<PropertyTax> getAllPropertyTax() {
        return this.propertyTaxRepository.findAll();
    }

    public PropertyTax getPropertyTaxById(final Long propertyTaxId) {
        return this.propertyTaxRepository.findPropertyTaxByPropertyTaxId(propertyTaxId);
    }
}
