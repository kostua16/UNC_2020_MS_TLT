package nc.unc.cs.services.communal.controllers.mock.registration;

import nc.unc.cs.services.communal.controllers.mock.PropertyAndRegistrationParentWebTest;
import nc.unc.cs.services.communal.entities.Registration;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NumericalData extends PropertyAndRegistrationParentWebTest {
    @Test
    public void smallestCitizenId() throws Exception {
        Registration registration = Registration
            .builder()
            .registrationId(1L)
            .region("samara ")
            .city("samara")
            .street("main")
            .house("1b")
            .apartment("12a")
            .isActive(false)
            .citizenId(0L)
            .build();

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(registration)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void nullCitizenId() throws Exception {
        Registration registration = Registration
            .builder()
            .registrationId(1L)
            .region("samara ")
            .city("samara")
            .street("main")
            .house("1b")
            .apartment("12a")
            .isActive(false)
            .citizenId(null)
            .build();

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(registration)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
