package nc.unc.cs.services.communal.controllers.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.communal.controllers.RegistrationController;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {RegistrationController.class})
@Import(ObjectMapper.class)
public class RegistrationServiceTest {
    protected static final String REGISTRATION_CONTROLLER_MAPPING = "http://localhost:8083/communal/housing";
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected RegistrationService registrationService;

    public Property createProperty() {
        return Property.builder()
            .propertyId(1L)
            .region("Samara")
            .city("Samara")
            .street("main")
            .house("111")
            .apartment("1b")
            .apartmentSize(1000)
            .citizenId(111L)
            .build();
    }
}
