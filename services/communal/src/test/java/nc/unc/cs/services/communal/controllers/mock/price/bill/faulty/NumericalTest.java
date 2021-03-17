package nc.unc.cs.services.communal.controllers.mock.price.bill.faulty;

import nc.unc.cs.services.communal.controllers.mock.price.bill.UtilityBillPriceListParent;
import nc.unc.cs.services.communal.controllers.payloads.CreationUtilitiesPriceList;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NumericalTest extends UtilityBillPriceListParent {

  @Test
  void smallerColdWaterPrice() throws Exception {
    final CreationUtilitiesPriceList newPriceList = this.createCreationUtilitiesPriceList();
    newPriceList.setColdWaterPrice(-1);

    this.mockMvc
        .perform(
            post(UTILITY_BILL_PRICE_LIST_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newPriceList)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void smallerHotWaterPrice() throws Exception {
    final CreationUtilitiesPriceList newPriceList = this.createCreationUtilitiesPriceList();
    newPriceList.setHotWaterPrice(-1);

    this.mockMvc
        .perform(
            post(UTILITY_BILL_PRICE_LIST_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newPriceList)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void smallerElectricityPrice() throws Exception {
    final CreationUtilitiesPriceList newPriceList = this.createCreationUtilitiesPriceList();
    newPriceList.setElectricityPrice(-1);

    this.mockMvc
        .perform(
            post(UTILITY_BILL_PRICE_LIST_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newPriceList)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
