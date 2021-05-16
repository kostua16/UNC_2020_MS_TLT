package nc.unc.cs.services.communal.services;

import java.util.List;
import feign.FeignException;
import nc.unc.cs.services.common.clients.passport.PassportService;
import nc.unc.cs.services.common.clients.passport.UpdateRegistrationIdDto;
import nc.unc.cs.services.communal.controllers.payloads.CreationProperty;
import nc.unc.cs.services.communal.controllers.payloads.CreationRegistration;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.Registration;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.RegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
  /** Логгер. */
  private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
  /** Налоговый процент от стоимости платежа. */
  public static final Integer TAX_PERCENT = 50;
  /** Номер сервиса. */
  public static final Long SERVICE_ID = 19L;
  /** Стоимость предоставляемой услуги */
  public static final Integer SERVICE_COST = 2000;

  private final PropertyRepository propertyRepository;
  private final RegistrationRepository registrationRepository;
  private final BankIntegrationService bankIntegrationService;
  private final PassportService passportService;

  @Autowired
  public RegistrationService(
      final PropertyRepository propertyRepository,
      final RegistrationRepository registrationRepository,
      final BankIntegrationService bankIntegrationService,
      final PassportService passportService
  ) {
    this.propertyRepository = propertyRepository;
    this.registrationRepository = registrationRepository;
    this.bankIntegrationService = bankIntegrationService;
    this.passportService = passportService;
  }

  /**
   * Возвращает активную регистрацю пользователя.
   *
   * @param citizenId идентификатор гражданина
   * @return активная регистрация гражданина
   */
  public Registration getActiveRegistrationByCitizenId(final Long citizenId) {
    return this.registrationRepository.findRegistrationByCitizenIdAndIsActive(citizenId, true);
  }

  /**
   * Добавляет / Изменяет прописку гражданина.
   *
   * @param creationRegistration новое место регистрации
   * @return http-ответ, в теле которого находится данные о прописке
   */
  public ResponseEntity<Registration> addRegistration(
      final CreationRegistration creationRegistration) {
    final ResponseEntity<Registration> response;
    final Registration registration =
        Registration.builder()
            .region(creationRegistration.getRegion())
            .city(creationRegistration.getCity())
            .street(creationRegistration.getStreet())
            .house(creationRegistration.getHouse())
            .apartment(creationRegistration.getApartment())
            .isActive(true)
            .citizenId(creationRegistration.getCitizenId())
            .build();
    final Registration lastRegistration =
        this.getActiveRegistrationByCitizenId(creationRegistration.getCitizenId());

    this.bankIntegrationService.bankRequest(
        SERVICE_ID, registration.getCitizenId(), SERVICE_COST, TAX_PERCENT);
    if (lastRegistration != null) {
      lastRegistration.setIsActive(false);
      this.registrationRepository.save(lastRegistration);
    }
    this.registrationRepository.save(registration);
    logger.info(
        "Registration has been added to the citizen with ID = {}", registration.getCitizenId());
    response = ResponseEntity.ok(registration);

    this.updateDomestic(registration.getCitizenId(), registration.getRegistrationId());

    return response;
  }

  public void updateDomestic(final Long citizenId, final Long registrationId) throws FeignException {
    final UpdateRegistrationIdDto updateRegistrationIdDto = new UpdateRegistrationIdDto(registrationId);
    this.passportService.updateDomesticRegistration(citizenId, updateRegistrationIdDto);
  }

  /**
   * Возвращает регистрацию по идентификатору.
   *
   * @param registrationId регистрационный идентификатор
   * @return регистрация
   */
  public Registration getRegistrationByRegistrationId(final Long registrationId) {
    return this.registrationRepository.findRegistrationByRegistrationId(registrationId);
  }

  /**
   * Возвращает список регистраций гражданина.
   *
   * @param citizenId идентификатор гражданина
   * @return список регистраций
   */
  public List<Registration> getAllRegistrations(final Long citizenId) {
    return this.registrationRepository.findRegistrationsByCitizenId(citizenId);
  }

  /**
   * Ищет недвижимость по адресу.
   *
   * @param property данные о недвижимости
   * @return недвижимость
   */
  public Property getPropertyByAddress(final CreationProperty property) {
    return this.propertyRepository.findPropertyByRegionAndCityAndStreetAndHouseAndApartment(
        property.getRegion(),
        property.getCity(),
        property.getStreet(),
        property.getHouse(),
        property.getApartment());
  }

  /**
   * Добавляет недвижимость. Если недвижимость уже существует, то обновляется владелец.
   *
   * @param newProperty данные о недвижимсоти
   * @return http-ответ, в теле которого находится данные о недвижимости
   */
  public ResponseEntity<Property> addCitizensProperty(final CreationProperty newProperty) {
    final ResponseEntity<Property> response;
    final Property property;
    final Property lastProperty = this.getPropertyByAddress(newProperty);

    if (lastProperty == null) {
      property =
          Property.builder()
              .region(newProperty.getRegion())
              .city(newProperty.getCity())
              .street(newProperty.getStreet())
              .house(newProperty.getHouse())
              .apartment(newProperty.getApartment())
              .apartmentSize(newProperty.getApartmentSize())
              .citizenId(newProperty.getCitizenId())
              .build();
      logger.info("New property added");
    } else {
      lastProperty.setCitizenId(newProperty.getCitizenId());
      property = lastProperty;
      logger.info("Property owner updated");
    }
    this.bankIntegrationService.bankRequest(
        SERVICE_ID, property.getCitizenId(), SERVICE_COST, TAX_PERCENT);
    this.propertyRepository.save(property);
    logger.info("Added property for user with ID = {}", property.getCitizenId());
    response = ResponseEntity.ok(property);

    return response;
  }

  /**
   * Возвращает список недвижимостей гражданина.
   *
   * @param citizenId идентификатор гражданина
   * @return список недвижимостей
   */
  public List<Property> getPropertiesByCitizenId(final Long citizenId) {
    return this.propertyRepository.findPropertyByCitizenId(citizenId);
  }

  /**
   * Возвращает список со всей недвижимостью из БД.
   *
   * @return список недвижимости
   */
  public List<Property> getAllProperties() {
    return this.propertyRepository.findAll();
  }
}
