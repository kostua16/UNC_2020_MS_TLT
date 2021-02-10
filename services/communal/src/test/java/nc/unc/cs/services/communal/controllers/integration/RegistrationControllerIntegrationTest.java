package nc.unc.cs.services.communal.controllers.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationControllerIntegrationTest.class);
    private static final String REGISTRATION_CONTROLLER_MAPPING = "http://localhost:8083/communal/housing";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    public void addCitizensPropertyTest() throws Exception {
        Property property = new Property();
        property.setRegion("  samara ");
        property.setCity("samara");
        property.setStreet("main");
        property.setHouse("15A");
        property.setApartment("144");
        property.setApartmentSize(200);
        property.setCitizenId(111L);

        mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING + "/property/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(property)))
            .andExpect(status().isOk());

        Property propertyBD = this.propertyRepository.findPropertyByCitizenId(111L).get(0);
        Assertions.assertEquals(propertyBD.getRegion(), property.getRegion().trim().toUpperCase());
//        Assertions.assertEquals(propertyBD.getApartmentSize(), property.getApartmentSize());
    }
}
