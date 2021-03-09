package nc.unc.cs.services.bank.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.constraints.Min;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.services.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bank")
@Api(value = "Bank API")
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
    @ApiOperation(
            httpMethod = "POST",
            value = "Registration of provided services",
            notes = "Registers taxes and creates an invoice for payment (PaymentRequest)",
            nickname = "requestPayment")
    @ApiImplicitParam(
            name = "paymentPayload",
            value = "Data for registration of the service provided to the user",
            required = true,
            type = "PaymentPayload",
            dataType = "PaymentPayload",
            dataTypeClass = PaymentPayload.class,
            paramType = "body")
    public ResponseEntity<Long> requestPayment(
            @Validated @RequestBody final PaymentPayload paymentPayload) {
        return this.bankService.requestPayment(paymentPayload);
    }

    @ApiOperation(
            httpMethod = "PUT",
            value = "Payment of the invoice",
            notes = "Payment of the issued invoice and the tax attached to it",
            response = Transaction.class,
            nickname = "pay")
    @PutMapping(value = "payment/{paymentId}", produces = "application/json")
    public ResponseEntity<Transaction> pay(
            @ApiParam(
                            name = "paymentId",
                            type = "long",
                            value = "PaymentPayload ID",
                            required = true)
                    @Min(value = 1L)
                    @PathVariable("paymentId")
                    final Long paymentId) {
        return this.bankService.payment(paymentId);
    }

    @GetMapping(value = "{paymentId}", produces = "application/json")
    @ApiOperation(
            httpMethod = "GET",
            value = "Checking the status of invoice payment.",
            nickname = "checkPaymentStatus")
    public Boolean checkPaymentStatus(
            @ApiParam(
                            name = "paymentId",
                            type = "long",
                            value = "PaymentPayload ID",
                            required = true)
                    @PathVariable("paymentId")
                    final Long paymentId) {
        return this.bankService.isPaid(paymentId);
    }

    @ApiOperation(
            httpMethod = "GET",
            value = "Receiving all unpaid invoices issued to the user",
            nickname = "getDebtPaymentRequests",
            response = PaymentRequest.class,
            responseContainer = "List")
    @GetMapping("check/{citizenId}")
    public List<PaymentRequest> getDebtPaymentRequests(
            @ApiParam(name = "citizenId", type = "long", value = "Citizen ID", required = true)
                    @PathVariable("citizenId")
                    Long citizenId) {
        return this.bankService.getDebtPaymentRequests(citizenId);
    }

    // добавить методов по извлечению транзакций и неоплаченных счетов из БД (по
    // датам, статусу и тп)
}
