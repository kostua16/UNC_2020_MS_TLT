package nc.unc.cs.services.communal.controllers.mock.price_lists.tax.correct;

import nc.unc.cs.services.communal.controllers.mock.price_lists.tax.PropertyTaxValueParent;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AddPropertyTaxValueTest extends PropertyTaxValueParent {

    @Test
    void addPropertyTaxValue() throws Exception{
        final PropertyTaxValue propertyTaxValue = this.createPropertyTaxValue();

        when(propertyTaxService.addPropertyTaxValue(propertyTaxValue))
            .thenReturn(ResponseEntity.ok(propertyTaxValue));

        this.mockMvc.perform(post(PROPERTY_TAX_VALUE_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(propertyTaxValue)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(this.objectMapper.writeValueAsString(propertyTaxValue))));
    }
}
