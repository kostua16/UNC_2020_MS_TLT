package nc.unc.cs.services.communal.controllers.mock.price_lists.tax.faulty;

import nc.unc.cs.services.communal.controllers.mock.price_lists.tax.PropertyTaxValueParent;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IncorrectRegionNameTest extends PropertyTaxValueParent {
    @ParameterizedTest
    @ValueSource(
        strings = {
            "   ",
            " 1 ",
            "sssssssssssssssssssssssssssssssssssssssss"
        }
    )
    void checkRegionNameTest(final String word) throws Exception {
        final PropertyTaxValue propertyTaxValue = this.createPropertyTaxValue();
        propertyTaxValue.setRegion(word);

        this.mockMvc.perform(post(PROPERTY_TAX_VALUE_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(propertyTaxValue)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
