package nc.unc.cs.services.bank.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import java.util.List;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.services.BankService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {BankController.class})
@Import(ObjectMapper.class)
@AutoConfigureRestDocs
public class GetInfoTest {
  private static final FieldDescriptor AMOUNT_DESCR =
      fieldWithPath("amount").type(Integer.class).description("Service cost.");
  private static final FieldDescriptor SERVICE_ID_DESCR =
      fieldWithPath("serviceId").type(Long.class).description("ID of the service.");
  private static final FieldDescriptor CITIZEN_ID_DESCR =
      fieldWithPath("citizenId").type(Long.class).description("ID of the citizen.");
  private static final FieldDescriptor PAYMENT_ID_DESCR =
      fieldWithPath("paymentRequestId").type(Long.class).description("ID of the payment request.");
  private static final FieldDescriptor STATUS_DESCR =
      fieldWithPath("status").type(Boolean.class).description("Payment status.");
  private static final FieldDescriptor TAX_ID_DESCR =
      fieldWithPath("taxId").type(Long.class).description("ID of the tax");

  private static final FieldDescriptor[] PAYMENT_REQUEST_DESCR =
      new FieldDescriptor[] {
        GetInfoTest.AMOUNT_DESCR,
        GetInfoTest.SERVICE_ID_DESCR,
        GetInfoTest.CITIZEN_ID_DESCR,
        GetInfoTest.PAYMENT_ID_DESCR,
        GetInfoTest.STATUS_DESCR,
        GetInfoTest.TAX_ID_DESCR
      };

  private static final ResponseFieldsSnippet PAYMENT_REQUEST_RESP =
      responseFields(GetInfoTest.PAYMENT_REQUEST_DESCR);
  private static final ResponseFieldsSnippet PAYMENT_REQUESTS_RESP =
      responseFields(fieldWithPath("[]").description("PaymentRequests"))
          .andWithPrefix("[].", GetInfoTest.PAYMENT_REQUEST_DESCR);

  private static final String BANK_CONTROLLER_MAPPING = "http://localhost:8084/bank/";
  @Autowired private MockMvc mockMvc;
  @MockBean private BankService bankService;

  @Test
  void checkPaymentStatusTest() throws Exception {
    final Long paymentId = 1L;
    when(this.bankService.isPaid(paymentId)).thenReturn(Boolean.TRUE);

    this.mockMvc
        .perform(
            get(BANK_CONTROLLER_MAPPING + "/{paymentId}", paymentId)
                .contentType("application/json"))
        //        .andDo(
        //            document(
        //                "checkPaymentStatusTest", PAYMENT_REQUEST_RESP))
        //                requestFields(
        //                    fieldWithPath("requestPaymentId").description("Request payment id.")
        //                ),
        //                responseFields(
        //                    fieldWithPath("paymentStatus").description("Payment status.")
        //                )
        //            )
        //        )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void getDebtPaymentRequestsTest() throws Exception {
    final Long citizenId = 111L;
    final PaymentRequest paymentRequest =
        PaymentRequest.builder()
            .paymentRequestId(1L)
            .serviceId(1L)
            .citizenId(citizenId)
            .amount(3333)
            .taxId(1L)
            .build();

    final List<PaymentRequest> list =
        ImmutableList.<PaymentRequest>builder().add(paymentRequest).build();

    when(this.bankService.getDebtPaymentRequests(citizenId)).thenReturn(list);

    this.mockMvc
        .perform(
            get(BANK_CONTROLLER_MAPPING + "check/{citizenId}", citizenId)
                .contentType("application/json"))
        .andDo(document("getDebtPaymentRequestsTest", GetInfoTest.PAYMENT_REQUESTS_RESP))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].amount", Matchers.is(paymentRequest.getAmount())));
  }
}
