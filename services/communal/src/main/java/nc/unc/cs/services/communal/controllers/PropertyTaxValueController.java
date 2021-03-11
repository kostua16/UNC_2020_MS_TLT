package nc.unc.cs.services.communal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import nc.unc.cs.services.communal.controllers.payloads.CreationPropertyTaxValue;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "communal/tax/price-list")
@CrossOrigin
@Tag(name = "Property Tax Value API",
     description = "API create price list for calculate property tax")
public class PropertyTaxValueController {

  private final PropertyTaxService propertyTaxService;

  @Autowired
  public PropertyTaxValueController(
      final PropertyTaxService propertyTaxService) {
    this.propertyTaxService = propertyTaxService;
  }

  @GetMapping(value = "{propertyTaxValueId}", produces = "application/json")
  @Operation(summary = "getPropertyTaxValueById",
             description = "Receiving PropertyTaxValue by ID", method = "GET")
  public PropertyTaxValue
  getPropertyTaxValueById(@Parameter(
      name = "propertyTaxValueId", description = "Property Tax Value ID",
      required = true, schema = @Schema(type = "long")
      ) @PathVariable("propertyTaxValueId") final Long propertyTaxValueId) {
    return this.propertyTaxService.getPropertyTaxValueById(propertyTaxValueId);
  }

  @GetMapping(value = "region", produces = "application/json")
  @Operation(
      summary = "getPropertyTaxValueByRegion",
      description = "Receiving PropertyTaxValue by region of the location",
      method = "GET")
  public PropertyTaxValue
  getPropertyTaxValueByRegion(@Parameter(
      name = "region", description = "Region to search for PropertyTaxValue",
      required = true, schema = @Schema(type = "string"))
                              @RequestParam("region") final String region) {
    return this.propertyTaxService.getPropertyTaxValueByRegion(region);
  }

  @GetMapping(produces = "application/json")
  @Operation(summary = "getListPropertyTaxValue",
             description = "Receiving all PropertyTaxValue", method = "GET")
  public List<PropertyTaxValue>
  getListPropertyTaxValue() {
    return this.propertyTaxService.getListPropertyTaxValue();
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  @Operation(summary = "addPropertyTaxValue",
             description = "Registration price list for calculate property tax",
             method = "POST")
  @ApiResponse(responseCode = "400",
               description = "PropertyTaxService with ID = null")
  public ResponseEntity<PropertyTaxValue>
  addPropertyTaxValue(
      @Validated @RequestBody @io.swagger.v3.oas.annotations.parameters.
      RequestBody(required = true,
                  description = "Data for registration of the price list")
      final CreationPropertyTaxValue newPropertyTaxValue) {
    return this.propertyTaxService.addPropertyTaxValue(newPropertyTaxValue);
  }
}
