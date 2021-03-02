package nc.unc.cs.services.communal.controllers.mock.price_lists.bill.faulty;

import nc.unc.cs.services.communal.controllers.mock.price_lists.bill.UtilityBillPriceListParentTest;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NumericalTest extends UtilityBillPriceListParentTest {

    @Test
    public void smallerColdWaterPrice() throws Exception {
        final UtilitiesPriceList utilitiesPriceList = this.createUtilitiesPriceList();
        utilitiesPriceList.setColdWaterPrice(-1);

        this.mockMvc.perform(post(UTILITY_BILL_PRICE_LIST_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(utilitiesPriceList)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void smallerHotWaterPrice() throws Exception {
        final UtilitiesPriceList utilitiesPriceList = this.createUtilitiesPriceList();
        utilitiesPriceList.setHotWaterPrice(-1);

        this.mockMvc.perform(post(UTILITY_BILL_PRICE_LIST_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(utilitiesPriceList)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void smallerElectricityPrice() throws Exception {
        final UtilitiesPriceList utilitiesPriceList = this.createUtilitiesPriceList();
        utilitiesPriceList.setElectricityPrice(-1);

        this.mockMvc.perform(post(UTILITY_BILL_PRICE_LIST_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(utilitiesPriceList)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
