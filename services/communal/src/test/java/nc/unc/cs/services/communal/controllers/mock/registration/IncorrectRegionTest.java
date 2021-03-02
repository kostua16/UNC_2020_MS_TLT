package nc.unc.cs.services.communal.controllers.mock.registration;

import nc.unc.cs.services.communal.controllers.mock.PropertyAndRegistrationParentWeb;
import nc.unc.cs.services.communal.entities.Registration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IncorrectRegionTest extends PropertyAndRegistrationParentWeb {

    @ParameterizedTest
    @ValueSource(
        strings = {
            "   ",
            " 1 ",
            "sssssssssssssssssssssssssssssssssssssssss"
        }
    )
    void checkRegionNameTest(final String word) throws Exception {
        Registration registration = this.createRegistration();
        registration.setRegion(word);

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(registration)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}