package nc.unc.cs.services.communal.controllers.mock;

import nc.unc.cs.services.communal.controllers.RegistrationController;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.Registration;
import nc.unc.cs.services.communal.services.RegistrationService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {RegistrationController.class})
public class PropertyAndRegistrationParentWebTest extends ParentWebTest {
    protected static final String REGISTRATION_CONTROLLER_MAPPING = "http://localhost:8083/communal/housing";
    @MockBean
    protected RegistrationService registrationService;

    public final Property createProperty() {
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

    public final Registration createRegistration() {
        return Registration.builder()
            .registrationId(1L)
            .region("Samara")
            .city("Samara")
            .street("main")
            .house("111")
            .apartment("1b")
            .isActive(true)
            .citizenId(111L)
            .build();
    }
}
