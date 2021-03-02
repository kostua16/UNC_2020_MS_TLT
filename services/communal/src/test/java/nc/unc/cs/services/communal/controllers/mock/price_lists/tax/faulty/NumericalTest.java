package nc.unc.cs.services.communal.controllers.mock.price_lists.tax.faulty;

import nc.unc.cs.services.communal.controllers.mock.price_lists.tax.PropertyTaxValueParentTest;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NumericalTest extends PropertyTaxValueParentTest {

    @Test
    public void smallerPricePerSquareMeter() throws Exception {
        final PropertyTaxValue propertyTaxValue = this.createPropertyTaxValue();
        propertyTaxValue.setPricePerSquareMeter(-1);

        this.mockMvc.perform(post(PROPERTY_TAX_VALUE_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(propertyTaxValue)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void smallerCadastralValue() throws Exception {
        final PropertyTaxValue propertyTaxValue = this.createPropertyTaxValue();
        propertyTaxValue.setCadastralValue(-1);

        this.mockMvc.perform(post(PROPERTY_TAX_VALUE_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(propertyTaxValue)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void overSizeCadastralValue() throws Exception {
        final PropertyTaxValue propertyTaxValue = this.createPropertyTaxValue();
        propertyTaxValue.setCadastralValue(101);

        this.mockMvc.perform(post(PROPERTY_TAX_VALUE_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(propertyTaxValue)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
