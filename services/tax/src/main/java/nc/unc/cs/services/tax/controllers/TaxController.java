package nc.unc.cs.services.tax.controllers;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import nc.unc.cs.services.tax.controllers.payloads.CreateTax;
import nc.unc.cs.services.tax.controllers.payloads.IdInfo;
import nc.unc.cs.services.tax.controllers.payloads.UpdateTax;
import nc.unc.cs.services.tax.entities.Tax;
import nc.unc.cs.services.tax.services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tax")
public class TaxController {

    private final TaxService taxService;

    @Autowired
    public TaxController(final TaxService taxService) {
        this.taxService = taxService;
    }


    @GetMapping("{taxId}")
    public Boolean checkPaid(@PathVariable("taxId") final Long taxId) {
        return this.taxService.isPaid(taxId);
    }

    @PostMapping("create")
    public Long createTax(@RequestBody final CreateTax createTax) {
        return this.taxService.createTax(
            createTax.getServiceId(), createTax.getCitizenId(), createTax.getTaxAmount());
    }

    @PutMapping("{taxId}")
    public ResponseEntity<String> payTax(
        @PathVariable("taxId") final Long taxId,
        @RequestBody final UpdateTax updateTax
    ) {
        this.taxService.payTax(taxId, new Date()); // заглушка

        return ResponseEntity.ok("Платёжь прошёл");
    }

    @PostMapping("my-debt")
    public List<Tax> getTaxes(@RequestBody final IdInfo idInfo) {
        return this.taxService.getTaxes(idInfo.getCitizenId());
    }

    @PostMapping("debt")
    public List<Tax> getListUnpaidTaxes(@RequestBody final IdInfo idInfo) {
        return this.taxService.getListUnpaidTaxes(idInfo.getServiceId(), idInfo.getCitizenId());
    }

    // e.g.: http://localhost:8080/tax/page-debt?startPage=2&endPage=2&status=false
    @GetMapping("page-debt")
    public List<Tax> getPage(
        @PathParam("pageNumber") final Integer pageNumber,
        @PathParam("size") final Integer size,
        @PathParam("status") final Boolean status,
        @PathParam("column") final String column
    ) {
        return this.taxService.getPage(pageNumber, size, status, column);
    }
}
