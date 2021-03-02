package nc.unc.cs.services.communal.controllers.mock.property;

import nc.unc.cs.services.communal.controllers.mock.PropertyAndRegistrationParentWeb;
import nc.unc.cs.services.communal.entities.Property;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IncorrectApartmentTest extends PropertyAndRegistrationParentWeb {

    @ParameterizedTest
    @ValueSource(
        strings = {
            "   ",
            " 1 ",
            "sssssssssssssssssssssssssssssssssssssssss"
        }
    )
    void checkApartmentNameTest(final String word) throws Exception {
        Property property = this.createProperty();
        property.setApartment(word);

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
