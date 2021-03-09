package nc.unc.cs.services.communal.services.integrations.property_tax;

import static org.mockito.BDDMockito.given;

import java.util.Date;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.repositories.PropertyTaxRepository;
import nc.unc.cs.services.communal.services.BankIntegrationService;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ChangePropertyTaxTest {

    @Mock private PropertyTaxRepository propertyTaxRepository;
    @Mock private BankIntegrationService bankIntegrationService;

    @InjectMocks private PropertyTaxService propertyTaxService;

    @Test
    void changePropertyTaxStatusTest() {
        final PropertyTax propertyTax = new PropertyTax();
        propertyTax.setPropertyTaxId(1L);
        propertyTax.setPropertyId(1L);
        propertyTax.setTaxAmount(10000);
        propertyTax.setDate(new Date());
        propertyTax.setIsPaid(false);
        propertyTax.setCitizenId(111L);
        propertyTax.setPaymentRequestId(15L);

        given(this.propertyTaxService.getPropertyTaxById(propertyTax.getPropertyTaxId()))
                .willReturn(propertyTax);

        given(this.bankIntegrationService.checkPaymentStatus(propertyTax.getPaymentRequestId()))
                .willReturn(true);
        given(this.propertyTaxRepository.save(propertyTax)).willReturn(propertyTax);

        ResponseEntity<PropertyTax> response = this.propertyTaxService.changePropertyTaxStatus(1L);
        System.out.println(propertyTax.getIsPaid());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> Assertions.assertTrue(response.getBody().getIsPaid()));
    }
}
