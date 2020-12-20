package nc.unc.cs.services.bank.controllers;

import nc.unc.cs.services.bank.controllers.payloads.requests.PaymentPayload;
import nc.unc.cs.services.bank.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bank")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(final BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<String> send() {
        return ResponseEntity.ok("Good job");
    }

    @PostMapping("payment")
    public Long requestPayment(@RequestBody PaymentPayload paymentPayload) {
        return this.bankService
            .requestPayment(
                paymentPayload.getServiceId(),
                paymentPayload.getCitizenId(),
                paymentPayload.getAmount(),
                paymentPayload.getTaxAmount()
            );

    }

}
