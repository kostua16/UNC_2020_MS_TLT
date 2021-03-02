package nc.unc.cs.services.communal.controllers.mock.price_lists.bill.faulty;

import nc.unc.cs.services.communal.controllers.mock.price_lists.bill.UtilityBillPriceListParent;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IncorrectRegionNameTest extends UtilityBillPriceListParent {

    @ParameterizedTest
    @ValueSource(
        strings = {
            "   ",
            " 1 ",
            "sssssssssssssssssssssssssssssssssssssssss"
        }
    )
    void checkRegionNameTest(final String word) throws Exception {
        final UtilitiesPriceList utilitiesPriceList = this.createUtilitiesPriceList();
        utilitiesPriceList.setRegion(word);

        this.mockMvc.perform(post(UTILITY_BILL_PRICE_LIST_MAPPING)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(utilitiesPriceList)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
