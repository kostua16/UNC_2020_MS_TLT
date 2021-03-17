package nc.unc.cs.services.bank.services;

import nc.unc.cs.services.bank.repositories.PaymentRequestRepository;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
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
class RequestPaymentTestTest {
  @Mock
  private PaymentRequestRepository paymentRequestRepository;
  @Mock
  private LoggingService logging;
  @Mock
  private TaxIntegrationService taxIntegrationService;
  @InjectMocks
  private BankService bankService;

  private PaymentPayload createPaymentPayload() {
    return PaymentPayload
        .builder()
        .serviceId(20L)
        .citizenId(111L)
        .amount(10000)
        .taxAmount(1000)
        .build();
  }

  @Test
  void requestPaymentCorrectTest() {
    final Long testTaxId = 1L;
    final PaymentPayload paymentPayload = this.createPaymentPayload();

    given(this.taxIntegrationService
        .createTax(
            paymentPayload.getServiceId(),
            paymentPayload.getCitizenId(),
            paymentPayload.getTaxAmount()
        ))
        .willReturn(testTaxId);

    given(this.logging.addLog(any()))
        .willAnswer(invocation -> invocation.getArgument(0));
    given(this.paymentRequestRepository.save(any()))
        .willAnswer(invocation -> invocation.getArgument(0));

    ResponseEntity<Long> response = this.bankService.requestPayment(paymentPayload);
    System.out.println(response);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
