package nc.unc.cs.services.bank.services;

import java.util.Optional;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.exceptions.PaymentRequestNotFoundException;
import nc.unc.cs.services.bank.repositories.PaymentRequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class FindDataTest {
    @Mock
    private PaymentRequestRepository paymentRequestRepository;
    @InjectMocks
    private BankService bankService;

    @Test
    void findPaymentRequestByIdTest() {
        final PaymentRequest paymentRequest = PaymentRequest
            .builder()
            .paymentRequestId(1L)
            .amount(10000)
            .citizenId(111L)
            .serviceId(20L)
            .taxId(1L)
            .build();

        given(this.paymentRequestRepository.findById(paymentRequest.getPaymentRequestId()))
            .willReturn(Optional.of(paymentRequest));

        final PaymentRequest response =
            this.bankService.findPaymentRequestById(paymentRequest.getPaymentRequestId());

        Assertions.assertEquals(
            paymentRequest.getPaymentRequestId(),
            response.getPaymentRequestId()
        );
    }

    @Test
    void findPaymentRequestByIdExceptionTest() {
        given(this.paymentRequestRepository.findById(1L))
            .willThrow(new PaymentRequestNotFoundException(1L));


        Assertions.assertThrows(
            PaymentRequestNotFoundException.class,
            () -> this.bankService.findPaymentRequestById(1L)
        );
    }
}
