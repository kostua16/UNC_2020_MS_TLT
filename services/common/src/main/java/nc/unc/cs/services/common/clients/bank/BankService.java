package nc.unc.cs.services.common.clients.bank;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "BANK", path = "bank") // вынести в переменную
@ConditionalOnMissingClass("nc.unc.cs.services.bank.controllers.BankController")
public interface BankService {
    @PostMapping(value = "request-payment", consumes = "application/json", produces = "application/json")
    ResponseEntity<PaymentRequest> requestPayment(@RequestBody final PaymentPayload paymentPayload);
}
