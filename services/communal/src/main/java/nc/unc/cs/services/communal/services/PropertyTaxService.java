package nc.unc.cs.services.communal.services;

import java.util.List;
import feign.FeignException;
import nc.unc.cs.services.communal.controllers.payloads.CreationPropertyTaxValue;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.exceptions.PropertyNotFoundException;
import nc.unc.cs.services.communal.exceptions.PropertyTaxNotFoundException;
import nc.unc.cs.services.communal.exceptions.PropertyTaxValueNotFoundException;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PropertyTaxService {
    /** Логгер. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyTaxService.class);

    /** Налоговый процент от стоимости платежа. */
    public static final Integer TAX_PERCENT = 10;
    /** Номер сервиса. */
    public static final Long SERVICE_ID = 20L;
    /** Процентный делитель. */
    public static final Double PERCENT_DIVISOR = 100.0;

    /** Репозиторий недвижимости. */
    private final PropertyRepository propertyRepository;
    /** Репозиторий налогов на недвижимость. */
    private final PropertyTaxRepository propertyTaxRepository;
    /** Репозиторий прейскурантов. */
    private final PropertyTaxValueRepository propertyTaxValueRepository;
    /** Класс с интеграциями с банковским сервисом. */
    private final BankIntegrationService bankIntegrationService;

    /**
     * Конструктор.
     *
     * @param propertyRepository репозиторий недвижимости
     * @param propertyTaxRepository репозиторий налогов на недвижимость
     * @param propertyTaxValueRepository репозиторий прейскурантов
     * @param bankIntegrationService класс с интеграциями с банковским сервисом.
     */
    @Autowired
    public PropertyTaxService(
        final PropertyRepository propertyRepository,
        final PropertyTaxRepository propertyTaxRepository,
        final PropertyTaxValueRepository propertyTaxValueRepository,
        final BankIntegrationService bankIntegrationService
    ) {
        this.propertyRepository = propertyRepository;
        this.propertyTaxRepository = propertyTaxRepository;
        this.propertyTaxValueRepository = propertyTaxValueRepository;
        this.bankIntegrationService = bankIntegrationService;
    }

    /**
     * Добавляет или изменяет прейскурант.
     *
     * @param newPropertyTaxValue новый прейскурант
     * @return http-ответ, в теле которого находится созданный прейскурант
     */
    public ResponseEntity<PropertyTaxValue> addPropertyTaxValue(
        final CreationPropertyTaxValue newPropertyTaxValue
    ) {
        final PropertyTaxValue propertyTaxValue = PropertyTaxValue
            .builder()
            .region(newPropertyTaxValue.getRegion())
            .pricePerSquareMeter(newPropertyTaxValue.getPricePerSquareMeter())
            .cadastralValue(newPropertyTaxValue.getCadastralValue())
            .build();

        final ResponseEntity<PropertyTaxValue> response;
        final PropertyTaxValue lastPropertyTaxValue =
            this.propertyTaxValueRepository
                .findPropertyTaxValueByRegion(
                    propertyTaxValue.getRegion());
        if (lastPropertyTaxValue == null) {
            this.propertyTaxValueRepository.save(propertyTaxValue);
            LOGGER.info("Property Tax Value has been created.");
            response = ResponseEntity.ok(propertyTaxValue);
        } else {
            lastPropertyTaxValue
                .setCadastralValue(propertyTaxValue
                    .getCadastralValue());
            lastPropertyTaxValue
                .setPricePerSquareMeter(propertyTaxValue
                    .getPricePerSquareMeter());
            this.propertyTaxValueRepository.save(lastPropertyTaxValue);
            LOGGER.info("PropertyTaxValue has been updated.");
            response = ResponseEntity.ok(lastPropertyTaxValue);
        }
        return response;
    }

    /**
     * Возвращает все прейскуранты.
     *
     * @return Налоговая квитанция
     */
    public List<PropertyTaxValue> getListPropertyTaxValue() {
        return this.propertyTaxValueRepository.findAll();
    }

    /**
     * Возвращает прейскурант по указанному идентификатору.
     *
     * @param propertyTaxValueId идентификатор прейскуранта
     * @return Налоговая квитанция
     */
    public PropertyTaxValue getPropertyTaxValueById(final Long propertyTaxValueId) {
        return this.propertyTaxValueRepository
            .findPropertyTaxValueByPropertyTaxValueId(propertyTaxValueId);
    }

    /**
     * Возвращает прейскурант для указанного региона.
     *
     * @param region название региона
     * @return Налоговая квитанция
     */
    public PropertyTaxValue getPropertyTaxValueByRegion(final String region) {
        return this.propertyTaxValueRepository
            .findPropertyTaxValueByRegion(region.trim().toUpperCase());
    }

    /**
     * Расчёт налога на имущество.
     *
     * @param apartmentSize кол-во кв.м. в помещении
     * @param pricePerSquareMeter стоимость кв.м. в данном регионе
     * @param cadastralValue кадастровый значение (процент) в данном регионе
     * @return расчитанный налог
     */
    public Integer calculatePropertyTaxAmount(
        final Double apartmentSize,
        final Double pricePerSquareMeter,
        final Double cadastralValue
    ) {
        return
            (int) (apartmentSize * pricePerSquareMeter / PERCENT_DIVISOR
                * (cadastralValue / PERCENT_DIVISOR));
    }

    /**
     * Возвращает прайс-лист для имущественного расчёта налога.
     *
     * @param region наименование региона
     * @return прайс-лист
     * @throws PropertyTaxValueNotFoundException если не удасться найти
     *          прайс-лист с заданным регионом
     */
    public PropertyTaxValue findPropertyTaxValueByRegion(
        final String region
    ) throws PropertyTaxValueNotFoundException {
        final PropertyTaxValue propertyTaxValue =
            this.propertyTaxValueRepository.findPropertyTaxValueByRegion(region);
        if (propertyTaxValue == null) {
            throw new PropertyTaxValueNotFoundException(region);
        }
        return propertyTaxValue;
    }

    /**
     * Возвращает данные о объект недвижимость.
     *
     * @param propertyId идентификатор недвижимости
     * @return объект недвижимость
     * @throws PropertyNotFoundException если не удасться найти недвижимость
     *          с заданным идентификатором
     */
    public Property findPropertyById(
        final Long propertyId
    ) throws PropertyNotFoundException {
        final Property property =
            this.propertyRepository.findPropertyByPropertyId(propertyId);
        if (property == null) {
            throw new PropertyNotFoundException(propertyId);
        }
        return property;
    }

    /**
     * Создаёт налог на недвижимость.
     *
     * @param propertyId идетнтификатор недвижимости
     * @return http-ответ, в теле которого находится налоговая квитанция
     * @throws FeignException если не удасться обратиться к Банковскому сервису
     */
    public ResponseEntity<PropertyTax> calculatePropertyTax(
        final Long propertyId
    ) throws FeignException {
        final Property property =
            this.findPropertyById(propertyId);
        final PropertyTaxValue propertyTaxValue =
            this.findPropertyTaxValueByRegion((property.getRegion()));
        final Integer amount = this.calculatePropertyTaxAmount(
            Double.valueOf(property.getApartmentSize()),
            Double.valueOf(propertyTaxValue.getPricePerSquareMeter()),
            Double.valueOf(propertyTaxValue.getCadastralValue()));

        final PropertyTax propertyTax = PropertyTax
            .builder()
            .propertyId(property.getPropertyId())
            .citizenId(property.getCitizenId())
            .taxAmount(amount)
            .paymentRequestId(
                this.bankIntegrationService.bankRequest(
                    SERVICE_ID,
                    property.getCitizenId(),
                    amount,
                    TAX_PERCENT))
            .build();
        this.propertyTaxRepository.save(propertyTax);
        LOGGER.info("PropertyTax successfully created");
        return ResponseEntity.ok(propertyTax);
    }

    /**
     * Проверяет и изменяет статуса опалаты налоговой квитанции.
     *
     * @param propertyTaxId идентификатор квитанции с данными о налоге
     * @return http-ответ, в теле которого находится
     *          изменённая налоговая квитанция
     */
    public ResponseEntity<PropertyTax> changePropertyTaxStatus(final Long propertyTaxId) {
        final ResponseEntity<PropertyTax> response;
        final PropertyTax propertyTax = this.getPropertyTaxById(propertyTaxId);
        final Boolean isPaid = this.bankIntegrationService
            .checkPaymentStatus(propertyTax.getPaymentRequestId());
        if (Boolean.TRUE.equals(isPaid)) {
            propertyTax.setIsPaid(true);
            this.propertyTaxRepository.save(propertyTax);
            LOGGER.info("PropertyTax with ID = {} has been paid", propertyTaxId);

            response = ResponseEntity.ok(propertyTax);
        } else {
            LOGGER.error("PropertyTax with ID = {} was not paid", propertyTax.getPropertyTaxId());
            response = ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .body(propertyTax);
        }
        return response;
    }

    /**
     * Возвращает все налоговые квитанции на недвижимость
     *          для конкретного гражданина.
     *
     * @param citizenId идентификатор гражданина
     * @return Список налоговых квитанций
     */
    public List<PropertyTax> getPropertyTaxesByCitizenId(final Long citizenId) {
        return this.propertyTaxRepository.findPropertyTaxByCitizenId(citizenId);
    }

    /**
     * Возвращает все налоговые квитанции на недвижимость
     *          для конкретного имущества.
     *
     * @param propertyId идентификатор недвижимости,
     *         на которую были расчитаны налоги
     * @return Список налоговых квитанций
     */
    public List<PropertyTax> getDebtPropertyTaxesByProperty(final Long propertyId) {
        return this.propertyTaxRepository.findPropertyTaxesByPropertyIdAndIsPaid(propertyId, false);
    }

    /**
     * Возвращает все налоговые квитанции.
     *
     * @return Список налоговых квитанций
     */
    public List<PropertyTax> getAllPropertyTax() {
        return this.propertyTaxRepository.findAll();
    }

    /**
     * Возвращает налоговую квитанции на недвижимость
     *          по указаноому идентификатору.
     *
     * @param propertyTaxId идентификатор налоговой квитанции
     * @return налоговая квитанция
     * @throws PropertyTaxNotFoundException если не найдётся налог на имущество
     */
    public PropertyTax getPropertyTaxById(
        final Long propertyTaxId
    ) throws PropertyTaxNotFoundException {
        final PropertyTax propertyTax = this.propertyTaxRepository
            .findPropertyTaxByPropertyTaxId(propertyTaxId);
        if (propertyTax == null) {
            throw new PropertyTaxNotFoundException(propertyTaxId);
        }
        return propertyTax;
    }
}
