package nc.unc.cs.services.bank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.bank.services.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BankController.class})
@Import(ObjectMapper.class)
class RequestPaymentTest {
  private static final String BANK_CONTROLLER_MAPPING = "http://localhost:8084/bank/request-payment";
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private BankService bankService;

  private PaymentPayload createPaymentPayload() {
    return PaymentPayload
        .builder()
        .serviceId(15L)
        .citizenId(111L)
        .amount(1000)
        .taxAmount(100)
        .build();
  }

  @Test
  void requestPaymentTest() throws Exception {
    final PaymentPayload paymentPayload = this.createPaymentPayload();
    when(this.bankService.requestPayment(paymentPayload))
        .thenReturn(ResponseEntity.ok(1L));

    this.mockMvc
        .perform(
            post(BANK_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(paymentPayload)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(this.objectMapper.writeValueAsString(1L))));
  }
}
