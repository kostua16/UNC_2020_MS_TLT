package nc.unc.cs.services.bank.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import nc.unc.cs.services.bank.controllers.dto.PeriodTransactions;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.services.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Tag(name = "Bank Api")
@CrossOrigin
public class BankController {

  private final BankService bankService;

  @Autowired
  public BankController(final BankService bankService) {
    this.bankService = bankService;
  }

  @PostMapping(
      value = "request-payment",
      consumes = "application/json",
      produces = "application/json")
  @Operation(
      summary = "requestPayment",
      description = "Registers taxes and creates an invoice for payment (PaymentRequest)",
      method = "POST")
  public ResponseEntity<Long> requestPayment(
      @Validated
          @RequestBody
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              description = "Data for registration of the service provided to the user")
          final PaymentPayload paymentPayload) {
    return this.bankService.requestPayment(paymentPayload);
  }

  @Operation(
      summary = "pay",
      description = "Payment of the issued invoice and the tax attached to it",
      method = "PUT")
  @PutMapping(value = "payment/{paymentId}", produces = "application/json")
  public ResponseEntity<Transaction> pay(@PathVariable("paymentId") final Long paymentId) {
    return this.bankService.payment(paymentId);
  }

  @GetMapping(value = "{paymentId}", produces = "application/json")
  @Operation(
      summary = "checkPaymentStatus",
      description = "Checking the status of invoice payment",
      method = "GET")
  public Boolean checkPaymentStatus(
      @Parameter(name = "paymentId", description = "PaymentPayload ID", required = true)
          @PathVariable("paymentId")
          final Long paymentId) {
    return this.bankService.isPaid(paymentId);
  }

  @Operation(
      summary = "getDebtPaymentRequests",
      description = "Receiving all unpaid invoices issued to the user",
      method = "GET")
  @GetMapping("check/{citizenId}")
  public List<PaymentRequest> getDebtPaymentRequests(
      @Parameter(name = "citizenId", description = "Citizen ID", required = true)
          @PathVariable("citizenId")
          Long citizenId) {
    return this.bankService.getDebtPaymentRequests(citizenId);
  }

  @Operation(
      summary = "getAllCitizenTransactions",
      description =
          "Returns a list of transactions"
              + " made during a specified period by a specific citizen.",
      method = "POST")
  @PostMapping(value = "transaction", produces = "application/json")
  public List<Transaction> getAllCitizenTransactions(
      @Validated @RequestBody final PeriodTransactions periodTransactions) {
    return this.bankService.getAllCitizenTransactions(
        periodTransactions.getStartDate(),
        periodTransactions.getEndDate(),
        periodTransactions.getCitizenId());
  }
}
