package nc.unc.cs.services.communal.services.integrations.property_tax;

import static org.mockito.BDDMockito.given;

import nc.unc.cs.services.communal.controllers.payloads.CreationPropertyTaxValue;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
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

@ExtendWith(SpringExtension.class)
class PropertyTaxValueTestTest {

  private static final Logger logger =
      LoggerFactory.getLogger(PropertyTaxValueTestTest.class);

  @Mock private PropertyTaxValueRepository propertyTaxValueRepository;

  @InjectMocks private PropertyTaxService propertyTaxService;

  private CreationPropertyTaxValue createCreationPropertyTaxValue() {
    return CreationPropertyTaxValue.builder()
        .region("samara")
        .pricePerSquareMeter(100000)
        .cadastralValue(10)
        .build();
  }

  @Test
  void getTax() {
    Integer res =
        propertyTaxService.calculatePropertyTaxAmount(100.0, 1000.0, 15.0);
    Assertions.assertEquals(150, res);
  }

  @Test
  void addPropertyTaxValueTestCreate() {
    final CreationPropertyTaxValue newPropertyTaxValue =
        this.createCreationPropertyTaxValue();
    final PropertyTaxValue propertyTaxValue =
        PropertyTaxValue.builder()
            .region(newPropertyTaxValue.getRegion())
            .pricePerSquareMeter(newPropertyTaxValue.getPricePerSquareMeter())
            .cadastralValue(newPropertyTaxValue.getCadastralValue())
            .build();

    given(this.propertyTaxValueRepository.findPropertyTaxValueByRegion(
              propertyTaxValue.getRegion()))
        .willReturn(null);
    given(this.propertyTaxValueRepository.save(propertyTaxValue))
        .willReturn(propertyTaxValue);

    ResponseEntity<PropertyTaxValue> response =
        this.propertyTaxService.addPropertyTaxValue(newPropertyTaxValue);

    Assertions.assertAll(
        ()
            -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> Assertions.assertEquals(propertyTaxValue, response.getBody()));
  }

  @Test
  void addPropertyTaxValueTestUpdate() {
    final CreationPropertyTaxValue newPropertyTaxValue =
        this.createCreationPropertyTaxValue();
    final PropertyTaxValue propertyTaxValue =
        PropertyTaxValue.builder()
            .propertyTaxValueId(1L)
            .region(newPropertyTaxValue.getRegion())
            .pricePerSquareMeter(newPropertyTaxValue.getPricePerSquareMeter())
            .cadastralValue(newPropertyTaxValue.getCadastralValue())
            .build();

    final PropertyTaxValue lastPropertyTaxValue =
        PropertyTaxValue.builder()
            .region(newPropertyTaxValue.getRegion())
            .pricePerSquareMeter(80000)
            .cadastralValue(20)
            .build();

    given(this.propertyTaxValueRepository.findPropertyTaxValueByRegion(
              lastPropertyTaxValue.getRegion()))
        .willReturn(propertyTaxValue);
    given(this.propertyTaxValueRepository.save(propertyTaxValue))
        .willReturn(propertyTaxValue);

    final ResponseEntity<PropertyTaxValue> response =
        this.propertyTaxService.addPropertyTaxValue(newPropertyTaxValue);

    Assertions.assertAll(
        ()
            -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
        ()
            -> Assertions.assertEquals(
                newPropertyTaxValue.getPricePerSquareMeter(),
                response.getBody().getPricePerSquareMeter()),
        ()
            -> Assertions.assertEquals(newPropertyTaxValue.getCadastralValue(),
                                       response.getBody().getCadastralValue()),
        ()
            -> Assertions.assertEquals(
                propertyTaxValue.getPropertyTaxValueId(),
                response.getBody().getPropertyTaxValueId()));
  }
}