package nc.unc.cs.services.communal.services.integrations.registration;

import static org.mockito.BDDMockito.given;

import nc.unc.cs.services.communal.controllers.payloads.CreationProperty;
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

@ExtendWith(SpringExtension.class)
class AddPropertyTest {
  private static final Logger logger = LoggerFactory.getLogger(AddPropertyTest.class);

  /** Налоговый процент от стоимости платежа. */
  public static final Integer TAX_PERCENT = RegistrationService.TAX_PERCENT;
  /** Номер сервиса. */
  public static final Long REGISTRATION_SERVICE_ID = RegistrationService.REGISTRATION_SERVICE_ID;
  /** Стоимость предоставляемой услуги */
  public static final Integer SERVICE_COST = RegistrationService.SERVICE_COST;

  @Mock private PropertyRepository propertyRepository;
  @Mock private BankIntegrationService bankIntegrationService;

  @InjectMocks private RegistrationService registrationService;

  protected final CreationProperty createCreationProperty() {
    return CreationProperty.builder()
        .region("Samara")
        .city("Samara")
        .street("main")
        .house("111")
        .apartment("1b")
        .apartmentSize(1000)
        .citizenId(111L)
        .build();
  }

  protected final Property createProperty() {
    final CreationProperty property = this.createCreationProperty();
    return Property.builder()
        .region(property.getRegion())
        .city(property.getCity())
        .street(property.getStreet())
        .house(property.getHouse())
        .apartment(property.getApartment())
        .apartmentSize(property.getApartmentSize())
        .citizenId(property.getCitizenId())
        .build();
  }

  @Test
  void addNewPropertyTest() {
    final CreationProperty creationProperty = this.createCreationProperty();
    final Property property = this.createProperty();

    given(this.registrationService.getPropertyByAddress(creationProperty)).willReturn(null);

    given(
            this.bankIntegrationService.bankRequest(
                REGISTRATION_SERVICE_ID, property.getCitizenId(), SERVICE_COST, TAX_PERCENT))
        .willReturn(15L);

    given(this.propertyRepository.save(property)).willReturn(property);

    ResponseEntity<Property> response =
        this.registrationService.addCitizensProperty(creationProperty);

    Assertions.assertAll(
        () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> Assertions.assertEquals(property.getRegion(), response.getBody().getRegion()));
  }

  @Test
  void updatePropertyOwner() {
    final CreationProperty creationProperty = this.createCreationProperty();
    final Property property = this.createProperty();
    final Property lastProperty = this.createProperty();
    lastProperty.setCitizenId(555L);

    given(this.registrationService.getPropertyByAddress(creationProperty)).willReturn(lastProperty);

    given(
            this.bankIntegrationService.bankRequest(
                REGISTRATION_SERVICE_ID, property.getCitizenId(), SERVICE_COST, TAX_PERCENT))
        .willReturn(15L);

    given(this.propertyRepository.save(property)).willReturn(property);

    ResponseEntity<Property> response =
        this.registrationService.addCitizensProperty(creationProperty);

    Assertions.assertAll(
        () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> Assertions.assertEquals(property.getRegion(), response.getBody().getRegion()));
  }
}
