package nc.unc.cs.services.communal.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping(value = "communal/tax/price")
@CrossOrigin
@Api(value = "Property Tax Value API",
     description = "API create price list for calculate property tax")
public class PropertyTaxValueController {

  private final PropertyTaxService propertyTaxService;

  @Autowired
  public PropertyTaxValueController(
      final PropertyTaxService propertyTaxService) {
    this.propertyTaxService = propertyTaxService;
  }

  @GetMapping(value = "{propertyTaxValueId}", produces = "application/json")
  @ApiOperation(httpMethod = "GET", value = "Receiving PropertyTaxValue",
                notes = "Receiving PropertyTaxValue by ID",
                nickname = "getPropertyTaxValueById")
  public PropertyTaxValue
  getPropertyTaxValueById(
      @ApiParam(name = "propertyTaxValueId", type = "long",
                value = "Property Tax Value ID", required = true)
      @PathVariable("propertyTaxValueId") final Long propertyTaxValueId) {
    return this.propertyTaxService.getPropertyTaxValueById(propertyTaxValueId);
  }

  @GetMapping(value = "region", produces = "application/json")
  @ApiOperation(httpMethod = "GET", value = "Receiving PropertyTaxValue",
                notes = "Receiving PropertyTaxValue by region of the location",
                nickname = "getPropertyTaxValueByRegion")
  public PropertyTaxValue
  getPropertyTaxValueByRegion(
      @ApiParam(name = "region", type = "string",
                value = "Region to search for PropertyTaxValue",
                required = true) @RequestParam("region") final String region) {
    return this.propertyTaxService.getPropertyTaxValueByRegion(region);
  }

  @GetMapping(produces = "application/json")
  @ApiOperation(httpMethod = "GET", value = "Receiving all PropertyTaxValue",
                nickname = "getListPropertyTaxValue")
  public List<PropertyTaxValue>
  getListPropertyTaxValue() {
    return this.propertyTaxService.getListPropertyTaxValue();
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  @ApiOperation(httpMethod = "POST", value = "Registration Property Tax Value",
                notes = "Registration price list for calculate property tax",
                nickname = "addPropertyTaxValue")
  @ApiResponses({
    @ApiResponse(code = 400, message = "PropertyTaxService with ID = null",
                 response = PropertyTaxValue.class)
  })
  @ApiImplicitParam(name = "propertyTaxValue",
                    value = "Data for registration of the price list",
                    required = true, type = "PropertyTaxValue",
                    dataType = "PropertyTaxValue",
                    dataTypeClass = PropertyTaxValue.class, paramType = "body")
  public ResponseEntity<PropertyTaxValue>
  addPropertyTaxValue(@RequestBody final PropertyTaxValue propertyTaxValue) {
    return this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
  }
}
