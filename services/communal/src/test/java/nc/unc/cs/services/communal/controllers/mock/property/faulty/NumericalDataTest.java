package nc.unc.cs.services.communal.controllers.mock.property.faulty;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.property.PropertyParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationProperty;
import org.junit.jupiter.api.Test;

class NumericalDataTest extends PropertyParentWeb {

  @Test
  void smallestApartmentSize() throws Exception {
    final CreationProperty property = this.createCreationProperty();
    property.setApartmentSize(9);

    this.mockMvc
        .perform(
            post(PROPERTY_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(property)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void nullApartmentSize() throws Exception {
    final CreationProperty property = this.createCreationProperty();
    property.setApartmentSize(null);

    this.mockMvc
        .perform(
            post(PROPERTY_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(property)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void smallestCitizenId() throws Exception {
    final CreationProperty property = this.createCreationProperty();
    property.setApartmentSize(9);

    this.mockMvc
        .perform(
            post(PROPERTY_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(property)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void nullCitizenId() throws Exception {
    final CreationProperty property = this.createCreationProperty();
    property.setCitizenId(null);

    this.mockMvc
        .perform(
            post(PROPERTY_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(property)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
