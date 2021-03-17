package nc.unc.cs.services.communal.controllers.mock.registration.faulty;

import nc.unc.cs.services.communal.controllers.mock.registration.RegistrationParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationRegistration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IncorrectCityTest extends RegistrationParentWeb {

  @ParameterizedTest
  @ValueSource(strings = {"   ", " 1 ", "sssssssssssssssssssssssssssssssssssssssss"})
  void checkCityNameTest(final String word) throws Exception {
    final CreationRegistration registration = this.createCreationRegistration();
    registration.setCity(word);

    this.mockMvc
        .perform(
            post(REGISTRATION_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registration)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
