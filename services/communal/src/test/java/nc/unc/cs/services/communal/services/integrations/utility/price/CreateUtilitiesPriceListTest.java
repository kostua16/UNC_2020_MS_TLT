package nc.unc.cs.services.communal.services.integrations.utility.price;

import nc.unc.cs.services.communal.controllers.payloads.CreationUtilitiesPriceList;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import nc.unc.cs.services.communal.repositories.UtilitiesPriceListRepository;
import nc.unc.cs.services.communal.services.CommunalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class CreateUtilitiesPriceListTest {

    @Mock
    private UtilitiesPriceListRepository utilitiesPriceListRepository;
    @InjectMocks
    private CommunalService communalService;

    private CreationUtilitiesPriceList createCreationUtilitiesPriceList() {
        return CreationUtilitiesPriceList
            .builder()
            .region("samara")
            .coldWaterPrice(5)
            .hotWaterPrice(20)
            .electricityPrice(5)
            .build();
    }

    private UtilitiesPriceList createUtilitiesPriceList() {
        final CreationUtilitiesPriceList newPriceList =
            this.createCreationUtilitiesPriceList();
        return UtilitiesPriceList
            .builder()
            .region(newPriceList.getRegion())
            .coldWaterPrice(newPriceList.getColdWaterPrice())
            .hotWaterPrice(newPriceList.getHotWaterPrice())
            .electricityPrice(newPriceList.getElectricityPrice())
            .build();
    }

    @Test
    void addUtilitiesPriceListTest() {
        final CreationUtilitiesPriceList newPriceList = this.createCreationUtilitiesPriceList();
        final UtilitiesPriceList utilitiesPriceList = this.createUtilitiesPriceList();

        given(this.utilitiesPriceListRepository.findUtilitiesPriceListByRegion(newPriceList.getRegion()))
            .willReturn(null);
        given(this.utilitiesPriceListRepository.save(utilitiesPriceList))
            .willReturn(utilitiesPriceList);

        ResponseEntity<UtilitiesPriceList> response =
            this.communalService.addUtilitiesPriceList(newPriceList);

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertEquals(utilitiesPriceList.getRegion(), response.getBody().getRegion()),
            () -> Assertions.assertEquals(
                utilitiesPriceList.getElectricityPrice(),
                response.getBody().getElectricityPrice()
            )
        );
    }

    @Test
    void updateUtilitiesPriceListTest() {
        final CreationUtilitiesPriceList newPriceList = this.createCreationUtilitiesPriceList();
        final UtilitiesPriceList utilitiesPriceList = this.createUtilitiesPriceList();
        final UtilitiesPriceList lastPriceList = this.createUtilitiesPriceList();
        lastPriceList.setColdWaterPrice(6);
        lastPriceList.setHotWaterPrice(21);
        lastPriceList.setElectricityPrice(6);
        final Integer electricityPrice = lastPriceList.getElectricityPrice();

        given(this.utilitiesPriceListRepository.findUtilitiesPriceListByRegion(newPriceList.getRegion()))
            .willReturn(lastPriceList);
        given(this.utilitiesPriceListRepository.save(utilitiesPriceList))
            .willReturn(utilitiesPriceList);

        ResponseEntity<UtilitiesPriceList> response =
            this.communalService.addUtilitiesPriceList(newPriceList);

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertEquals(utilitiesPriceList.getRegion(), response.getBody().getRegion()),
            () -> Assertions.assertNotEquals(
                electricityPrice,
                response.getBody().getElectricityPrice()
            )
        );
    }

}
