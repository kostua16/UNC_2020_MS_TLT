package nc.unc.cs.services.tax.controllers.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.tax.controllers.TaxController;
import nc.unc.cs.services.tax.services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {TaxController.class})
@Import(ObjectMapper.class)
class TaxWeb {
  static final String TAX_CONTROLLER_MAPPING = "http://localhost:8082/tax";
  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;
  @MockBean TaxService taxService;
}
