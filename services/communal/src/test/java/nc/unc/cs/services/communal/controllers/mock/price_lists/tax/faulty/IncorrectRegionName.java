package nc.unc.cs.services.communal.controllers.mock.price_lists.tax.faulty;

import nc.unc.cs.services.communal.controllers.mock.IncorrectDataTests;
import nc.unc.cs.services.communal.controllers.mock.price_lists.tax.PropertyTaxValueParentTest;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IncorrectRegionName extends PropertyTaxValueParentTest implements IncorrectDataTests {
    @Test
    @Override
    public void blankData() throws Exception {
        final PropertyTaxValue propertyTaxValue = this.createPropertyTaxValue();
        propertyTaxValue.setRegion("  ");

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(propertyTaxValue)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @Override
    public void smallestSizeData() throws Exception {
        final PropertyTaxValue propertyTaxValue = this.createPropertyTaxValue();
        propertyTaxValue.setRegion(" 1 ");

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(propertyTaxValue)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @Override
    public void oversizeData() throws Exception {
        final PropertyTaxValue propertyTaxValue = this.createPropertyTaxValue();
        propertyTaxValue.setRegion("asdsdsdgqsr1rvxzxgadgasfasfasdasdasdasdasdasdasdasdasdasd");

        this.mockMvc.perform(post(REGISTRATION_CONTROLLER_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(propertyTaxValue)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
