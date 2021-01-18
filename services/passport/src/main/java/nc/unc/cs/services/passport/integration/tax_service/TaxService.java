package nc.unc.cs.services.passport.integration.tax_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("tax")
public interface TaxService {
    @PostMapping(value = "debt", produces = "application/json", consumes = "application/json")
    public Boolean getListUnpaidTaxes(@RequestBody final IdInfo idInfo);
}
