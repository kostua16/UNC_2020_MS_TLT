package nc.unc.cs.services.communal.services.integrations.property_tax;

import static org.mockito.BDDMockito.given;

import java.util.Date;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
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

@ExtendWith(SpringExtension.class)
class CalculatePropertyTaxTest {
  private static final Logger logger =
      LoggerFactory.getLogger(CalculatePropertyTaxTest.class);

  /** Налоговый процент от стоимости платежа. */
  static final Integer TAX_PERCENT = PropertyTaxService.TAX_PERCENT;
  /** Номер сервиса. */
  static final Long SERVICE_ID = PropertyTaxService.SERVICE_ID;
  /** Процентный делитель. */
  static final Double PERCENT_DIVISOR = PropertyTaxService.PERCENT_DIVISOR;

  @Mock private PropertyRepository propertyRepository;
  @Mock private PropertyTaxRepository propertyTaxRepository;
  @Mock private PropertyTaxValueRepository propertyTaxValueRepository;
  @Mock private BankIntegrationService bankIntegrationService;

  @InjectMocks private PropertyTaxService propertyTaxService;

  Property getProperty() {
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

  PropertyTaxValue getPropertyTaxValue() {
    PropertyTaxValue ptv = new PropertyTaxValue();
    ptv.setPropertyTaxValueId(1L);
    ptv.setRegion("samara  ");
    ptv.setPricePerSquareMeter(1000);
    ptv.setCadastralValue(15);

    return ptv;
  }

  @Test
  void calculatePropertyTaxTest() {
    final Property property = this.getProperty();
    PropertyTaxValue propertyTaxValue = getPropertyTaxValue();

    given(this.propertyRepository.findPropertyByPropertyId(1L))
        .willReturn(property);
    given(this.propertyTaxValueRepository.findPropertyTaxValueByRegion(
              property.getRegion()))
        .willReturn(propertyTaxValue);

    PropertyTax propertyTax = new PropertyTax();
    propertyTax.setPropertyId(property.getPropertyId());
    propertyTax.setIsPaid(false);
    propertyTax.setDate(new Date());
    propertyTax.setCitizenId(111L);
    propertyTax.setTaxAmount(this.propertyTaxService.calculatePropertyTaxAmount(
        Double.valueOf(property.getApartmentSize()),
        Double.valueOf(propertyTaxValue.getPricePerSquareMeter()),
        Double.valueOf(propertyTaxValue.getCadastralValue())));

    System.out.println(propertyTax.toString());

    given(this.bankIntegrationService.bankRequest(
              SERVICE_ID, property.getCitizenId(), propertyTax.getTaxAmount(),
              TAX_PERCENT))
        .willReturn(15L);

    propertyTax.setPaymentRequestId(1L);
    propertyTax.setPropertyTaxId(1L);
    given(this.propertyTaxRepository.save(propertyTax)).willReturn(propertyTax);

    ResponseEntity<PropertyTax> response =
        this.propertyTaxService.calculatePropertyTax(1L);

    Assertions.assertAll(
        ()
            -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
        ()
            -> Assertions.assertNotNull(response.getBody().getTaxAmount()),
        ()
            -> Assertions.assertFalse(response.getBody().getIsPaid()),
        ()
            -> Assertions.assertEquals(
                15L, response.getBody().getPaymentRequestId()));
  }
}
