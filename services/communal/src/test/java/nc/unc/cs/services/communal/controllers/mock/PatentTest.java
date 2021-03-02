package nc.unc.cs.services.communal.controllers.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.communal.controllers.RegistrationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {RegistrationController.class})
@Import(ObjectMapper.class)
public class PatentTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
}
