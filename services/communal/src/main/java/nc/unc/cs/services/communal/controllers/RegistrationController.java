package nc.unc.cs.services.communal.controllers;

import nc.unc.cs.services.communal.entities.Registration;
import nc.unc.cs.services.communal.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("communal/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Registration> addRegistration(@RequestBody Registration registration) {
        return this.registrationService.addRegister(registration);
    }
}
