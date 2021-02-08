package nc.unc.cs.services.communal.services;

import java.util.List;
import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.Registration;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.RegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    private final PropertyRepository propertyRepository;
    private final RegistrationRepository registrationRepository;
    private final BankService bankService;

    @Autowired
    public RegistrationService(
        final PropertyRepository propertyRepository,
        final RegistrationRepository registrationRepository,
        final BankService bankService
    ) {
        this.propertyRepository = propertyRepository;
        this.registrationRepository = registrationRepository;
        this.bankService = bankService;
    }

    public Registration getActiveRegistrationByCitizenId(final Long citizenId) {
        return this.registrationRepository.findRegistrationByCitizenIdAndIsActive(citizenId, true);
    }

    /**
     * Method of adding / updating the registration of a citizen.
     * When updating the registration, the previous one becomes inactive.
     *
     * @param registration Place of registration
     * @return ResponseEntity status with input data
     */
    public ResponseEntity<Registration> addRegistration(final Registration registration) {
        // возможно стоит добавить расчёт стоимости услуги и налога на неё
        registration.setIsActive(true);
        registration.setCitizenId(111L);
        try {
            this.bankService.requestPayment(
                new PaymentPayload(13L, registration.getCitizenId(), 1000, 1000)
            );
            Registration lastRegistration = this.getActiveRegistrationByCitizenId(registration.getCitizenId());

            if (lastRegistration != null) {
                lastRegistration.setIsActive(false);
                this.registrationRepository.save(lastRegistration);
            }

            this.registrationRepository.save(registration);
            logger.info("Registration has been added to the citizen with ID = {}", registration.getCitizenId());

            return ResponseEntity.ok(registration);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error! Failed to add registration.");
            return ResponseEntity.status(503).body(registration);
        }
    }

    public Registration getRegistrationByCitizenId(final Long citizenId) {
        return this.getActiveRegistrationByCitizenId(citizenId);
    }

    public Registration getRegistrationByRegistrationId(final Long registrationId) {
        return this.registrationRepository.findRegistrationByRegistrationId(registrationId);
    }

    public List<Registration> getAllRegistrations(final Long citizenId) {
        return this.registrationRepository.findRegistrationsByCitizenId(citizenId);
    }

    public ResponseEntity<Property> addCitizensProperty(final Property property) {
        try {
            this.bankService.requestPayment(
                new PaymentPayload(14L, property.getCitizenId(), 10000, 1000) // hard code
            );
            Property lastProperty = this.propertyRepository
                .findPropertyByRegionAndCityAndStreetAndHouseAndApartment(
                    property.getRegion(),
                    property.getCity(),
                    property.getStreet(),
                    property.getHouse(),
                    property.getApartment()
                );

            if (lastProperty == null) {

                this.propertyRepository.save(property);
                logger.info("New property added");
            } else {
                lastProperty.setCitizenId(property.getCitizenId());
                property.setPropertyId(lastProperty.getPropertyId());
                this.propertyRepository.save(lastProperty);

                logger.info("Property owner updated");
            }

            return ResponseEntity.ok(property);
        } catch (Exception e) {
            logger.error("Failed to privatize property!");
            e.printStackTrace();
            return ResponseEntity.status(503).body(property);
        }
    }

    public List<Property> getPropertiesByCitizenId(final Long citizenId) {
        return this.propertyRepository.findPropertyByCitizenId(citizenId);
    }

}
