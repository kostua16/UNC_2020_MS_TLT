package nc.unc.cs.services.communal.services.integrations.property_tax;

import static org.mockito.BDDMockito.given;

import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.repositories.PropertyTaxRepository;
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
class ChangePropertyTaxTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChangePropertyTaxTest.class);

  @Mock private PropertyTaxRepository propertyTaxRepository;
  @Mock private BankIntegrationService bankIntegrationService;

  @InjectMocks private PropertyTaxService propertyTaxService;

  private PropertyTax createPropertyTax() {
    return PropertyTax.builder()
        .paymentRequestId(1L)
        .propertyId(1L)
        .taxAmount(10000)
        .citizenId(111L)
        .paymentRequestId(15L)
        .build();
  }

  @Test
  void changePropertyTaxStatusTest() {
    final PropertyTax propertyTax = this.createPropertyTax();

    LOGGER.debug("Initial object state: {}", propertyTax);

    given(this.propertyTaxRepository.findPropertyTaxByPropertyTaxId(propertyTax.getPropertyTaxId()))
        .willReturn(propertyTax);

    given(this.bankIntegrationService.checkPaymentStatus(propertyTax.getPaymentRequestId()))
        .willReturn(true);
    given(this.propertyTaxRepository.save(propertyTax)).willReturn(propertyTax);

    final ResponseEntity<PropertyTax> response =
        this.propertyTaxService.changePropertyTaxStatus(propertyTax.getPropertyTaxId());

    LOGGER.debug("Final object state: {}", propertyTax);

    Assertions.assertAll(
        () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> Assertions.assertTrue(response.getBody().getIsPaid()));
  }

  @Test
  void changeAlreadyPaidPropertyTaxStatusTest() {
    final PropertyTax propertyTax = this.createPropertyTax();

    propertyTax.setIsPaid(true);
    given(this.propertyTaxRepository.findPropertyTaxByPropertyTaxId(propertyTax.getPropertyTaxId()))
        .willReturn(propertyTax);

    final ResponseEntity<PropertyTax> response =
        this.propertyTaxService.changePropertyTaxStatus(propertyTax.getPropertyTaxId());

    Assertions.assertAll(
        () -> Assertions.assertEquals(HttpStatus.PAYMENT_REQUIRED, response.getStatusCode()),
        () -> Assertions.assertTrue(response.getBody().getIsPaid()));
  }
}
