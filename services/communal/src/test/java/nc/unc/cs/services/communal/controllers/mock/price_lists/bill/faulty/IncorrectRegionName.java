package nc.unc.cs.services.communal.controllers.mock.price_lists.bill.faulty;

import nc.unc.cs.services.communal.controllers.mock.IncorrectDataTests;
import nc.unc.cs.services.communal.controllers.mock.price_lists.bill.UtilityBillPriceListParentTest;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IncorrectRegionName extends UtilityBillPriceListParentTest implements IncorrectDataTests {

    @Test
    @Override
    public void blankData() throws Exception {
        final UtilitiesPriceList utilitiesPriceList = this.createUtilitiesPriceList();
        utilitiesPriceList.setRegion("  ");

        this.mockMvc.perform(post(UTILITY_BILL_PRICE_LIST_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(utilitiesPriceList)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @Override
    public void smallestSizeData() throws Exception {
        final UtilitiesPriceList utilitiesPriceList = this.createUtilitiesPriceList();
        utilitiesPriceList.setRegion(" 1 ");

        this.mockMvc.perform(post(UTILITY_BILL_PRICE_LIST_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(utilitiesPriceList)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @Override
    public void oversizeData() throws Exception {
        final UtilitiesPriceList utilitiesPriceList = this.createUtilitiesPriceList();
        utilitiesPriceList.setRegion("asdsdsdgqsr1rvxzxgadgasfasfasdasdasdasdasdasdasdasdasdasd");

        this.mockMvc.perform(post(UTILITY_BILL_PRICE_LIST_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(utilitiesPriceList)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
