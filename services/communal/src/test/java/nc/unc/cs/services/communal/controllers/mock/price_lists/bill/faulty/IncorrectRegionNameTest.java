package nc.unc.cs.services.communal.controllers.mock.price_lists.bill.faulty;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.price_lists.bill.UtilityBillPriceListParent;
import nc.unc.cs.services.communal.controllers.payloads.CreationUtilitiesPriceList;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class IncorrectRegionNameTest extends UtilityBillPriceListParent {

  @ParameterizedTest
  @ValueSource(strings = {"   ", " 1 ",
                          "sssssssssssssssssssssssssssssssssssssssss"})
  void
  checkRegionNameTest(final String word) throws Exception {
    final CreationUtilitiesPriceList newPriceList =
        this.createCreationUtilitiesPriceList();
    newPriceList.setRegion(word);

    this.mockMvc
        .perform(post(UTILITY_BILL_PRICE_LIST_MAPPING)
                     .contentType("application/json")
                     .content(objectMapper.writeValueAsString(newPriceList)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
