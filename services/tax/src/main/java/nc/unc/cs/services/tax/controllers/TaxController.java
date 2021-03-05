package nc.unc.cs.services.tax.controllers;

import javax.websocket.server.PathParam;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Tax Api")
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
    @ApiOperation(
        httpMethod = "GET",
        value = "Checking Tax Payment Status",
        nickname = "checkPaid"
    )
    public Boolean checkPaid(
        @ApiParam(name = "taxId", type = "long", value = "Tax ID", required = true)
        @PathVariable("taxId") final Long taxId
    ) {
        return this.taxService.isPaid(taxId);
    }

    @PostMapping(value = "create", produces = "application/json", consumes = "application/json")
    @ApiOperation(
        httpMethod = "POST",
        value = "Create Tax",
        notes = "Tax creation for a specific service",
        nickname = "createTax"
    )
    @ApiImplicitParam(
        name = "createTax",
        value = "An object containing citizen (user) and service IDs and tax amount",
        required = true,
        type = "CreationTax",
        dataType = "CreationTax",
        dataTypeClass = CreationTax.class
//        paramType = "body"
    )
    public Long createTax(@Validated @RequestBody final CreationTax createTax) {
        return this.taxService.createTax(
            createTax.getServiceId(), createTax.getCitizenId(), createTax.getTaxAmount());
    }

    @PostMapping(value = "pay-tax", produces = "application/json", consumes = "application/json")
    @ApiOperation(
        httpMethod = "POST",
        value = "Payment of tax",
        notes = "Change in tax payment status",
        nickname = "payTax"
    )
    @ApiResponses({
        @ApiResponse(code = 400, message = "Tax ID", response = Long.class)
    })
    @ApiImplicitParam(
        name = "taxPayment",
        value = "An object containing tax ID and data of payment",
        required = true,
        type = "TaxPayment",
        dataType = "TaxPayment",
        dataTypeClass = TaxPayment.class,
        paramType = "body"
    )
    public ResponseEntity<Long> payTax(@Validated @RequestBody final TaxPayment taxPayment) {
        return this.taxService.payTax(taxPayment);
    }

    @PostMapping(value = "debt", produces = "application/json", consumes = "application/json")
    @ApiOperation(
        httpMethod = "POST",
        value = "A citizen is not a debtor",
        notes = "Checking a user of a citizen's debts for a specific service",
        nickname = "isNotDebtor"
    )
    @ApiImplicitParam(
        name = "idInfo",
        value = "An object containing citizen (user) and service IDs",
        required = true,
        type = "IdInfo",
        dataType = "IdInfo",
        dataTypeClass = IdInfo.class,
        paramType = "body"
    )
    public Boolean isNotDebtor(@Validated @RequestBody final IdInfo idInfo) {
        return this.taxService.isNotDebtor(idInfo.getServiceId(), idInfo.getCitizenId());
    }

    // e.g.: http://localhost:8080/tax/page-debt?startPage=2&endPage=2&status=false
    @GetMapping(value = "page-debt", produces = "application/json")
    public List<Tax> getPage(
        @PathParam("pageNumber") final Integer pageNumber,
        @PathParam("size") final Integer size,
        @PathParam("status") final Boolean status,
        @PathParam("column") final String column
    ) {
        return this.taxService.getPage(pageNumber, size, status, column);
    }
}
