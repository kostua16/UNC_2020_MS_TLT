package nc.unc.cs.services.communal.controllers.mock.price.tax.faulty;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.price.tax.PropertyTaxValueParent;
import nc.unc.cs.services.communal.controllers.payloads.CreationPropertyTaxValue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class IncorrectRegionNameTest extends PropertyTaxValueParent {
    @ParameterizedTest
    @ValueSource(strings = {"   ", " 1 ", "sssssssssssssssssssssssssssssssssssssssss"})
    void checkRegionNameTest(final String word) throws Exception {
        final CreationPropertyTaxValue newPropertyTaxValue = this.createCreationPropertyTaxValue();
        newPropertyTaxValue.setRegion(word);

        this.mockMvc
                .perform(
                        post(PROPERTY_TAX_VALUE_CONTROLLER_MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(newPropertyTaxValue)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
