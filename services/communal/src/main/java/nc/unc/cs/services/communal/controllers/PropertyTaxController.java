package nc.unc.cs.services.communal.controllers;

import java.util.List;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "communal/tax")
@CrossOrigin
public class PropertyTaxController {

    private final PropertyTaxService propertyTaxService;

    @Autowired
    public PropertyTaxController(final PropertyTaxService propertyTaxService) {
        this.propertyTaxService = propertyTaxService;
    }

    @GetMapping(value = "price/{propertyTaxValueId}", produces = "application/json")
    public PropertyTaxValue getPropertyTaxValueById(
        @PathVariable("propertyTaxValueId") final Long propertyTaxValueId
    ) {
        return this.propertyTaxService.getPropertyTaxValueById(propertyTaxValueId);
    }

    @GetMapping(value = "price/state", produces = "application/json")
    public PropertyTaxValue getPropertyTaxValueByState(
        @RequestParam("state") final String state
    ) {
        return this.propertyTaxService.getPropertyTaxValueByState(state);
    }

    @GetMapping(value = "price", produces = "application/json")
    public List<PropertyTaxValue> getListPropertyTaxValue() {
        return this.propertyTaxService.getListPropertyTaxValue();
    }

    @PostMapping(value = "price", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PropertyTaxValue> addPropertyTaxValue(@RequestBody final PropertyTaxValue propertyTaxValue) {
        return this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
    }
}
