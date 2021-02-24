package nc.unc.cs.services.communal.services.integrations;

import java.util.Date;
import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTax;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
public class PropertyTaxServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(PropertyTaxServiceTest.class);

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private PropertyTaxRepository propertyTaxRepository;
    @Mock
    private PropertyTaxValueRepository propertyTaxValueRepository;
    @Mock
    private BankService bankService;

    @InjectMocks
    private PropertyTaxService propertyTaxService;

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

    @Test
    public void calculatePropertyTaxTest() {
        Property property = this.getProperty();
        PropertyTaxValue propertyTaxValue = getPropertyTaxValue();

        given(this.propertyRepository.findPropertyByPropertyId(1L)).willReturn(property);
        given(this.propertyTaxValueRepository.findPropertyTaxValueByRegion(property.getRegion())).willReturn(propertyTaxValue);

        Date date = new Date(5555);

        PropertyTax propertyTax = new PropertyTax();
        propertyTax.setPropertyId(property.getPropertyId());
        propertyTax.setIsPaid(false);
        propertyTax.setDate(date);
        propertyTax.setCitizenId(111L);
        propertyTax.setTaxAmount(
            this.propertyTaxService.calculatePropertyTaxAmount(
                Double.valueOf(property.getApartmentSize()),
                Double.valueOf(propertyTaxValue.getPricePerSquareMeter()),
                Double.valueOf(propertyTaxValue.getCadastralValue())));


        logger.warn("Test Date: {} ", propertyTax.getDate().getTime());

        System.out.println(propertyTax.toString());

        given(this.bankService.requestPayment(anyObject())) // изменить тело метода, вынести PaymentPayload в переменную
            .willReturn(new ResponseEntity<>(15L, HttpStatus.OK));

        propertyTax.setPaymentRequestId(1L);
        propertyTax.setPropertyTaxId(1L);
        given(this.propertyTaxRepository.save(propertyTax)).willReturn(propertyTax);

        ResponseEntity<PropertyTax> response = this.propertyTaxService.calculatePropertyTax(1L);

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertNotNull(response.getBody().getTaxAmount()),
            () -> Assertions.assertFalse(response.getBody().getIsPaid()),
            () -> Assertions.assertEquals(15L, response.getBody().getPaymentRequestId())
        );
    }


    @Test
    public void addPropertyTaxValueTestNullCadastralValue() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("samara");
        propertyTaxValue.setPricePerSquareMeter(100000);
        ResponseEntity<PropertyTaxValue> response = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addPropertyTaxValueTestZeroCadastralValue() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion(" samara");
        propertyTaxValue.setCadastralValue(0);
        propertyTaxValue.setPricePerSquareMeter(100000);
        ResponseEntity<PropertyTaxValue> response = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addPropertyTaxValueTestNullPrice() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("samara  ");
        propertyTaxValue.setCadastralValue(10);
        ResponseEntity<PropertyTaxValue> response = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addPropertyTaxValueTestZeroPrice() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("samara  ");
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(0);
        ResponseEntity<PropertyTaxValue> response = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addPropertyTaxValueTestNullRegion() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(100000);

        given(this.propertyTaxValueRepository.save(propertyTaxValue)).willReturn(propertyTaxValue);

        ResponseEntity<PropertyTaxValue> response = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
            () -> Assertions.assertNull(response.getBody().getPropertyTaxValueId())
        );
    }

    @Test
    public void addPropertyTaxValueTestSpaceOrEmptyRegion() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("         ");
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(100000);

        given(this.propertyTaxValueRepository.save(propertyTaxValue)).willReturn(propertyTaxValue);

        ResponseEntity<PropertyTaxValue> response = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getTax() {
        Integer res = propertyTaxService.calculatePropertyTaxAmount(100.0, 1000.0, 15.0);
        Assertions.assertEquals(150, res);
    }

    @Test
    public void addPropertyTaxValueTestCreate() {
        final PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("samara");
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(100000);
        propertyTaxValue.setPropertyTaxValueId(1L);

        given(this.propertyTaxValueRepository.findPropertyTaxValueByRegion(propertyTaxValue.getRegion()))
            .willReturn(null);
        given(this.propertyTaxValueRepository.save(propertyTaxValue))
            .willReturn(propertyTaxValue);

        ResponseEntity<PropertyTaxValue> response = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertNotNull(response.getBody().getPropertyTaxValueId())
        );
    }

    @Test
    public void addPropertyTaxValueTestUpdate() {
        final PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("samara");
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(100000);
        propertyTaxValue.setPropertyTaxValueId(1L);

        final PropertyTaxValue newPropertyTaxValue = new PropertyTaxValue();
        newPropertyTaxValue.setRegion("samara");
        newPropertyTaxValue.setCadastralValue(15);
        newPropertyTaxValue.setPricePerSquareMeter(80000);

        given(this.propertyTaxValueRepository.findPropertyTaxValueByRegion(newPropertyTaxValue.getRegion()))
            .willReturn(propertyTaxValue);
        given(this.propertyTaxValueRepository.save(newPropertyTaxValue))
            .willReturn(newPropertyTaxValue);


        ResponseEntity<PropertyTaxValue> response = this.propertyTaxService.addPropertyTaxValue(newPropertyTaxValue);

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertEquals(
                newPropertyTaxValue.getPricePerSquareMeter(),
                response.getBody().getPricePerSquareMeter()
            ),
            () -> Assertions.assertEquals(
                newPropertyTaxValue.getCadastralValue(),
                response.getBody().getCadastralValue()
            ),
            () -> Assertions.assertEquals(
                propertyTaxValue.getPropertyTaxValueId(),
                response.getBody().getPropertyTaxValueId()
            )
        );
    }

    public void changePropertyTaxStatusTest() {
        final PropertyTax propertyTax = new PropertyTax();
        propertyTax.setPropertyTaxId(1L);
        propertyTax.setPropertyId(1L);
        propertyTax.setTaxAmount(10000);
        propertyTax.setDate(new Date());
        propertyTax.setIsPaid(false);
        propertyTax.setCitizenId(111L);
        propertyTax.setPaymentRequestId(15L);

        System.out.println(propertyTax.getIsPaid());
        given(this.bankService.checkPaymentStatus(propertyTax.getPaymentRequestId())).willReturn(true);
        given(this.propertyTaxRepository.save(propertyTax)).willReturn(propertyTax);

        ResponseEntity<PropertyTax> response = this.propertyTaxService.changePropertyTaxStatus(1L);
        System.out.println(propertyTax.getIsPaid());

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertTrue(response.getBody().getIsPaid())
        );
    }
}