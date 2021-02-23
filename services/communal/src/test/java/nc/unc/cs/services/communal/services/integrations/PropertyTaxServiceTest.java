package nc.unc.cs.services.communal.services.integrations;

import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxValueRepository;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.BDDMockito.given;


//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class PropertyTaxServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(PropertyTaxServiceTest.class);

    @Mock
    private BankService bankService;
    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private PropertyTaxRepository propertyTaxRepository;
    @Mock
    private PropertyTaxValueRepository propertyTaxValueRepository;

    @InjectMocks
    private PropertyTaxService propertyTaxService;


    @Test
    public void getTax() {
        Integer res = propertyTaxService.calculatePropertyTaxAmount(100.0, 1000.0, 15.0);
        Assertions.assertEquals(150, res);
    }

    @Test
    public void addPropertyTaxValueTestOk() {
        final PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("samara");
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(100000);
        propertyTaxValue.setPropertyTaxValueId(1L);

        given(this.propertyTaxValueRepository.save(propertyTaxValue)).willReturn(propertyTaxValue);

        ResponseEntity<PropertyTaxValue> resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);

        Assertions.assertAll(
            () -> Assertions.assertEquals(200, resp.getStatusCodeValue()),
            () -> Assertions.assertNotNull(resp.getBody().getPropertyTaxValueId())
        );
    }

    @Test
    public void addPropertyTaxValueTestNullRegion() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(100000);

        given(this.propertyTaxValueRepository.save(propertyTaxValue)).willReturn(propertyTaxValue);

        ResponseEntity<PropertyTaxValue> resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertAll(
            () -> Assertions.assertEquals(400, resp.getStatusCodeValue()),
            () -> Assertions.assertNull(resp.getBody().getPropertyTaxValueId())
        );
    }

    @Test
    public void addPropertyTaxValueTestSpaceOrEmptyRegion() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("         ");
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(100000);

        given(this.propertyTaxValueRepository.save(propertyTaxValue)).willReturn(propertyTaxValue);

        ResponseEntity<PropertyTaxValue> resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(400, resp.getStatusCodeValue());
    }

    public Property getProperty() {
        Property property = new Property();

        property.setPropertyId(1L);
        property.setRegion(" samara ");
        property.setCity(" tlt");
        property.setStreet("main ");
        property.setHouse("12B");
        property.setApartment("11-d");
        property.setApartmentSize(100);
        property.setCitizenId(111L);

        return property;
    }

    public PropertyTaxValue getPropertyTaxValue() {
        PropertyTaxValue ptv = new PropertyTaxValue();
        ptv.setPropertyTaxValueId(1L);
        ptv.setRegion("samara  ");
        ptv.setPricePerSquareMeter(1000);
        ptv.setCadastralValue(15);

        return ptv;
    }

//    @Test
//    public void calculatePropertyTaxTest() {
//        Property property = this.getProperty();
//        PropertyTaxValue propertyTaxValue = getPropertyTaxValue();
//
//        given(this.propertyRepository.findPropertyByPropertyId(1L)).willReturn(property);
//        given(this.propertyTaxValueRepository.findPropertyTaxValueByRegion(property.getRegion())).willReturn(propertyTaxValue);
//
//        Date date = new Date();
//        date.setTime(555555);
//        PropertyTax propertyTax = new PropertyTax();
//        propertyTax.setPropertyId(property.getPropertyId());
//        propertyTax.setIsPaid(false);
//        propertyTax.setDate(date);
//        propertyTax.setCitizenId(111L);
//        propertyTax.setTaxAmount(
//            propertyTaxService.calculatePropertyTaxAmount(
//                Double.valueOf(property.getApartmentSize()),
//                Double.valueOf(propertyTaxValue.getPricePerSquareMeter()),
//                Double.valueOf(propertyTaxValue.getCadastralValue())));
//
//
//        logger.warn("Test Date: {} ", propertyTax.getDate().getTime());
//
//        given(this.bankService.requestPayment(new PaymentPayload(
//            PropertyTaxService.SERVICE_ID,
//            propertyTax.getCitizenId(),
//            propertyTax.getTaxAmount(),
//            propertyTax.getTaxAmount() / PropertyTaxService.TAX_PERCENT)))
//            .willReturn(ResponseEntity.ok(1L));
//
//        System.out.println(propertyTax.toString());
//        propertyTax.setPaymentRequestId(1L);
//        propertyTax.setPropertyTaxId(1L);
//        given(this.propertyTaxRepository.save(propertyTax)).willReturn(propertyTax);
//
//        ResponseEntity<PropertyTax> resp = this.propertyTaxService.calculatePropertyTax(1L);
//
//        Assertions.assertAll(
//            () -> Assertions.assertEquals(200, resp.getStatusCodeValue()),
//            () -> Assertions.assertNotNull(resp.getBody().getTaxAmount()),
//            () -> Assertions.assertFalse(resp.getBody().getIsPaid()),
//            () -> Assertions.assertEquals(15L, resp.getBody().getPaymentRequestId())
//        );
//    }

//
//    @Test
//    public void addPropertyTaxValueTestNullCadastralValue() {
//        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
//        propertyTaxValue.setRegion("samara");
//        propertyTaxValue.setPricePerSquareMeter(100000);
//        ResponseEntity resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
//        Assertions.assertEquals(400, resp.getStatusCodeValue());
//    }
//
//    @Test
//    public void addPropertyTaxValueTestZeroCadastralValue() {
//        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
//        propertyTaxValue.setRegion(" samara");
//        propertyTaxValue.setCadastralValue(0);
//        propertyTaxValue.setPricePerSquareMeter(100000);
//        ResponseEntity resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
//        Assertions.assertEquals(400, resp.getStatusCodeValue());
//    }
//
//    @Test
//    public void addPropertyTaxValueTestNullPrice() {
//        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
//        propertyTaxValue.setRegion("samara  ");
//        propertyTaxValue.setCadastralValue(10);
//        ResponseEntity resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
//        Assertions.assertEquals(400, resp.getStatusCodeValue());
//    }
//
//    @Test
//    public void addPropertyTaxValueTestZeroPrice() {
//        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
//        propertyTaxValue.setRegion("samara  ");
//        propertyTaxValue.setCadastralValue(10);
//        propertyTaxValue.setPricePerSquareMeter(0);
//        ResponseEntity resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
//        Assertions.assertEquals(400, resp.getStatusCodeValue());
//    }
}