package nc.unc.cs.services.common.clients.tax;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "tax", url = "nc-edu-2020-tax.herokuapp.com") // вынести в переменную
@ConditionalOnMissingClass("nc.unc.cs.services.tax.controllers.TaxController")
@RequestMapping("tax")
public interface TaxService {

    @PostMapping(value = "create", produces = "application/json", consumes = "application/json")
    Long createTax(@RequestBody final CreationTax creationTax);

    @PostMapping(value = "pay-tax", produces = "application/json", consumes = "application/json")
    ResponseEntity<Long> payTax(@RequestBody final TaxPayment taxPayment);

    @PostMapping(value = "debt", produces = "application/json", consumes = "application/json")
    Boolean getListUnpaidTaxes(@RequestBody final IdInfo idInfo);
}
