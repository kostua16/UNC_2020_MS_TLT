package nc.unc.cs.services.passport.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import nc.unc.cs.services.passport.controller.dto.DomesticDto;
import nc.unc.cs.services.passport.model.Citizen;
import nc.unc.cs.services.passport.model.Domestic;
import nc.unc.cs.services.passport.service.PassportTable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {PassportController.class})
@AutoConfigureRestDocs
class UpdateDomesticTest {
  private static final String PASSPORT_CONTROLLER_MAPPING = "http://localhost:8095";
  private static final Logger logger = LoggerFactory.getLogger(UpdateDomesticTest.class);

  private static final FieldDescriptor REGISTRATION_DESCR =
      fieldWithPath("registration").type(String.class).description("registration of citizen.");

  private static final FieldDescriptor DOMESTIC_ID_DESCR =
      fieldWithPath("domesticId").type(String.class).description("registration of citizen.");

  private static final FieldDescriptor CITIZEN_ID_DESCR =
      fieldWithPath("citizenId").type(String.class).description("registration of citizen.");

  private static final FieldDescriptor NAME_DESCR =
      fieldWithPath("name").type(String.class).description("name of citizen.");

  private static final FieldDescriptor SURNAME_DESCR =
      fieldWithPath("surname").type(String.class).description("surname.");

  private static final FieldDescriptor DATE_OF_BIRTH_DESCR =
      fieldWithPath("dateOfBirth").type(String.class).description("date of birth of the citizen.");

  private static final FieldDescriptor ACTIVE_DESCR =
      fieldWithPath("isActive")
          .type(String.class)
          .description("boolean value that indicates whether the passport tax has been paid.");

  private static final FieldDescriptor SERIES_DESCR =
      fieldWithPath("series").type(Long.class).description("Series of the passport.");

  private static final FieldDescriptor NUMBER_DESCR =
      fieldWithPath("number").type(Long.class).description("Number of the passport.");

  private static final FieldDescriptor[] PASSPORT_DESCR =
      new FieldDescriptor[] {
        UpdateDomesticTest.DOMESTIC_ID_DESCR,
        UpdateDomesticTest.CITIZEN_ID_DESCR,
        UpdateDomesticTest.ACTIVE_DESCR,
        UpdateDomesticTest.NAME_DESCR,
        UpdateDomesticTest.SURNAME_DESCR,
        UpdateDomesticTest.DATE_OF_BIRTH_DESCR,
        UpdateDomesticTest.REGISTRATION_DESCR,
        UpdateDomesticTest.SERIES_DESCR,
        UpdateDomesticTest.NUMBER_DESCR
      };

  private static final ResponseFieldsSnippet PASSPORT_RESP =
      responseFields(UpdateDomesticTest.PASSPORT_DESCR);

  @Autowired private MockMvc mockMvc;

  @MockBean private PassportTable passportTable;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void updateDomesticPassportTest() throws Exception {
    Citizen citizen = new Citizen();
    citizen.setCitizenId(111L);
    citizen.setSurname("Pupkin");
    citizen.setName("Vasya");
    citizen.setRegistration("Samara");
    citizen.setDateOfBirth(new Date());

    DomesticDto domesticDTO =
        new DomesticDto(
            1L, "Samara", "Pupkin", "Vasya", citizen.getDateOfBirth(), false, 2222, 333333, 111L);

    final Domestic domestic = new Domestic();

    when(passportTable.updateDomestic(domesticDTO.getDomesticId(), domesticDTO))
        .thenReturn(ResponseEntity.ok(domestic));

    mockMvc
        .perform(
            post(PASSPORT_CONTROLLER_MAPPING
                    + "/passport/updateDomestic/"
                    + domesticDTO.getDomesticId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(domesticDTO)))
        .andDo(document("updateDomesticTest", PASSPORT_RESP))
        .andExpect(status().isOk());
  }
}
