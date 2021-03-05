package nc.unc.cs.services.communal.controllers.mock.property.correct;

import nc.unc.cs.services.communal.controllers.mock.property.PropertyParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationProperty;
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

class AddPropertyWebTest extends PropertyParentWeb {

    private static final Logger logger = LoggerFactory.getLogger(AddPropertyWebTest.class);

    @Test
    void addCitizensPropertyTest() throws Exception {
        final CreationProperty creationProperty = this.createCreationProperty();
        final Property property = this.createProperty();
        logger.debug("Property Object: \n {} \n", property);

        when(registrationService.addCitizensProperty(creationProperty)).thenReturn(ResponseEntity.ok(property));

        this.mockMvc.perform(post(PROPERTY_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(creationProperty)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(this.objectMapper.writeValueAsString(property))));
    }
}
