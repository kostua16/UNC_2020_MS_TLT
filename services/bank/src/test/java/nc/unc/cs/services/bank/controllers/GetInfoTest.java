package nc.unc.cs.services.bank.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.services.BankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BankController.class})
@Import(ObjectMapper.class)
public class GetInfoTest {
  private static final String BANK_CONTROLLER_MAPPING = "http://localhost:8084/bank/";
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private BankService bankService;

  @Test
  void checkPaymentStatusTest() throws Exception {
    final Long paymentId = 1L;
    when(this.bankService.isPaid(paymentId))
        .thenReturn(Boolean.TRUE);

    this.mockMvc
        .perform(
            get(BANK_CONTROLLER_MAPPING + paymentId)
                .contentType("application/json")
        )
        .andDo(print())
        .andExpect(status().isOk());
  }
  @Test
  void getDebtPaymentRequestsTest() throws Exception {
    final List<PaymentRequest> list = new ArrayList<>();
    Collections.addAll(list, new PaymentRequest(), new PaymentRequest());
    final Long citizenId = 111L;
    when(this.bankService.getDebtPaymentRequests(citizenId))
        .thenReturn(list);

    this.mockMvc
        .perform(
            get(BANK_CONTROLLER_MAPPING + "check/" + citizenId)
                .contentType("application/json")
        )
        .andDo(print())
        .andExpect(status().isOk());
  }
}
