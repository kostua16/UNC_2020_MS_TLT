package nc.unc.cs.services.communal.controllers.mock.property.correct;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import nc.unc.cs.services.communal.controllers.mock.property.PropertyParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationProperty;
import nc.unc.cs.services.communal.entities.Property;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

@AutoConfigureRestDocs
class AddPropertyWebTest extends PropertyParentWeb {

  private static final Logger logger = LoggerFactory.getLogger(AddPropertyWebTest.class);

  private static final FieldDescriptor REGION_DESCR =
      fieldWithPath("region").type(String.class).description("Address region.");
  private static final FieldDescriptor CITY_DESCR =
      fieldWithPath("city").type(String.class).description("Address city.");
  private static final FieldDescriptor STREET_DESCR =
      fieldWithPath("street").type(String.class).description("Address street.");
  private static final FieldDescriptor HOUSE_DESCR =
      fieldWithPath("house").type(String.class).description("Address house.");
  private static final FieldDescriptor APARTMENT_DESCR =
      fieldWithPath("apartment").type(String.class).description("Address apartment.");
  private static final FieldDescriptor APARTMENT_SIZE_DESCR =
      fieldWithPath("apartmentSize").type(Integer.class).description("Address size.");
  private static final FieldDescriptor CITIZEN_ID_DESCR =
      fieldWithPath("citizenId").type(Long.class).description("ID of the citizen.");
  private static final FieldDescriptor PROPERTY_ID_DESCR =
      fieldWithPath("propertyId").type(Long.class).description("ID of the property.");
  private static final FieldDescriptor PROPERTY_TAX_DESCR =
      fieldWithPath("propertyTaxDate").type(Date.class).description("Last tax deduction.");

  private static final FieldDescriptor[] PROPERTY_DESCR =
      new FieldDescriptor[] {
        AddPropertyWebTest.REGION_DESCR,
        AddPropertyWebTest.CITY_DESCR,
        AddPropertyWebTest.STREET_DESCR,
        AddPropertyWebTest.HOUSE_DESCR,
        AddPropertyWebTest.APARTMENT_DESCR,
        AddPropertyWebTest.APARTMENT_SIZE_DESCR,
        AddPropertyWebTest.CITIZEN_ID_DESCR
      };
  private static final FieldDescriptor[] PROPERTY_REQ_DESCR =
      new FieldDescriptor[] {
        AddPropertyWebTest.REGION_DESCR,
        AddPropertyWebTest.CITY_DESCR,
        AddPropertyWebTest.STREET_DESCR,
        AddPropertyWebTest.HOUSE_DESCR,
        AddPropertyWebTest.APARTMENT_DESCR,
        AddPropertyWebTest.APARTMENT_SIZE_DESCR,
        AddPropertyWebTest.PROPERTY_ID_DESCR,
        AddPropertyWebTest.PROPERTY_TAX_DESCR,
        AddPropertyWebTest.CITIZEN_ID_DESCR
      };

  private static final RequestFieldsSnippet PROPERTY_REQ =
      requestFields(AddPropertyWebTest.PROPERTY_DESCR);
  private static final ResponseFieldsSnippet PROPERTY_RESP =
      responseFields(AddPropertyWebTest.PROPERTY_REQ_DESCR);

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
        .andDo(
            document(
                "addCitizensPropertyTest",
                AddPropertyWebTest.PROPERTY_REQ,
                AddPropertyWebTest.PROPERTY_RESP))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            content().string(containsString(this.objectMapper.writeValueAsString(property))));
  }
}
