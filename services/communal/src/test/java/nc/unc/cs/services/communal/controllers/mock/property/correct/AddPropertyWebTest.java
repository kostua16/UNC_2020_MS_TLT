package nc.unc.cs.services.communal.controllers.mock.property.correct;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.property.PropertyParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationProperty;
import nc.unc.cs.services.communal.entities.Property;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

class AddPropertyWebTest extends PropertyParentWeb {

    private static final Logger logger = LoggerFactory.getLogger(AddPropertyWebTest.class);

  private static final FieldDescriptor ADD_REGION_DESCR =
      fieldWithPath("region").type(String.class).description("region where the property is located");

  private static final FieldDescriptor ADD_CITY_DESCR =
      fieldWithPath("city").type(String.class).description("city where the property is located");

  private static final FieldDescriptor[] PROPERTY_DESCR =
      new FieldDescriptor[] {AddPropertyWebTest.ADD_REGION_DESCR, AddPropertyWebTest.ADD_CITY_DESCR};

  private static final ResponseFieldsSnippet PROPERTY_RESP = responseFields(AddPropertyWebTest.PROPERTY_DESCR);


    @Test
    void addCitizensPropertyTest() throws Exception {
        final CreationProperty creationProperty = this.createCreationProperty();
        final Property property = this.createProperty();
        logger.debug("Property Object: \n {} \n", property);

        when(registrationService.addCitizensProperty(creationProperty))
                .thenReturn(ResponseEntity.ok(property));

        this.mockMvc
                .perform(
                        post(PROPERTY_CONTROLLER_MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(creationProperty)))
                .andDo(document("addCitizensProperty", PROPERTY_RESP))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                this.objectMapper.writeValueAsString(property))));
    }
}
