// package nc.unc.cs.services.communal.controllers.mock;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import nc.unc.cs.services.communal.controllers.RegistrationController;
// import nc.unc.cs.services.communal.entities.Property;
// import nc.unc.cs.services.communal.services.RegistrationService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Import;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.context.web.WebAppConfiguration;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;
// import static org.hamcrest.Matchers.containsString;
// import static org.mockito.Mockito.when;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = {RegistrationController.class})
//@WebAppConfiguration
//@Import(ObjectMapper.class)
// public class RegistrationControllerTest {
//
//    private static final Logger logger =
//    LoggerFactory.getLogger(RegistrationControllerTest.class); private static
//    final String REGISTRATION_CONTROLLER_MAPPING =
//    "http://localhost:8083/communal/housing";
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private RegistrationService registrationService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @BeforeEach
//    public void setup() throws Exception {
//        this.mockMvc =
//        MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
//    }
//
//    @Test
//    public void addCitizensPropertyTest() throws Exception {
//        Property property = new Property();
//        property.setRegion("  samara ");
//        property.setCity("samara");
//        property.setStreet("main");
//        property.setHouse("15A");
//        property.setApartment("144");
//        property.setApartmentSize(200);
//        property.setCitizenId(111L);
//
//        property.setPropertyId(1L);
//
//        when(registrationService.addCitizensProperty(property)).thenReturn(ResponseEntity.ok(property));
//
//        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING +
//        "/property/add")
//            .contentType("application/json")
//            .content(objectMapper.writeValueAsString(property)))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(content().string(containsString(this.objectMapper.writeValueAsString(property))));
//    }
//}
