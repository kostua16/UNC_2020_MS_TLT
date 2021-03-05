package nc.unc.cs.services.communal.controllers.mock.property.faulty;

import nc.unc.cs.services.communal.controllers.mock.property.PropertyParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IncorrectStreetTest extends PropertyParentWeb {

    @ParameterizedTest
    @ValueSource(
        strings = {
            "   ",
            " 1 ",
            "sssssssssssssssssssssssssssssssssssssssss"
        }
    )
    void checkStreetNameTest(final String word) throws Exception {
        final CreationProperty property = this.createCreationProperty();
        property.setStreet(word);

        this.mockMvc.perform(post(PROPERTY_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}