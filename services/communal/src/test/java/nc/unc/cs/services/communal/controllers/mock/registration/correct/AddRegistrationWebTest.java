package nc.unc.cs.services.communal.controllers.mock.registration.correct;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.registration.RegistrationParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationRegistration;
import nc.unc.cs.services.communal.entities.Registration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

@AutoConfigureRestDocs
class AddRegistrationWebTest extends RegistrationParentWeb {

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
  private static final FieldDescriptor CITIZEN_ID_DESCR =
      fieldWithPath("citizenId").type(Long.class).description("ID of the citizen.");
  private static final FieldDescriptor REGISTRATION_ID_DESCR =
      fieldWithPath("registrationId").type(Long.class).description("ID of the registration.");
  private static final FieldDescriptor IS_ACTIVE_DESCR =
      fieldWithPath("isActive").type(Boolean.class).description("Registration active status.");

  private static final FieldDescriptor[] REGISTRATION_DESCR =
      new FieldDescriptor[] {
        AddRegistrationWebTest.REGION_DESCR,
        AddRegistrationWebTest.CITY_DESCR,
        AddRegistrationWebTest.STREET_DESCR,
        AddRegistrationWebTest.HOUSE_DESCR,
        AddRegistrationWebTest.APARTMENT_DESCR,
        AddRegistrationWebTest.CITIZEN_ID_DESCR
      };
  private static final FieldDescriptor[] REG_RESP_DESCR =
      new FieldDescriptor[] {
        AddRegistrationWebTest.REGION_DESCR,
        AddRegistrationWebTest.CITY_DESCR,
        AddRegistrationWebTest.STREET_DESCR,
        AddRegistrationWebTest.HOUSE_DESCR,
        AddRegistrationWebTest.APARTMENT_DESCR,
        AddRegistrationWebTest.REGISTRATION_ID_DESCR,
        AddRegistrationWebTest.IS_ACTIVE_DESCR,
        AddRegistrationWebTest.CITIZEN_ID_DESCR
      };

  private static final RequestFieldsSnippet REGISTRATION_REQ =
      requestFields(AddRegistrationWebTest.REGISTRATION_DESCR);
  private static final ResponseFieldsSnippet REGISTRATION_RESP =
      responseFields(AddRegistrationWebTest.REG_RESP_DESCR);

  @Test
  public void addRegistrationTest() throws Exception {
    final CreationRegistration newRegistration = this.createCreationRegistration();
    final Registration registration = this.createRegistration();

    when(this.registrationService.addRegistration(newRegistration))
        .thenReturn(ResponseEntity.ok(registration));

    this.mockMvc
        .perform(
            post(REGISTRATION_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newRegistration)))
        .andDo(
            document(
                "addRegistrationTest",
                AddRegistrationWebTest.REGISTRATION_REQ,
                AddRegistrationWebTest.REGISTRATION_RESP))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.region", Matchers.is(newRegistration.getRegion())));
  }
}
