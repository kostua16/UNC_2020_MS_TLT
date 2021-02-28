package nc.unc.cs.services.communal.services;

import java.util.List;
import feign.FeignException;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.Registration;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.RegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    public RegistrationService(
        final PropertyRepository propertyRepository,
        final RegistrationRepository registrationRepository,
        final BankIntegrationService bankIntegrationService
    ) {
        this.propertyRepository = propertyRepository;
        this.registrationRepository = registrationRepository;
        this.bankIntegrationService = bankIntegrationService;
    }

    /**
     * Возвращает активную регистрацю пользователя.
     * @param citizenId идентификатор гражданина
     * @return активная регистрация гражданина
     */
    public Registration getActiveRegistrationByCitizenId(final Long citizenId) {
        return this.registrationRepository.findRegistrationByCitizenIdAndIsActive(citizenId, true);
    }

    /**
     * Добавляет / Изменяет прописку гражданина.
     *
     * @param registration место регистрации
     * @return http-ответ, в теле которого находится данные о прописке
     * @throws NullPointerException если пропущеныны какие-либо поля
     * @throws FeignException если не удасться обратиться к Банковскому сервису
     */
    public ResponseEntity<Registration> addRegistration(final Registration registration) {
        ResponseEntity<Registration> response;
        try { // ? id вытаскивать из токена или получать с фронта ?
            final Registration lastRegistration =
                this.getActiveRegistrationByCitizenId(registration.getCitizenId());
            registration.setIsActive(true);

            this.bankIntegrationService.bankRequest(
                SERVICE_ID, registration.getCitizenId(), SERVICE_COST, TAX_PERCENT
            );
            if (lastRegistration != null) {
                lastRegistration.setIsActive(false);
                this.registrationRepository.save(lastRegistration);
            }

            this.registrationRepository.save(registration);
            logger.info("Registration has been added to the citizen with ID = {}", registration.getCitizenId());
            response = ResponseEntity.ok(registration);
        } catch (NullPointerException npe) {
            logger.error("Invalid input data!", npe);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registration);
        } catch (FeignException fe) {
            logger.error(
                "Failed to add registration!"
                    + " Failed to send a request to the BankService.",
                fe
            );
            response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(registration);
        }
        return response;
    }

    /**
     * Возвращает регистрацию по идентификатору.
     * @param registrationId регистрационный идентификатор
     * @return регистрация
     */
    public Registration getRegistrationByRegistrationId(final Long registrationId) {
        return this.registrationRepository.findRegistrationByRegistrationId(registrationId);
    }

    /**
     * Возвращает список регистраций гражданина.
     * @param citizenId идентификатор гражданина
     * @return список регистраций
     */
    public List<Registration> getAllRegistrations(final Long citizenId) {
        return this.registrationRepository.findRegistrationsByCitizenId(citizenId);
    }

    /**
     * Ищет недвижимость по адресу.
     * @param property данные о недвижимости
     * @return недвижимость
     */
    public Property getPropertyByAddress(
        final Property property
    ) throws NullPointerException {
        return this.propertyRepository
            .findPropertyByRegionAndCityAndStreetAndHouseAndApartment(
                property.getRegion(),
                property.getCity(),
                property.getStreet(),
                property.getHouse(),
                property.getApartment()
            );
    }

    /**
     * Добавляет недвижимость.
     * Если недвижимость уже существует, то обнавляется владелец.
     *
     * @param property данные о недвижимсоти
     * @return http-ответ, в теле которого находится данные о недвижимости
     * @throws NullPointerException если пропущеныны какие-либо поля
     * @throws FeignException если не удасться обратиться к Банковскому сервису
     */
    public ResponseEntity<Property> addCitizensProperty(final Property property) {
        ResponseEntity<Property> response;

        try { // сохранения недвижимости после регистрации услуги
            final Property newProperty;
            final Property lastProperty = this.getPropertyByAddress(property);

            if (lastProperty == null) {
                newProperty = property;
                logger.info("New property added");
            } else {
                lastProperty.setCitizenId(property.getCitizenId());
                newProperty = lastProperty;
                logger.info("Property owner updated");
            }
            this.bankIntegrationService.bankRequest(
                SERVICE_ID, property.getCitizenId(), SERVICE_COST, TAX_PERCENT
            );
            this.propertyRepository.save(newProperty);

            response = ResponseEntity.ok(newProperty);
        } catch (NullPointerException npe) {
            logger.error("Invalid input data!", npe);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(property);
        } catch (FeignException fe) {
            logger.error(
                "Failed to privatize property!"
                    + " Failed to send a request to the BankService.",
                fe
            );
            response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(property);
        }
        return response;
    }

    /**
     * Возвращает список недвижимостей гражданина.
     * @param citizenId идентификатор гражданина
     * @return список недвижимостей
     */
    public List<Property> getPropertiesByCitizenId(final Long citizenId) {
        return this.propertyRepository.findPropertyByCitizenId(citizenId);
    }

}
