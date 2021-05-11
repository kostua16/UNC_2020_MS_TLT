package nc.unc.cs.services.communal.controllers.mock.price.bill.correct;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nc.unc.cs.services.communal.controllers.mock.price.bill.UtilityBillPriceListParent;
import nc.unc.cs.services.communal.controllers.payloads.CreationUtilitiesPriceList;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

@AutoConfigureRestDocs
class AddUtilityBillPriceListTest extends UtilityBillPriceListParent {

  private static final FieldDescriptor REGION_DESCR =
      fieldWithPath("region").type(String.class).description("Address region.");

  private static final FieldDescriptor UTILITIES_PRICE_LIST_ID_DESCR =
      fieldWithPath("utilitiesPriceListId").type(String.class).description("Utility bills.");

  private static final FieldDescriptor COLD_WATER_DESCR =
      fieldWithPath("coldWaterPrice").type(String.class).description("Apartment cold water price.");

  private static final FieldDescriptor HOT_WATER_DESCR =
      fieldWithPath("hotWaterPrice").type(String.class).description("Apartment hot water price.");

  private static final FieldDescriptor ELECTRICITY_DESCR =
      fieldWithPath("electricityPrice")
          .type(String.class)
          .description("Apartment electricity price.");

  private static final FieldDescriptor[] BILL_PRICE_DESCR =
      new FieldDescriptor[] {
        AddUtilityBillPriceListTest.UTILITIES_PRICE_LIST_ID_DESCR,
        AddUtilityBillPriceListTest.REGION_DESCR,
        AddUtilityBillPriceListTest.COLD_WATER_DESCR,
        AddUtilityBillPriceListTest.HOT_WATER_DESCR,
        AddUtilityBillPriceListTest.ELECTRICITY_DESCR
      };

  private static final FieldDescriptor[] BILL_PRICE_REQ_DESCR =
      new FieldDescriptor[] {
        AddUtilityBillPriceListTest.REGION_DESCR,
        AddUtilityBillPriceListTest.COLD_WATER_DESCR,
        AddUtilityBillPriceListTest.HOT_WATER_DESCR,
        AddUtilityBillPriceListTest.ELECTRICITY_DESCR
      };

  private static final RequestFieldsSnippet BILL_PRICE_REQ =
      requestFields(AddUtilityBillPriceListTest.BILL_PRICE_REQ_DESCR);
  private static final ResponseFieldsSnippet BILL_PRICE_RESP =
      responseFields(AddUtilityBillPriceListTest.BILL_PRICE_DESCR);

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
        .andDo(document("addRegistrationTest", BILL_PRICE_REQ, BILL_PRICE_RESP))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            content().string(containsString(this.objectMapper.writeValueAsString(priceList))));
  }
}
