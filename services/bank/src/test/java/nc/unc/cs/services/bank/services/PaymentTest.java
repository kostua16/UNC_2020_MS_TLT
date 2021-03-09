package nc.unc.cs.services.bank.services;

import java.util.Date;
import java.util.Optional;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.repositories.PaymentRequestRepository;
import nc.unc.cs.services.bank.repositories.TransactionRepository;
import nc.unc.cs.services.common.clients.logging.LoggingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class PaymentTest {
    @Mock
    private PaymentRequestRepository paymentRequestRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private LoggingService logging;
    @Mock
    private TaxIntegrationService taxIntegrationService;
    @InjectMocks
    private BankService bankService;

    private PaymentRequest createPaymentRequest() {
        return PaymentRequest
            .builder()
            .paymentRequestId(1L)
            .amount(10000)
            .citizenId(111L)
            .serviceId(20L)
            .taxId(1L)
            .build();
    }

    @Test
    void paymentCorrectTest() {
        final PaymentRequest paymentRequest = this.createPaymentRequest();

        given(this.paymentRequestRepository.findById(paymentRequest.getPaymentRequestId()))
            .willReturn(Optional.of(paymentRequest));

        given(
            this.taxIntegrationService.payTax(
                paymentRequest.getTaxId(),
                new Date()
            ))
            .willReturn(ResponseEntity.ok(1L));

        given(this.logging.addLog(any()))
            .willAnswer(invocation -> invocation.getArgument(0));
        given(this.transactionRepository.save(any()))
            .willAnswer(invocation -> invocation.getArgument(0));
        given(this.paymentRequestRepository.save(any()))
            .willAnswer(invocation -> invocation.getArgument(0));

        final ResponseEntity<Transaction> response =
            this.bankService.payment(paymentRequest.getPaymentRequestId());

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertEquals(paymentRequest.getAmount(), response.getBody().getAmount()),
            () -> Assertions.assertEquals(
                paymentRequest.getPaymentRequestId(),
                response.getBody().getPaymentRequestId()
            ),
            () -> Assertions.assertEquals(paymentRequest.getCitizenId(), response.getBody().getCitizenId()),
            () -> Assertions.assertTrue(paymentRequest.getStatus())
        );
    }
}
