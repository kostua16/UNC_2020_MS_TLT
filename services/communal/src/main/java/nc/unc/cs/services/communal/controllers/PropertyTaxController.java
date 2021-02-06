package nc.unc.cs.services.communal.controllers;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import nc.unc.cs.services.communal.controllers.payloads.IdRequest;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "communal/tax/property-tax")
@CrossOrigin
@Api(value = "Property Tax API", description = "API calculate property tax")
public class PropertyTaxController {

    private final PropertyTaxService propertyTaxService;

    @Autowired
    public PropertyTaxController(final PropertyTaxService propertyTaxService) {
        this.propertyTaxService = propertyTaxService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PropertyTax> calculatePropertyTax(@RequestBody final IdRequest idRequest) {
        return this.propertyTaxService.calculatePropertyTax(idRequest.getPropertyId());
    }

    @PutMapping(value = "pay/{propertyTaxId}", produces = "application/json")
    public ResponseEntity<PropertyTax> changePropertyTaxStatus(
        @ApiParam(name = "propertyTaxId", type = "long", value = "Property Tax ID", required = true)
        @PathVariable("propertyTaxId") final Long propertyTaxId
    ) {
        return this.propertyTaxService.changePropertyTaxStatus(propertyTaxId);
    }

    @GetMapping(value = "citizen/{citizenId}", produces = "application/json")
    public List<PropertyTax> getPropertyTaxesByCitizenId(
        @ApiParam(name = "citizenId", type = "long", value = "Citizen ID", required = true)
        @PathVariable("citizenId") final Long citizenId
    ) {
        return this.propertyTaxService.getPropertyTaxesByCitizenId(citizenId);
    }

    @GetMapping(value = "propety/{propertyId}", produces = "application/json")
    public List<PropertyTax> getDebtPropertyTaxesByProperty(
        @ApiParam(name = "propertyId", type = "long", value = "Property ID", required = true)
        @PathVariable("propertyId") final Long propertyId
    ) {
        return this.propertyTaxService.getDebtPropertyTaxesByProperty(propertyId);
    }

    @GetMapping(produces = "application/json")
    public List<PropertyTax> getAllPropertyTax() {
        return this.propertyTaxService.getAllPropertyTax();
    }

    @GetMapping(value = "{propertyTaxId}", produces = "application/json")
    public PropertyTax getAllPropertyTax(
        @ApiParam(name = "propertyTaxId", type = "long", value = "Property Tax ID", required = true)
        @PathVariable("propertyTaxId") final Long propertyTaxId
    ) {
        return this.propertyTaxService.getPropertyTaxById(propertyTaxId);
    }
}
