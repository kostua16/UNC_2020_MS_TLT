package nc.unc.cs.services.communal.services;

import nc.unc.cs.services.communal.entities.Registration;
import nc.unc.cs.services.communal.integrations.BankService;
import nc.unc.cs.services.communal.integrations.PaymentPayload;
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

    public ResponseEntity<Registration> addRegister(final Registration registration) {
        // возможно стоит добавить расчёт стоимости услуги и налога на неё
        registration.setIsActive(true);
        registration.setCitizenId(111L);
        try {
            this.bankService.requestPayment(new PaymentPayload(13L, registration.getCitizenId(), 1000, 1000));
            logger.info("step 1");
            this.registrationRepository.save(registration);
            return ResponseEntity.ok(registration);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error! Failed to add registration.");
            return ResponseEntity.status(503).body(registration);
        }
    }
}
