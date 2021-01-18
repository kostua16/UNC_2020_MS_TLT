package nc.unc.cs.services.bank.controllers;

import java.util.List;
import nc.unc.cs.services.bank.controllers.payloads.requests.PaymentPayload;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bank")
@CrossOrigin
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(final BankService bankService) {
        this.bankService = bankService;
    }


    @PostMapping(value = "request-payment", consumes = "application/json", produces = "application/json")
    public Long requestPayment(@RequestBody final PaymentPayload paymentPayload) {
        return this.bankService.requestPayment(
                paymentPayload.getServiceId(),
                paymentPayload.getCitizenId(),
                paymentPayload.getAmount(),
                paymentPayload.getTaxAmount()
            );
    }

    @PutMapping(value = "payment/{paymentId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Long> pay(@PathVariable("paymentId") final Long paymentId) {
        System.out.println(paymentId);
        return this.bankService.payment(paymentId);
    }

    @GetMapping("{paymentId}")
    public Boolean checkPaymentStatus(@PathVariable("paymentId") final Long paymentId) {
        return this.bankService.isPaid(paymentId);
    }

    @GetMapping("check/{citizenId}")
    public List<PaymentRequest> getPayments(@PathVariable("citizenId") Long citizenId) {
        return this.bankService.getPayments(citizenId);
    }
}
