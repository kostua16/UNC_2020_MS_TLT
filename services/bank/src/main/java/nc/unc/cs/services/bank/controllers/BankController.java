package nc.unc.cs.services.bank.controllers;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.services.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
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
@Api(value = "Bank API")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(final BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping(value = "request-payment", consumes = "application/json", produces = "application/json")
    @ApiOperation(
        httpMethod = "POST",
        value = "Registration of provided services",
        notes = "Registers taxes and creates an invoice for payment (PaymentRequest)",
//        response = ResponseEntity.class,
        nickname = "requestPayment"
    )
    @ApiResponses({
        @ApiResponse(code = 500, message = "PaymentRequest with ID = null", response = PaymentRequest.class),
        @ApiResponse(code = 503, message = "PaymentRequest with ID = null", response = PaymentRequest.class)
    })
    @ApiImplicitParam(
        name = "paymentPayload",
        value = "Data for registration of the service provided to the user",
        required = true,
        type = "PaymentPayload",
        dataType = "PaymentPayload",
        dataTypeClass = PaymentPayload.class,
        paramType = "body"
    )
    public ResponseEntity<Long> requestPayment(
        @RequestBody final PaymentPayload paymentPayload
    ) {
        return this.bankService.requestPayment(
            paymentPayload.getServiceId(),
            paymentPayload.getCitizenId(),
            paymentPayload.getAmount(),
            paymentPayload.getTaxAmount()
        );
    }

    @ApiOperation(
        httpMethod = "PUT",
        value = "Payment of the invoice",
        notes = "Payment of the issued invoice and the tax attached to it",
        response = Transaction.class,
        nickname = "pay"
    )
    @ApiResponses({
        @ApiResponse(code = 400, message = "Uncreated transaction", response = Transaction.class),
        @ApiResponse(code = 500, message = "Uncreated transaction", response = Transaction.class),
        @ApiResponse(code = 503, message = "Uncreated transaction", response = Transaction.class)
    })
    @PutMapping(value = "payment/{paymentId}", produces = "application/json")
    public ResponseEntity<Transaction> pay(
        @ApiParam(name = "paymentId", type = "long", value = "PaymentPayload ID", required = true)
        @PathVariable("paymentId") final Long paymentId
    ) {
        return this.bankService.payment(paymentId);
    }

    @GetMapping(value = "{paymentId}", produces = "application/json")
    @ApiOperation(
        httpMethod = "GET",
        value = "Checking the status of invoice payment.",
        nickname = "checkPaymentStatus"
    )
    public Boolean checkPaymentStatus(
        @ApiParam(name = "paymentId", type = "long", value = "PaymentPayload ID", required = true)
        @PathVariable("paymentId") final Long paymentId
    ) {
        return this.bankService.isPaid(paymentId);
    }

    @ApiOperation(
        httpMethod = "GET",
        value = "Receiving all unpaid invoices issued to the user",
        nickname = "getDebtPaymentRequests",
        response = PaymentRequest.class,
        responseContainer = "List"
    )
    @GetMapping("check/{citizenId}")
    public List<PaymentRequest> getDebtPaymentRequests(
        @ApiParam(name = "citizenId", type = "long", value = "Citizen ID", required = true)
        @PathVariable("citizenId") Long citizenId
    ) {
        return this.bankService.getDebtPaymentRequests(citizenId);
    }

    // добавить методов по извлечению транзакций и неоплаченных счетов из БД (по датам, статусу и тп)
}
