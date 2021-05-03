package nc.unc.cs.services.passport.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import nc.unc.cs.services.passport.model.Citizen;
import nc.unc.cs.services.passport.model.Domestic;
import nc.unc.cs.services.passport.service.PassportTable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PassportController.class)
// @Import(ObjectMapper.class)
// @WithMockUser("customUsername") // аннотация для создания авторизованного пользователя
class RegistrDomesticTest {
  private static final String PASSPORT_CONTROLLER_MAPPING = "http://localhost:8095";
  private static final Logger logger = LoggerFactory.getLogger(RegistrDomesticTest.class);

  @Autowired private MockMvc mockMvc;

  @MockBean private PassportTable passportTable;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void registerDomesticPassportTest() throws Exception {
    Citizen citizen = new Citizen();
    citizen.setCitizenId(1L);
    citizen.setSurname("Pupkin");
    citizen.setName("Vasya");
    citizen.setRegistration("Samara");
    citizen.setDateOfBirth(new Date());

    Domestic domestic =
        new Domestic(
            1L, "Samara", "Pupkin", "Vasya", citizen.getDateOfBirth(), false, 2222, 333333, 111L);

    when(passportTable.registerDomesticPassport(citizen)).thenReturn(ResponseEntity.ok(domestic));

    mockMvc
        .perform(
            post(PASSPORT_CONTROLLER_MAPPING + "/passport/registerDomestic")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(domestic)))
//        .andDo(document("registerDomesticPassportTest"))
        .andExpect(status().isOk());
  }
}
