package nc.unc.cs.services.communal.services.integrations.property_tax;

import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.repositories.PropertyTaxRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxValueRepository;
import nc.unc.cs.services.communal.services.BankIntegrationService;
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
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
public class PropertyTaxValueTest {

    private static final Logger logger = LoggerFactory.getLogger(PropertyTaxValueTest.class);

    @Mock
    private PropertyTaxRepository propertyTaxRepository;
    @Mock
    private PropertyTaxValueRepository propertyTaxValueRepository;
    @Mock
    private BankIntegrationService bankIntegrationService;

    @InjectMocks
    private PropertyTaxService propertyTaxService;

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


}