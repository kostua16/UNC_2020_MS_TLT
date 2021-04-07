package nc.unc.cs.services.passport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.passport.controller.dto.DomesticDto;
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

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {PassportController.class})
//@Import(ObjectMapper.class)
//@WithMockUser("customUsername") // аннотация для создания авторизованного пользователя
class UpdateDomesticTest {
  private static final String PASSPORT_CONTROLLER_MAPPING = "http://localhost:8095";
  private static final Logger logger = LoggerFactory.getLogger(UpdateDomesticTest.class);

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PassportTable passportTable;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void updateDomesticPassportTest() throws Exception {
    Citizen citizen = new Citizen();
    citizen.setCitizenId(111L);
    citizen.setSurname("Pupkin");
    citizen.setName("Vasya");
    citizen.setRegistration("Samara");
    citizen.setDateOfBirth(new Date());

    DomesticDto domesticDTO = new DomesticDto(
        1L,
        "Samara",
        "Pupkin",
        "Vasya",
        citizen.getDateOfBirth(),
        false,
        2222,
        333333,
        111L
    );

    final Domestic domestic = new Domestic();

    when(passportTable.updateDomestic(domesticDTO.getDomesticId(), domesticDTO))
        .thenReturn(ResponseEntity.ok(domestic));


    mockMvc.perform(post(PASSPORT_CONTROLLER_MAPPING + "/passport/updateDomestic/" + domesticDTO.getDomesticId())
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(domesticDTO)))
        .andExpect(status().isOk());
  }
}
