package nc.unc.cs.services.bank.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.services.BankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {BankController.class})
@Import(ObjectMapper.class)
class PayTest {
  private static final String BANK_CONTROLLER_MAPPING = "http://localhost:8084/bank/payment/";
  @Autowired private MockMvc mockMvc;
  //  @Autowired
  //  private ObjectMapper objectMapper;
  @MockBean private BankService bankService;

  private Transaction createTransaction() {
    return Transaction.builder()
        .transactionId(1L)
        .citizenId(111L)
        .paymentRequestId(15L)
        .amount(1000)
        .build();
  }

  @Test
  void payTest() throws Exception {
    final Long paymentId = 1L;
    final Transaction transaction = this.createTransaction();
    when(this.bankService.payment(paymentId)).thenReturn(ResponseEntity.ok(transaction));

    this.mockMvc
        .perform(put(BANK_CONTROLLER_MAPPING + paymentId).contentType("application/json"))
        .andDo(document("payTest"))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
