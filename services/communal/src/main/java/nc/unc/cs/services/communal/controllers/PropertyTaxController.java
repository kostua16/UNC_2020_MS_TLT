package nc.unc.cs.services.communal.controllers;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nc.unc.cs.services.communal.controllers.payloads.IdRequest;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "communal/property-tax")
@CrossOrigin
@Api(value = "Property Tax API", description = "API calculate property tax")
public class PropertyTaxController {

    private final PropertyTaxService propertyTaxService;

    @Autowired
    public PropertyTaxController(final PropertyTaxService propertyTaxService) {
        this.propertyTaxService = propertyTaxService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(
        httpMethod = "POST",
        value = "Calculate Property Tax",
        notes = "Calculate the tax on the selected property and notify the owner",
        nickname = "calculatePropertyTax"
    )
    @ApiResponses({
        @ApiResponse(code = 400, message = "PropertyTax with ID = null", response = PropertyTax.class),
        @ApiResponse(code = 503, message = "PropertyTax with ID = null", response = PropertyTax.class)
    })
    @ApiImplicitParam(
        name = "idRequest",
        value = "Property ID for which the tax will be calculated",
        required = true,
        type = "IdRequest",
        dataType = "IdRequest",
        dataTypeClass = IdRequest.class,
        paramType = "body"
    )
    public ResponseEntity<PropertyTax> calculatePropertyTax(
        @Validated @RequestBody final IdRequest idRequest
    ) {
        return this.propertyTaxService.calculatePropertyTax(idRequest.getPropertyId());
    }

    @PutMapping(value = "pay/{propertyTaxId}", produces = "application/json")
    @ApiOperation(
        httpMethod = "PUT",
        value = "Change property tax payment status",
        notes = "Checking and changing the status of payment of property tax.",
        nickname = "changePropertyTaxStatus"
    )
    @ApiResponses({
        @ApiResponse(code = 400, message = "PropertyTax with ID = null", response = PropertyTax.class),
        @ApiResponse(code = 503, message = "PropertyTax with ID = null", response = PropertyTax.class)
    })
    public ResponseEntity<PropertyTax> changePropertyTaxStatus(
        @ApiParam(name = "propertyTaxId", type = "long", value = "Property Tax ID", required = true)
        @PathVariable("propertyTaxId") final Long propertyTaxId
    ) {
        return this.propertyTaxService.changePropertyTaxStatus(propertyTaxId);
    }

    @GetMapping(value = "citizen/{citizenId}", produces = "application/json")
    @ApiOperation(
        httpMethod = "GET",
        value = "List PropertyTax by citizen ID",
        nickname = "getPropertyTaxesByCitizenId"
    )
    public List<PropertyTax> getPropertyTaxesByCitizenId(
        @ApiParam(name = "citizenId", type = "long", value = "Citizen ID", required = true)
        @PathVariable("citizenId") final Long citizenId
    ) {
        return this.propertyTaxService.getPropertyTaxesByCitizenId(citizenId);
    }

    @GetMapping(value = "property/{propertyId}", produces = "application/json")
    @ApiOperation(
        httpMethod = "GET",
        value = "List debt PropertyTax",
        notes = "List of debts for this property",
        nickname = "getDebtPropertyTaxesByProperty"
    )
    public List<PropertyTax> getDebtPropertyTaxesByProperty(
        @ApiParam(name = "propertyId", type = "long", value = "Property ID", required = true)
        @PathVariable("propertyId") final Long propertyId
    ) {
        return this.propertyTaxService.getDebtPropertyTaxesByProperty(propertyId);
    }

    @GetMapping(produces = "application/json")
    @ApiOperation(
        httpMethod = "GET",
        value = "List all PropertyTax",
        nickname = "getAllPropertyTax"
    )
    public List<PropertyTax> getAllPropertyTax() {
        return this.propertyTaxService.getAllPropertyTax();
    }

    @GetMapping(value = "{propertyTaxId}", produces = "application/json")
    @ApiOperation(
        httpMethod = "GET",
        value = "List all PropertyTax",
        nickname = "getAllPropertyTax"
    )
    public PropertyTax getPropertyTax(
        @ApiParam(name = "propertyTaxId", type = "long", value = "Property Tax ID", required = true)
        @PathVariable("propertyTaxId") final Long propertyTaxId
    ) {
        return this.propertyTaxService.getPropertyTaxById(propertyTaxId);
    }
}
