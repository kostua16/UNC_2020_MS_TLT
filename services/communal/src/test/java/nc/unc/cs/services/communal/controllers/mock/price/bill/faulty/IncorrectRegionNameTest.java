package nc.unc.cs.services.communal.controllers.mock.price.bill.faulty;

import nc.unc.cs.services.communal.controllers.mock.price.bill.UtilityBillPriceListParent;
import nc.unc.cs.services.communal.controllers.payloads.CreationUtilitiesPriceList;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IncorrectRegionNameTest extends UtilityBillPriceListParent {

  @ParameterizedTest
  @ValueSource(strings = {"   ", " 1 ", "sssssssssssssssssssssssssssssssssssssssss"})
  void checkRegionNameTest(final String word) throws Exception {
    final CreationUtilitiesPriceList newPriceList = this.createCreationUtilitiesPriceList();
    newPriceList.setRegion(word);

    this.mockMvc
        .perform(
            post(UTILITY_BILL_PRICE_LIST_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newPriceList)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
