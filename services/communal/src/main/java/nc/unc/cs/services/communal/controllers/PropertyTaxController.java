package nc.unc.cs.services.communal.controllers;

import io.swagger.annotations.Api;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "communal/tax/property")
@CrossOrigin
@Api(value = "Property Tax API", description = "API calculate property tax")
public class PropertyTaxController {

    private final PropertyTaxService propertyTaxService;

    @Autowired
    public PropertyTaxController(final PropertyTaxService propertyTaxService) {
        this.propertyTaxService = propertyTaxService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PropertyTax> calculatePropertyTax(@RequestBody final Long propertyId) {
        System.out.println(propertyId);
        return this.propertyTaxService.calculatePropertyTax(propertyId);
    }

}
