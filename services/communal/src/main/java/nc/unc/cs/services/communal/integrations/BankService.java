package nc.unc.cs.services.communal.integrations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("bank")
@RequestMapping("bank")
public interface BankService {
    @PostMapping(value = "request-payment", consumes = "application/json", produces = "application/json")
    ResponseEntity<PaymentRequest> requestPayment(@RequestBody final PaymentPayload paymentPayload);
}
