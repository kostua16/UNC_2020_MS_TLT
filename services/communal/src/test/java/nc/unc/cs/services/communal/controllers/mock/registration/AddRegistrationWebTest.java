package nc.unc.cs.services.communal.controllers.mock.registration;

import nc.unc.cs.services.communal.controllers.mock.PropertyAndRegistrationParentWeb;
import nc.unc.cs.services.communal.entities.Registration;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AddRegistrationWebTest extends PropertyAndRegistrationParentWeb {

    @Test
    public void addRegistrationTest() throws Exception {
        Registration registration = this.createRegistration();

        when(this.registrationService.addRegistration(registration)).thenReturn(ResponseEntity.ok(registration));

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(registration)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(this.objectMapper.writeValueAsString(registration))));
    }
}
