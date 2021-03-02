package nc.unc.cs.services.communal.controllers;

import java.util.List;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.Registration;
import nc.unc.cs.services.communal.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("communal/housing")
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Registration> addRegistration(@Validated @RequestBody Registration registration) {
        return this.registrationService.addRegistration(registration);
    }

    @GetMapping(value = "registrations/active/citizen/{citizenId}", produces = "application/json")
    public Registration getRegistrationByCitizenId(
        @Validated @PathVariable("citizenId") final Long citizenId
    ) {
        return this.registrationService.getActiveRegistrationByCitizenId(citizenId);
    }

    @GetMapping(value = "registrations/all/{citizenId}", produces = "application/json")
    public List<Registration> getAllRegistrations(
        @Validated @PathVariable("citizenId") final Long citizenId
    ) {
        return this.registrationService.getAllRegistrations(citizenId);
    }

    @GetMapping(value = "registrations/{registrationId}", produces = "application/json")
    public Registration getRegistrationByRegistrationId(
        @Validated @PathVariable("registrationId") final Long registrationId
    ) {
        return this.registrationService.getRegistrationByRegistrationId(registrationId);
    }

    @PostMapping(value = "property/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Property> addCitizensProperty(@Validated @RequestBody final Property property) {
        return this.registrationService.addCitizensProperty(property);
    }

    @GetMapping(value = "property/citizen/{citizenId}", produces = "application/json")
    public List<Property> getPropertiesByCitizenId(
        @Validated @PathVariable("citizenId") final Long citizenId
    ) {
        return this.registrationService.getPropertiesByCitizenId(citizenId);
    }
}
