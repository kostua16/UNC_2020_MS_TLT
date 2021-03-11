package nc.unc.cs.services.tax.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.websocket.server.PathParam;
import nc.unc.cs.services.common.clients.tax.CreationTax;
import nc.unc.cs.services.common.clients.tax.IdInfo;
import nc.unc.cs.services.common.clients.tax.TaxPayment;
import nc.unc.cs.services.tax.entities.Tax;
import nc.unc.cs.services.tax.services.TaxService;
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
@RequestMapping("tax")
@Tag(name = "Tax Api")
public class TaxController {

  private final TaxService taxService;

  @Autowired
  public TaxController(final TaxService taxService) {
    this.taxService = taxService;
  }

  @GetMapping(produces = "application/json")
  public List<Tax> getAllTaxes() {
    return this.taxService.getAllTaxes();
  }

  @GetMapping(value = "{taxId}", produces = "application/json")
  @Operation(summary = "checkPaid", description = "Checking Tax Payment Status",
             method = "GET")
  public Boolean
  checkPaid(@Parameter(name = "taxId", description = "Tax ID", required = true,
                       schema = @Schema(type = "long")) @PathVariable("taxId")
            final Long taxId) {
    return this.taxService.isPaid(taxId);
  }

  @PostMapping(value = "create", produces = "application/json",
               consumes = "application/json")
  @Operation(summary = "createTax",
             description = "Tax creation for a specific service",
             method = "POST")
  public Long
  createTax(
      @Validated @RequestBody @io.swagger.v3.oas.annotations.parameters.
      RequestBody(
          required = true,
          description =
              "An object containing citizen (user) and service IDs and tax amount")
      final CreationTax createTax) {
    return this.taxService.createTax(createTax.getServiceId(),
                                     createTax.getCitizenId(),
                                     createTax.getTaxAmount());
  }

  @PostMapping(value = "pay-tax", produces = "application/json",
               consumes = "application/json")
  @Operation(summary = "payTax", description = "Change in tax payment status",
             method = "POST")
  @ApiResponses(@ApiResponse(responseCode = "400", description = "Tax ID"))
  public ResponseEntity<Long>
  payTax(@Validated @RequestBody @io.swagger.v3.oas.annotations.parameters.
         RequestBody(
             required = true,
             description = "An object containing tax ID and data of payment")
         final TaxPayment taxPayment) {
    return this.taxService.payTax(taxPayment);
  }

  @PostMapping(value = "debt", produces = "application/json",
               consumes = "application/json")
  @Operation(summary = "isNotDebtor",
             description =
                 "Checking a user of a citizen's debts for a specific service",
             method = "POST")
  public Boolean
  isNotDebtor(
      @Validated @RequestBody @io.swagger.v3.oas.annotations.parameters.
      RequestBody(
          required = true,
          description = "An object containing citizen (user) and service IDs")
      final IdInfo idInfo) {
    return this.taxService.isNotDebtor(idInfo.getServiceId(),
                                       idInfo.getCitizenId());
  }

  // e.g.:
  // http://localhost:8080/tax/page-debt?startPage=2&endPage=2&status=false
  @GetMapping(value = "page-debt", produces = "application/json")
  public List<Tax> getPage(@PathParam("pageNumber") final Integer pageNumber,
                           @PathParam("size") final Integer size,
                           @PathParam("status") final Boolean status,
                           @PathParam("column") final String column) {
    return this.taxService.getPage(pageNumber, size, status, column);
  }
}
