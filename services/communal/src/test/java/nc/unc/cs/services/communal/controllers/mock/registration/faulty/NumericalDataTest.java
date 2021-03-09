package nc.unc.cs.services.communal.controllers.mock.registration.faulty;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.registration.RegistrationParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationRegistration;
import org.junit.jupiter.api.Test;

class NumericalDataTest extends RegistrationParentWeb {
    @Test
    void smallestCitizenId() throws Exception {
        final CreationRegistration registration = this.createCreationRegistration();
        registration.setCitizenId(0L);

        this.mockMvc
                .perform(
                        post(REGISTRATION_CONTROLLER_MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(registration)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void nullCitizenId() throws Exception {
        final CreationRegistration registration = this.createCreationRegistration();
        registration.setCitizenId(null);

        this.mockMvc
                .perform(
                        post(REGISTRATION_CONTROLLER_MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(registration)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
