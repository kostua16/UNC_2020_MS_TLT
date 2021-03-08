package nc.unc.cs.services.communal.services.integrations.utility.bill;

import nc.unc.cs.services.communal.controllers.payloads.UtilitiesPayload;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import nc.unc.cs.services.communal.entities.UtilityBill;
import nc.unc.cs.services.communal.exceptions.PropertyNotFoundException;
import nc.unc.cs.services.communal.exceptions.UtilitiesPriceListNotFoundException;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.UtilitiesPriceListRepository;
import nc.unc.cs.services.communal.repositories.UtilityBillRepository;
import nc.unc.cs.services.communal.services.BankIntegrationService;
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
class CreateUtilityBillTest {

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private UtilityBillRepository utilityBillRepository;
    @Mock
    private UtilitiesPriceListRepository utilitiesPriceListRepository;
    @Mock
    private BankIntegrationService bankIntegrationService;
    @InjectMocks
    private CommunalService communalService;

    /** Идентификатор сервиса, поставляеющего услугу. */
    public static final Long SERVICE_ID = CommunalService.SERVICE_ID;
    /** Налоговый процент. */
    public static final Integer TAX_PERCENT = CommunalService.TAX_PERCENT;

    private UtilitiesPayload createUtilitiesPayload() {
        return UtilitiesPayload
            .builder()
            .propertyId(1L)
            .coldWater(200)
            .hotWater(200)
            .electricity(200)
            .build();
    }

    private UtilitiesPriceList createUtilitiesPriceList() {
        return UtilitiesPriceList
            .builder()
            .utilitiesPriceListId(1L)
            .region("samara")
            .coldWaterPrice(5)
            .hotWaterPrice(20)
            .electricityPrice(5)
            .build();
    }

    private UtilityBill createUtilityBill() {
        final UtilitiesPayload utilitiesPayload = this.createUtilitiesPayload();
        return UtilityBill
            .builder()
            .coldWater(utilitiesPayload.getColdWater())
            .hotWater(utilitiesPayload.getHotWater())
            .electricity(utilitiesPayload.getElectricity())
            .build();
    }

    private Property createProperty() {
        return Property.builder()
            .propertyId(1L)
            .region("Samara")
            .city("Samara")
            .street("main")
            .house("111")
            .apartment("1b")
            .apartmentSize(1000)
            .citizenId(111L)
            .build();
    }

    @Test
    void calculateUtilityBill() {
        final UtilitiesPayload utilitiesPayload = this.createUtilitiesPayload();
        final Property property = this.createProperty();
        final UtilitiesPriceList priceList = this.createUtilitiesPriceList();
        final UtilityBill utilityBill = this.createUtilityBill();
        utilityBill.setCitizenId(property.getCitizenId());
        utilityBill.setPaymentRequestId(15L);
        utilityBill.setColdWaterAmount(utilitiesPayload.getColdWater() * priceList.getColdWaterPrice());
        utilityBill.setHotWaterAmount(utilitiesPayload.getHotWater() * priceList.getHotWaterPrice());
        utilityBill.setElectricityAmount(utilitiesPayload.getElectricity() * priceList.getElectricityPrice());
        utilityBill.setUtilityAmount(
            utilityBill.getColdWaterAmount()
                + utilityBill.getHotWaterAmount()
                + utilityBill.getElectricityAmount()
        );
        given(this.propertyRepository.findPropertyByPropertyId(utilitiesPayload.getPropertyId()))
            .willReturn(property);
        given(this.utilitiesPriceListRepository.findUtilitiesPriceListByRegion(property.getRegion()))
            .willReturn(priceList);
        given(this.bankIntegrationService.bankRequest(
            SERVICE_ID,
            utilityBill.getCitizenId(),
            utilityBill.getUtilityAmount(),
            utilityBill.getUtilityAmount() / TAX_PERCENT
        ))
            .willReturn(15L);
        given(this.utilityBillRepository.save(utilityBill)).willReturn(utilityBill);

        System.out.println(utilityBill);

        final ResponseEntity<UtilityBill> response = this.communalService.calculateUtilityBill(utilitiesPayload);

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertEquals(utilityBill.getUtilityAmount(), response.getBody().getUtilityAmount()),
            () -> Assertions.assertEquals(utilitiesPayload.getHotWater(), response.getBody().getHotWater()),
            () -> Assertions.assertEquals(utilitiesPayload.getPropertyId(), response.getBody().getPropertyId()),
            () -> Assertions.assertEquals(property.getCitizenId(), response.getBody().getCitizenId())
        );
    }

    @Test
    void calculateUtilityCostsPropertyNotFoundTest() {
        final UtilitiesPayload utilitiesPayload = this.createUtilitiesPayload();
        final Property property = this.createProperty();

        given(this.propertyRepository.findPropertyByPropertyId(utilitiesPayload.getPropertyId()))
            .willReturn(null);
        Assertions.assertThrows(
            PropertyNotFoundException.class,
            () -> this.communalService.calculateUtilityBill(utilitiesPayload)
        );
    }

    @Test
    void calculateUtilityCostsUtilitiesPriceListNotFoundTest() {
        final UtilitiesPayload utilitiesPayload = this.createUtilitiesPayload();
        final Property property = this.createProperty();

        given(this.utilitiesPriceListRepository.findUtilitiesPriceListByRegion(property.getRegion()))
            .willReturn(null);
        Assertions.assertThrows(
            UtilitiesPriceListNotFoundException.class,
            () -> this.communalService.calculateUtilityCosts(property.getRegion(), utilitiesPayload)
        );
    }
}
