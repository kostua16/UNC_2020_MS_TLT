package nc.unc.cs.services.tax.controllers;

import javax.websocket.server.PathParam;
import java.util.List;
import nc.unc.cs.services.tax.controllers.payloads.requests.CreateTax;
import nc.unc.cs.services.tax.controllers.payloads.requests.IdInfo;
import nc.unc.cs.services.tax.controllers.payloads.responses.TaxPayment;
import nc.unc.cs.services.tax.entities.Tax;
import nc.unc.cs.services.tax.services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tax")
@CrossOrigin
public class TaxController {

    private final TaxService taxService;

    @Autowired
    public TaxController(final TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping(value = "{taxId}", produces = "application/json", consumes = "application/json")
    public Boolean checkPaid(@PathVariable("taxId") final Long taxId) {
        return this.taxService.isPaid(taxId);
    }

    @PostMapping(value = "create", produces = "application/json", consumes = "application/json")
    public Long createTax(@RequestBody final CreateTax createTax) {
        return this.taxService.createTax(
            createTax.getServiceId(), createTax.getCitizenId(), createTax.getTaxAmount());
    }

    @PostMapping(value = "pay-tax", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Long> payTax(@RequestBody final TaxPayment taxPayment) {
        return this.taxService.payTax(taxPayment);
    }

    @PostMapping(value = "my-debt", produces = "application/json", consumes = "application/json")
    public List<Tax> getTaxes(@RequestBody final IdInfo idInfo) {
        return this.taxService.getTaxes(idInfo.getCitizenId());
    }

    @PostMapping(value = "debt", produces = "application/json", consumes = "application/json")
    public Boolean isNotDebtor(@RequestBody final IdInfo idInfo) {
        return this.taxService.isNotDebtor(idInfo.getServiceId(), idInfo.getCitizenId());
    }

    // e.g.: http://localhost:8080/tax/page-debt?startPage=2&endPage=2&status=false
    @GetMapping(value = "page-debt", produces = "application/json", consumes = "application/json")
    public List<Tax> getPage(
        @PathParam("pageNumber") final Integer pageNumber,
        @PathParam("size") final Integer size,
        @PathParam("status") final Boolean status,
        @PathParam("column") final String column
    ) {
        return this.taxService.getPage(pageNumber, size, status, column);
    }
}
