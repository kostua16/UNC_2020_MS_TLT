package nc.unc.cs.services.communal.services.integrations;

import nc.unc.cs.services.communal.SpringCommunalTest;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class PropertyTaxServiceTest extends SpringCommunalTest {

    @Autowired
    private PropertyTaxService propertyTaxService;

    @Test
    public void getTax() {
        Integer res = propertyTaxService.calculatePropertyTaxAmount(100.0, 1000.0, 15.0);
        Assertions.assertEquals(150, res);
    }

    @Test
    public void addPropertyTaxValueTestOk() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("samara");
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(100000);
        ResponseEntity<PropertyTaxValue> resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertAll(
            () -> Assertions.assertEquals(200, resp.getStatusCodeValue()),
            () -> Assertions.assertNotNull(resp.getBody().getPropertyTaxValueId())
        );
    }

    @Test // assertAll
    public void addPropertyTaxValueTestNullRegion() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(100000);
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
        ResponseEntity resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(400, resp.getStatusCodeValue());
    }

    @Test
    public void addPropertyTaxValueTestNullCadastralValue() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("samara");
        propertyTaxValue.setPricePerSquareMeter(100000);
        ResponseEntity resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(400, resp.getStatusCodeValue());
    }

    @Test
    public void addPropertyTaxValueTestZeroCadastralValue() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion(" samara");
        propertyTaxValue.setCadastralValue(0);
        propertyTaxValue.setPricePerSquareMeter(100000);
        ResponseEntity resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(400, resp.getStatusCodeValue());
    }

    @Test
    public void addPropertyTaxValueTestNullPrice() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("samara  ");
        propertyTaxValue.setCadastralValue(10);
        ResponseEntity resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(400, resp.getStatusCodeValue());
    }

    @Test
    public void addPropertyTaxValueTestZeroPrice() {
        PropertyTaxValue propertyTaxValue = new PropertyTaxValue();
        propertyTaxValue.setRegion("samara  ");
        propertyTaxValue.setCadastralValue(10);
        propertyTaxValue.setPricePerSquareMeter(0);
        ResponseEntity resp = this.propertyTaxService.addPropertyTaxValue(propertyTaxValue);
        Assertions.assertEquals(400, resp.getStatusCodeValue());
    }
}