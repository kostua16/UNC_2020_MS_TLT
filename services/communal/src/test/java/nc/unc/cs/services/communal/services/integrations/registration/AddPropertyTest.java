package nc.unc.cs.services.communal.services.integrations.registration;

import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.services.BankIntegrationService;
import nc.unc.cs.services.communal.services.RegistrationService;
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
class AddPropertyTest {
    private static final Logger logger = LoggerFactory.getLogger(AddPropertyTest.class);

    /** Налоговый процент от стоимости платежа. */
    public static final Integer TAX_PERCENT = RegistrationService.TAX_PERCENT;
    /** Номер сервиса. */
    public static final Long SERVICE_ID = RegistrationService.SERVICE_ID;
    /** Стоимость предоставляемой услуги */
    public static final Integer SERVICE_COST = RegistrationService.SERVICE_COST;

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private BankIntegrationService bankIntegrationService;

    @InjectMocks
    private RegistrationService registrationService;

    private Property createProperty() {
        return Property
            .builder()
            .propertyId(1L)
            .region("  samara   ")
            .city("samara")
            .street("main")
            .house("11")
            .apartment("11")
            .apartmentSize(1000)
            .citizenId(111L)
            .build();
    }

    @Test
    void addNewPropertyTest() {
        Property property = this.createProperty();

        given(this.registrationService.getPropertyByAddress(property)).willReturn(null);

            given(this.bankIntegrationService.bankRequest(
                SERVICE_ID, property.getCitizenId(), SERVICE_COST, TAX_PERCENT
            )).willReturn(15L);

        given(this.propertyRepository.save(property)).willReturn(property);

        ResponseEntity<Property> response = this.registrationService.addCitizensProperty(property);

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertEquals(property.getRegion(), response.getBody().getRegion())
        );
    }

    @Test
    void updatePropertyOwner() {
        Property property = this.createProperty();

        given(this.registrationService.getPropertyByAddress(property)).willReturn(property);

        given(this.bankIntegrationService.bankRequest(
            SERVICE_ID, property.getCitizenId(), SERVICE_COST, TAX_PERCENT
        )).willReturn(15L);

        given(this.propertyRepository.save(property)).willReturn(property);

        ResponseEntity<Property> response = this.registrationService.addCitizensProperty(property);

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
            () -> Assertions.assertEquals(property.getRegion(), response.getBody().getRegion())
        );
    }
}
