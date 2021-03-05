package nc.unc.cs.services.communal.controllers.mock.price_lists.bill;

import nc.unc.cs.services.communal.controllers.UtilitiesPriceListController;
import nc.unc.cs.services.communal.controllers.mock.ParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationUtilitiesPriceList;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import nc.unc.cs.services.communal.services.CommunalService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {UtilitiesPriceListController.class})
public class UtilityBillPriceListParent extends ParentWeb {
    protected static final String UTILITY_BILL_PRICE_LIST_MAPPING = "http://localhost:8083/communal/utilities/price-list";
    @MockBean
    protected CommunalService communalService;

    protected final UtilitiesPriceList createUtilitiesPriceList() {
        CreationUtilitiesPriceList creationUtilitiesPriceList = this.createCreationUtilitiesPriceList();
        return UtilitiesPriceList
            .builder()
            .utilitiesPriceListId(1L)
            .region(creationUtilitiesPriceList.getRegion())
            .coldWaterPrice(creationUtilitiesPriceList.getColdWaterPrice())
            .hotWaterPrice(creationUtilitiesPriceList.getHotWaterPrice())
            .electricityPrice(creationUtilitiesPriceList.getElectricityPrice())
            .build();
    }

    protected final CreationUtilitiesPriceList createCreationUtilitiesPriceList() {
        return CreationUtilitiesPriceList
            .builder()
            .region(" samara ")
            .coldWaterPrice(3)
            .hotWaterPrice(20)
            .electricityPrice(5)
            .build();
    }
}
