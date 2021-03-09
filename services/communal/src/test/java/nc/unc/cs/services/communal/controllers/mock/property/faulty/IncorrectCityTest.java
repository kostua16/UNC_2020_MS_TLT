package nc.unc.cs.services.communal.controllers.mock.property.faulty;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.property.PropertyParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class IncorrectCityTest extends PropertyParentWeb {

  @ParameterizedTest
  @ValueSource(strings = {"   ", " 1 ",
                          "sssssssssssssssssssssssssssssssssssssssss"})
  void
  checkCityNameTest(final String word) throws Exception {
    final CreationProperty property = this.createCreationProperty();
    property.setCity(word);

    this.mockMvc
        .perform(post(PROPERTY_CONTROLLER_MAPPING)
                     .contentType("application/json")
                     .content(objectMapper.writeValueAsString(property)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
