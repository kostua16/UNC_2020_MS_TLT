package nc.unc.cs.services.communal.controllers.mock.registration.correct;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.registration.RegistrationParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationRegistration;
import nc.unc.cs.services.communal.entities.Registration;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class AddRegistrationWebTest extends RegistrationParentWeb {

  @Test
  public void addRegistrationTest() throws Exception {
    final CreationRegistration newRegistration =
        this.createCreationRegistration();
    final Registration registration = this.createRegistration();

    when(this.registrationService.addRegistration(newRegistration))
        .thenReturn(ResponseEntity.ok(registration));

    this.mockMvc
        .perform(post(REGISTRATION_CONTROLLER_MAPPING)
                     .contentType("application/json")
                     .content(objectMapper.writeValueAsString(newRegistration)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(
            this.objectMapper.writeValueAsString(registration))));
  }
}
