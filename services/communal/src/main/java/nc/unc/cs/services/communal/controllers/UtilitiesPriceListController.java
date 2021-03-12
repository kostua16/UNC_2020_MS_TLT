package nc.unc.cs.services.communal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import nc.unc.cs.services.communal.controllers.payloads.CreationUtilitiesPriceList;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import nc.unc.cs.services.communal.services.CommunalService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "communal/utilities/price-list")
@Tag(name = "Utilities Price List API")
public class UtilitiesPriceListController {

  private final CommunalService communalService;

  public UtilitiesPriceListController(final CommunalService communalService) {
    this.communalService = communalService;
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  @Operation(
      summary = "addUtilitiesPriceList",
      description =
          "Creation and addition of price lists for calculating the amount of spent utilities.",
      method = "POST")
  @ApiResponse(responseCode = "400", description = "UtilitiesPriceList with ID = null")
  public ResponseEntity<UtilitiesPriceList> addUtilitiesPriceList(
      @Validated
          @RequestBody
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              description = "Data for registration of the price list")
          final CreationUtilitiesPriceList newUtilitiesPriceList) {
    return this.communalService.addUtilitiesPriceList(newUtilitiesPriceList);
  }

  @GetMapping(produces = "application/json")
  @Operation(
      summary = "getAllUtilitiesPriceList",
      description = "List of UtilitiesPriceList",
      method = "GET")
  public List<UtilitiesPriceList> getAllUtilitiesPriceList() {
    return this.communalService.getAllUtilitiesPriceList();
  }
}
