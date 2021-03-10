package nc.unc.cs.services.communal.controllers;

import java.util.List;
import io.swagger.annotations.Api;
import nc.unc.cs.services.communal.controllers.payloads.CreationProperty;
import nc.unc.cs.services.communal.entities.Property;
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
@RequestMapping("communal/property")
@Api(value = "Property API")
public class PropertyController {

    private final RegistrationService registrationService;

    @Autowired
    public PropertyController(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Property> addCitizensProperty(
        @Validated @RequestBody final CreationProperty newProperty) {
        return this.registrationService.addCitizensProperty(newProperty);
    }

    @GetMapping(value = "citizen/{citizenId}", produces = "application/json")
    public List<Property> getPropertiesByCitizenId(
        @PathVariable("citizenId") final Long citizenId
    ) {
        System.out.println("WORK!");
        return this.registrationService.getPropertiesByCitizenId(citizenId);
    }
}
