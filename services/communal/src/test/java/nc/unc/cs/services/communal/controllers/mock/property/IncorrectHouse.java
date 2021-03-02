package nc.unc.cs.services.communal.controllers.mock.property;

import nc.unc.cs.services.communal.controllers.mock.IncorrectDataTests;
import nc.unc.cs.services.communal.controllers.mock.PropertyAndRegistrationParentWebTest;
import nc.unc.cs.services.communal.entities.Property;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IncorrectHouse extends PropertyAndRegistrationParentWebTest implements IncorrectDataTests {

    private static final Logger logger = LoggerFactory.getLogger(IncorrectHouse.class);

    @Test
    @Override
    public void blankData() throws Exception {
        Property property = this.createProperty();
        property.setHouse("  ");

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @Override
    public void smallestSizeData() throws Exception {
        Property property = this.createProperty();
        property.setHouse(" 1 ");

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @Override
    public void oversizeData() throws Exception {
        Property property = this.createProperty();
        property.setHouse("asdsdsdgqsr1rvxzxgadgasfasfasdasdasdasdasdasdasdasdasdasd");

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
