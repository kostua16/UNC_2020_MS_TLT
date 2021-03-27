package nc.unc.cs.services.communal.controllers.mock.price.tax.correct;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.price.tax.PropertyTaxValueParent;
import nc.unc.cs.services.communal.controllers.payloads.CreationPropertyTaxValue;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class AddPropertyTaxValueTest extends PropertyTaxValueParent {

  @Test
  void addPropertyTaxValue() throws Exception {
    final CreationPropertyTaxValue creationPropertyTaxValue = this.createCreationPropertyTaxValue();
    final PropertyTaxValue propertyTaxValue = this.createPropertyTaxValue();

    System.out.println(creationPropertyTaxValue);

    when(propertyTaxService.addPropertyTaxValue(creationPropertyTaxValue))
        .thenReturn(ResponseEntity.ok(propertyTaxValue));

    this.mockMvc
        .perform(
            post(PROPERTY_TAX_VALUE_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creationPropertyTaxValue)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string(containsString(this.objectMapper.writeValueAsString(propertyTaxValue))));
  }
}
