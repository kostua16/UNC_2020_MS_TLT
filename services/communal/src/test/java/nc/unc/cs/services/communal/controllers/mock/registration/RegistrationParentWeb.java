package nc.unc.cs.services.communal.controllers.mock.registration;

import nc.unc.cs.services.communal.controllers.RegistrationController;
import nc.unc.cs.services.communal.controllers.mock.ParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationRegistration;
import nc.unc.cs.services.communal.entities.Registration;
import nc.unc.cs.services.communal.services.RegistrationService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {RegistrationController.class})
public class RegistrationParentWeb extends ParentWeb {
  protected static final String REGISTRATION_CONTROLLER_MAPPING =
      "http://localhost:8083/communal/registration";
  @MockBean protected RegistrationService registrationService;

  protected final CreationRegistration createCreationRegistration() {
    return CreationRegistration.builder()
        .region("Samara")
        .city("Samara")
        .street("main")
        .house("111")
        .apartment("1b")
        .citizenId(111L)
        .build();
  }

  protected final Registration createRegistration() {
    final CreationRegistration creationRegistration = this.createCreationRegistration();
    return Registration.builder()
        .region(creationRegistration.getRegion())
        .city(creationRegistration.getCity())
        .street(creationRegistration.getStreet())
        .house(creationRegistration.getHouse())
        .apartment(creationRegistration.getApartment())
        .isActive(true)
        .citizenId(creationRegistration.getCitizenId())
        .build();
  }
}
