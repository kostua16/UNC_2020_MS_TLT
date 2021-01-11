package nc.unc.cs.services.bank.integration;

import nc.unc.cs.services.bank.controllers.payloads.responses.TaxPayment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("tax")
public interface TaxService {

    @PostMapping(value = "tax/create", produces = "application/json", consumes = "application/json")
    Long createTax(@RequestBody final CreateTax createTax);

    @PostMapping(value = "tax/pay-tax", produces = "application/json", consumes = "application/json")
    ResponseEntity<Long> payTax(@RequestBody final TaxPayment taxPayment);
}
