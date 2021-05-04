package nc.unc.cs.services.bank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.bank.services.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BankController.class})
@Import(ObjectMapper.class)
@AutoConfigureRestDocs
class RequestPaymentTest {
  private static final FieldDescriptor AMOUNT_DESCR =
      fieldWithPath("amount").type(Integer.class).description("Service cost.");
  private static final FieldDescriptor TAX_AMOUNT_DESCR =
      fieldWithPath("taxAmount").type(Integer.class).description("Tax amount.");
  private static final FieldDescriptor SERVICE_ID_DESCR =
      fieldWithPath("serviceId").type(Long.class).description("ID of the service.");
  private static final FieldDescriptor CITIZEN_ID_DESCR =
      fieldWithPath("citizenId").type(Long.class).description("ID of the citizen.");

  private static final FieldDescriptor[] PAYMENT_REQUEST_DESCR =
      new FieldDescriptor[]{
          RequestPaymentTest.AMOUNT_DESCR,
          RequestPaymentTest.TAX_AMOUNT_DESCR,
          RequestPaymentTest.SERVICE_ID_DESCR,
          RequestPaymentTest.CITIZEN_ID_DESCR
      };

  private static final RequestFieldsSnippet REQUEST_PAYMENT_REQ =
      requestFields(RequestPaymentTest.PAYMENT_REQUEST_DESCR);

  private static final String BANK_CONTROLLER_MAPPING =
      "http://localhost:8084/bank/request-payment";
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private BankService bankService;

  private PaymentPayload createPaymentPayload() {
    return PaymentPayload.builder()
        .serviceId(15L)
        .citizenId(111L)
        .amount(1000)
        .taxAmount(100)
        .build();
  }

  @Test
  void requestPaymentTest() throws Exception {
    final PaymentPayload paymentPayload = this.createPaymentPayload();
    when(this.bankService.requestPayment(paymentPayload)).thenReturn(ResponseEntity.ok(1L));

    this.mockMvc
        .perform(
            post(BANK_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(paymentPayload)))
        .andDo(
            document(
                "requestPaymentTest",
                RequestPaymentTest.REQUEST_PAYMENT_REQ
            )
        )
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(this.objectMapper.writeValueAsString(1L))));
  }
}
