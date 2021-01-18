package nc.unc.cs.services.passport.integration.bank_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("bank")
public interface BankService {
    @PostMapping(value = "request-payment", consumes = "application/json", produces = "application/json")
    public Long requestPayment(@RequestBody final PaymentPayload paymentPayload);
}
