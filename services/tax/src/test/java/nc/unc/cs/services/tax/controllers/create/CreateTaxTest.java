package nc.unc.cs.services.tax.controllers.create;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.common.clients.tax.CreationTax;
import org.junit.jupiter.api.Test;

class CreateTaxTest extends TaxWeb {

  private CreationTax getCreationTax() {
    return CreationTax.builder()
        .serviceId(20L)
        .citizenId(111L)
        .taxAmount(1000)
        .build();
  }

  @Test
  void addCitizensPropertyTest() throws Exception {
    CreationTax creationTax = this.getCreationTax();

    when(this.taxService.createTax(creationTax.getServiceId(),
                                   creationTax.getCitizenId(),
                                   creationTax.getTaxAmount()))
        .thenReturn(1L);

    this.mockMvc
        .perform(post(TAX_CONTROLLER_MAPPING + "/create")
                     .contentType("application/json")
                     .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(
            containsString(this.objectMapper.writeValueAsString(1L))));
  }

  @Test
  void addCitizensPropertyTestServiceIdSmallerSize() throws Exception {
    CreationTax creationTax = this.getCreationTax();
    creationTax.setServiceId(null);

    this.mockMvc
        .perform(post(TAX_CONTROLLER_MAPPING + "/create")
                     .contentType("application/json")
                     .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void addCitizensPropertyTestServiceIdNull() throws Exception {
    CreationTax creationTax = this.getCreationTax();
    creationTax.setServiceId(0L);

    this.mockMvc
        .perform(post(TAX_CONTROLLER_MAPPING + "/create")
                     .contentType("application/json")
                     .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void addCitizensPropertyTestCitizenIdNull() throws Exception {
    CreationTax creationTax = this.getCreationTax();
    creationTax.setCitizenId(null);

    this.mockMvc
        .perform(post(TAX_CONTROLLER_MAPPING + "/create")
                     .contentType("application/json")
                     .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
  @Test
  void addCitizensPropertyTestCitizenIdSmallerSize() throws Exception {
    CreationTax creationTax = this.getCreationTax();
    creationTax.setCitizenId(0L);

    this.mockMvc
        .perform(post(TAX_CONTROLLER_MAPPING + "/create")
                     .contentType("application/json")
                     .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void addCitizensPropertyTestTaxAmountNull() throws Exception {
    CreationTax creationTax = this.getCreationTax();
    creationTax.setTaxAmount(null);

    this.mockMvc
        .perform(post(TAX_CONTROLLER_MAPPING + "/create")
                     .contentType("application/json")
                     .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void addCitizensPropertyTestTaxAmountSmallerSize() throws Exception {
    CreationTax creationTax = this.getCreationTax();
    creationTax.setTaxAmount(0);

    this.mockMvc
        .perform(post(TAX_CONTROLLER_MAPPING + "/create")
                     .contentType("application/json")
                     .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
