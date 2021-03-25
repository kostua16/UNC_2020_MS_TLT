package nc.unc.cs.services.communal.controllers.mock.price_lists.bill.correct;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.price_lists.bill.UtilityBillPriceListParent;
import nc.unc.cs.services.communal.controllers.payloads.CreationUtilitiesPriceList;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class AddUtilityBillPriceListTest extends UtilityBillPriceListParent {
    @Test
    void addPropertyTaxValue() throws Exception {
        final CreationUtilitiesPriceList newPriceList = this.createCreationUtilitiesPriceList();
        final UtilitiesPriceList priceList = this.createUtilitiesPriceList();

        when(this.communalService.addUtilitiesPriceList(newPriceList))
                .thenReturn(ResponseEntity.ok(priceList));

        this.mockMvc
                .perform(
                        post(UTILITY_BILL_PRICE_LIST_MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(newPriceList)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                this.objectMapper.writeValueAsString(priceList))));
    }
}
