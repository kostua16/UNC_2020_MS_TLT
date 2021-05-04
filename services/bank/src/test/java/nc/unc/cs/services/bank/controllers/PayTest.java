package nc.unc.cs.services.bank.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.services.BankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {BankController.class})
@Import(ObjectMapper.class)
@AutoConfigureRestDocs
class PayTest {
  private static final FieldDescriptor TRANSACTION_ID_DESCR =
      fieldWithPath("transactionId").type(Long.class).description("ID of the transaction.");
  private static final FieldDescriptor CITIZEN_ID_DESCR =
      fieldWithPath("citizenId").type(Long.class).description("Id of the citizen.");
  private static final FieldDescriptor PAYMENT_REQUEST_ID_DESCR =
      fieldWithPath("paymentRequestId").type(Long.class).description("ID of rhe request payment.");
  private static final FieldDescriptor CREATION_DATE_DESCR =
      fieldWithPath("creationDate").type(Long.class).description("Payment date");
  private static final FieldDescriptor AMOUNT_DESCR =
      fieldWithPath("amount").type(Long.class).description("Service cost.");

  private static final FieldDescriptor[] PAYMENT_DESCR =
      new FieldDescriptor[] {
        PayTest.TRANSACTION_ID_DESCR,
        PayTest.CITIZEN_ID_DESCR,
        PayTest.PAYMENT_REQUEST_ID_DESCR,
        PayTest.CREATION_DATE_DESCR,
        PayTest.AMOUNT_DESCR
      };

  private static final ResponseFieldsSnippet PAYMENT_RESP = responseFields(PayTest.PAYMENT_DESCR);

  private static final String BANK_CONTROLLER_MAPPING = "http://localhost:8084/bank/payment/";
  @Autowired private MockMvc mockMvc;
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
  void paymentTest() throws Exception {
    final Long paymentId = 1L;
    final Transaction transaction = this.createTransaction();
    when(this.bankService.payment(paymentId)).thenReturn(ResponseEntity.ok(transaction));

    this.mockMvc
        .perform(put(BANK_CONTROLLER_MAPPING + paymentId).contentType("application/json"))
        .andDo(document("paymentTest", PAYMENT_RESP))
        .andExpect(status().isOk());
  }
}
