package nc.unc.cs.services.common.clients.bank;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "BANK", path = "bank", url = "${app.bank-url}")
@ConditionalOnMissingClass("nc.unc.cs.services.bank.controllers.BankController")
public interface BankService {
  @PostMapping(
      value = "request-payment",
      consumes = "application/json",
      produces = "application/json")
  ResponseEntity<Long> requestPayment(@RequestBody final PaymentPayload paymentPayload);

  @GetMapping(value = "{paymentId}", produces = "application/json")
  Boolean checkPaymentStatus(@PathVariable("paymentId") final Long paymentId);
}
