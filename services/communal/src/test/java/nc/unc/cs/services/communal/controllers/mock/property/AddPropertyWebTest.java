package nc.unc.cs.services.communal.controllers.mock.property;

import nc.unc.cs.services.communal.controllers.mock.PropertyAndRegistrationParentWebTest;
import nc.unc.cs.services.communal.entities.Property;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddPropertyWebTest extends PropertyAndRegistrationParentWebTest {

    private static final Logger logger = LoggerFactory.getLogger(AddPropertyWebTest.class);

    @Test
    public void addCitizensPropertyTest() throws Exception {
        final Property property = this.createProperty();
        logger.debug("Property Object: \n {} \n", property);

        when(registrationService.addCitizensProperty(property)).thenReturn(ResponseEntity.ok(property));

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(this.objectMapper.writeValueAsString(property))));
    }
}
