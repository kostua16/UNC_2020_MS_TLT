package nc.unc.cs.services.communal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "communal/property-tax")
@Tag(name = "Property Tax Api", description = "API calculate property tax")
@CrossOrigin
public class PropertyTaxController {

  private final PropertyTaxService propertyTaxService;

  @Autowired
  public PropertyTaxController(final PropertyTaxService propertyTaxService) {
    this.propertyTaxService = propertyTaxService;
  }

  @PutMapping(value = "pay/{propertyTaxId}", produces = "application/json")
  @Operation(
      summary = "changePropertyTaxStatus",
      description = "Checking and changing the status of payment of property tax.",
      method = "PUT")
  @ApiResponses({
    @ApiResponse(responseCode = "400", description = "PropertyTax with ID = null"),
    @ApiResponse(responseCode = "503", description = "PropertyTax with ID = null")
  })
  public ResponseEntity<PropertyTax> changePropertyTaxStatus(
      @Parameter(
              name = "propertyTaxId",
              description = "Property Tax ID",
              required = true,
              schema = @Schema(type = "long"))
          @PathVariable("propertyTaxId")
          final Long propertyTaxId) {
    return this.propertyTaxService.changePropertyTaxStatus(propertyTaxId);
  }

  @GetMapping(value = "citizen/{citizenId}", produces = "application/json")
  @Operation(
      summary = "getPropertyTaxesByCitizenId",
      description = "List PropertyTax by citizen ID",
      method = "GET")
  public List<PropertyTax> getPropertyTaxesByCitizenId(
      @Parameter(
              name = "citizenId",
              description = "Citizen ID",
              required = true,
              schema = @Schema(type = "long"))
          @PathVariable("citizenId")
          final Long citizenId) {
    return this.propertyTaxService.getPropertyTaxesByCitizenId(citizenId);
  }

  @GetMapping(value = "property/{propertyId}", produces = "application/json")
  @Operation(
      summary = "getDebtPropertyTaxesByProperty",
      description = "List of debts for this property",
      method = "GET")
  public List<PropertyTax> getDebtPropertyTaxesByProperty(
      @Parameter(
              name = "propertyId",
              description = "Property ID",
              required = true,
              schema = @Schema(type = "long"))
          @PathVariable("propertyId")
          final Long propertyId) {
    return this.propertyTaxService.getDebtPropertyTaxesByProperty(propertyId);
  }

  @GetMapping(produces = "application/json")
  @Operation(summary = "getAllPropertyTax", description = "List all PropertyTax", method = "GET")
  public List<PropertyTax> getAllPropertyTax() {
    return this.propertyTaxService.getAllPropertyTax();
  }

  @GetMapping(value = "{propertyTaxId}", produces = "application/json")
  @Operation(summary = "getPropertyTax", description = "Get PropertyTax", method = "GET")
  public PropertyTax getPropertyTax(
      @Parameter(
              name = "propertyTaxId",
              description = "Property Tax ID",
              required = true,
              schema = @Schema(type = "long"))
          @PathVariable("propertyTaxId")
          final Long propertyTaxId) {
    return this.propertyTaxService.getPropertyTaxById(propertyTaxId);
  }
}
