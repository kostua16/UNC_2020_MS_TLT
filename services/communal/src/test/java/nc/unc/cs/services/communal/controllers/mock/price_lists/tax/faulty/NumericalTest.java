package nc.unc.cs.services.communal.controllers.mock.price_lists.tax.faulty;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.price_lists.tax.PropertyTaxValueParent;
import nc.unc.cs.services.communal.controllers.payloads.CreationPropertyTaxValue;
import org.junit.jupiter.api.Test;

class NumericalTest extends PropertyTaxValueParent {

    @Test
    void smallerPricePerSquareMeter() throws Exception {
        final CreationPropertyTaxValue newPropertyTaxValue = this.createCreationPropertyTaxValue();
        newPropertyTaxValue.setPricePerSquareMeter(-1);

        this.mockMvc
                .perform(
                        post(PROPERTY_TAX_VALUE_CONTROLLER_MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(newPropertyTaxValue)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void smallerCadastralValue() throws Exception {
        final CreationPropertyTaxValue newPropertyTaxValue = this.createCreationPropertyTaxValue();
        newPropertyTaxValue.setCadastralValue(-1);

        this.mockMvc
                .perform(
                        post(PROPERTY_TAX_VALUE_CONTROLLER_MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(newPropertyTaxValue)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void overSizeCadastralValue() throws Exception {
        final CreationPropertyTaxValue newPropertyTaxValue = this.createCreationPropertyTaxValue();
        newPropertyTaxValue.setCadastralValue(101);

        this.mockMvc
                .perform(
                        post(PROPERTY_TAX_VALUE_CONTROLLER_MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(newPropertyTaxValue)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
