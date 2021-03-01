package nc.unc.cs.services.tax.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import nc.unc.cs.services.tax.controllers.TaxController;
import nc.unc.cs.services.tax.entities.Tax;
import nc.unc.cs.services.tax.services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {TaxController.class})
@Import(ObjectMapper.class)
public class TaxWebTest {
    protected static final String TAX_CONTROLLER_MAPPING = "http://localhost:8082/tax";
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected TaxService taxService;
}
