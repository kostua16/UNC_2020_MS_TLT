package nc.unc.cs.services.communal.controllers.mock.property;

import nc.unc.cs.services.communal.controllers.mock.PropertyAndRegistrationParentWeb;
import nc.unc.cs.services.communal.entities.Property;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NumericalDataTest extends PropertyAndRegistrationParentWeb {

    @Test
    void smallestApartmentSize() throws Exception {
        Property property = this.createProperty();
        property.setApartmentSize(9);

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void nullApartmentSize() throws Exception {
        Property property =
            new Property(
                1L, "ss", "ss",
                "ss", "ss", "22",
                1000, 0L
            );

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void smallestCitizenId() throws Exception {
        Property property = this.createProperty();
        property.setApartmentSize(9);

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void nullCitizenId() throws Exception {
        Property property =
            new Property(
                1L, "ss", "ss",
                "ss", "ss", "22",
                10000, null
            );

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
