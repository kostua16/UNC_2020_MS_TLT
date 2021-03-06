package nc.unc.cs.services.communal.services.integrations.property_tax;

import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.exceptions.PropertyNotFoundException;
import nc.unc.cs.services.communal.exceptions.PropertyTaxValueNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class CalculatePropertyTaxTest {
    private static final Logger logger = LoggerFactory.getLogger(CalculatePropertyTaxTest.class);

    /** Налоговый процент от стоимости платежа. */
    static final Integer TAX_PERCENT = PropertyTaxService.TAX_PERCENT;
    /** Номер сервиса. */
    static final Long SERVICE_ID = PropertyTaxService.SERVICE_ID;
    /** Процентный делитель. */
    static final Double PERCENT_DIVISOR = PropertyTaxService.PERCENT_DIVISOR;

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private PropertyTaxRepository propertyTaxRepository;
    @Mock
    private PropertyTaxValueRepository propertyTaxValueRepository;
    @Mock
    private BankIntegrationService bankIntegrationService;

    @InjectMocks
    private PropertyTaxService propertyTaxService;

    private Property getProperty() {
        return Property
            .builder()
            .propertyId(1L)
            .region(" samara ")
            .city("samara")
            .street("main")
            .house("12d")
            .apartment("11")
            .apartmentSize(1000)
            .citizenId(111L)
            .build();
    }

    PropertyTaxValue getPropertyTaxValue() {
        return PropertyTaxValue
            .builder()
            .propertyTaxValueId(1L)
            .region(" samara ")
            .pricePerSquareMeter(10000)
            .cadastralValue(15)
            .build();
    }

    @Test
    void calculatePropertyTaxTest() {
        final Property property = this.getProperty();
        final PropertyTaxValue propertyTaxValue = getPropertyTaxValue();

        given(this.propertyRepository.findPropertyByPropertyId(property.getPropertyId()))
            .willReturn(property);
        given(this.propertyTaxValueRepository.findPropertyTaxValueByRegion(property.getRegion()))
            .willReturn(propertyTaxValue);

        final Integer amount = this.propertyTaxService.calculatePropertyTaxAmount(
            Double.valueOf(property.getApartmentSize()),
            Double.valueOf(propertyTaxValue.getPricePerSquareMeter()),
            Double.valueOf(propertyTaxValue.getCadastralValue()));

        given(this.bankIntegrationService.bankRequest(
            SERVICE_ID, property.getCitizenId(), amount, TAX_PERCENT))
            .willReturn(15L);

        given(this.propertyTaxRepository.save(any()))
            .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        final ResponseEntity<PropertyTax> response =
            this.propertyTaxService.calculatePropertyTax(property.getPropertyId());

        logger.info("Response {}", response.getBody());

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertNotNull(response.getBody().getTaxAmount()),
            () -> Assertions.assertFalse(response.getBody().getIsPaid()),
            () -> Assertions.assertEquals(15L, response.getBody().getPaymentRequestId()));
    }

    @Test
    void propertyNotFoundCalculateTest() {
        given(this.propertyRepository.findPropertyByPropertyId(anyLong()))
            .willReturn(null);
        Assertions.assertThrows(
            PropertyNotFoundException.class,
            () -> this.propertyTaxService.calculatePropertyTax(anyLong())
        );
    }

    @Test
    void propertyTaxValueNotFoundCalculateTest() {
        final Property property = this.getProperty();

        given(this.propertyRepository.findPropertyByPropertyId(property.getPropertyId()))
            .willReturn(property);
        given(this.propertyTaxValueRepository.findPropertyTaxValueByRegion(property.getRegion()))
            .willReturn(null);

        Assertions.assertThrows(
            PropertyTaxValueNotFoundException.class,
            () -> this.propertyTaxService.calculatePropertyTax(property.getPropertyId())
        );
    }
}
