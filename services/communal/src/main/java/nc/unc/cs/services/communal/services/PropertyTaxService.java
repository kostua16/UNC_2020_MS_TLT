package nc.unc.cs.services.communal.services;

import java.util.Date;
import java.util.List;
import feign.FeignException;
import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTax;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
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
    private static final Logger logger = LoggerFactory.getLogger(PropertyTaxService.class);

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
    /** Интеграция с банковским сервисом. */
    private final BankService bankService;

    /**
     * Конструктор.
     * @param propertyRepository репозиторий недвижимости
     * @param  propertyTaxRepository репозиторий налогов на недвижимость
     * @param propertyTaxValueRepository репозиторий прейскурантов
     * @param bankService Банковский сервис
     */
    @Autowired
    public PropertyTaxService(
        final PropertyRepository propertyRepository,
        final PropertyTaxRepository propertyTaxRepository,
        final PropertyTaxValueRepository propertyTaxValueRepository,
        final BankService bankService
    ) {
        this.propertyRepository = propertyRepository;
        this.propertyTaxRepository = propertyTaxRepository;
        this.propertyTaxValueRepository = propertyTaxValueRepository;
        this.bankService = bankService;
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
        return (int) (apartmentSize * pricePerSquareMeter / PERCENT_DIVISOR * (cadastralValue / PERCENT_DIVISOR));
    }

    /**
     * Добавляет или изменяет прейскурант.
     *
     * @param propertyTaxValue новый прейскурант
     * @return http-ответ, в теле которого находится созданный прейскурант
     * @throws NullPointerException если пропущеныны какие либо поля
     */
    public ResponseEntity<PropertyTaxValue> addPropertyTaxValue(
        final PropertyTaxValue propertyTaxValue
    ) {
        ResponseEntity<PropertyTaxValue> response;
        if (
            propertyTaxValue.getCadastralValue() != null
                && propertyTaxValue.getPricePerSquareMeter() != null
                && propertyTaxValue.getRegion() != null
                && propertyTaxValue.getCadastralValue() > 0
                && propertyTaxValue.getPricePerSquareMeter() > 0
                && !propertyTaxValue.getRegion().trim().isEmpty()
        ) {
            try {
                final PropertyTaxValue lastPropertyTaxValue =
                    this.propertyTaxValueRepository
                        .findPropertyTaxValueByRegion(
                            propertyTaxValue.getRegion());
                if (lastPropertyTaxValue == null) {
                    this.propertyTaxValueRepository.save(propertyTaxValue);
                    logger.info("Property Tax Value has been created.");
                    response = ResponseEntity.ok(propertyTaxValue);
                } else {
                    lastPropertyTaxValue
                        .setCadastralValue(propertyTaxValue
                            .getCadastralValue());
                    lastPropertyTaxValue
                        .setPricePerSquareMeter(propertyTaxValue
                            .getPricePerSquareMeter());
                    this.propertyTaxValueRepository.save(lastPropertyTaxValue);
                    logger.info("PropertyTaxValue has been updated.");
                    response = ResponseEntity.ok(lastPropertyTaxValue);
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                logger.error("NPE"); // заглушка
                response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(propertyTaxValue);
            }
        } else {
            logger.error("Invalid input data!");
            response = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(propertyTaxValue);
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

    // добавить проверку на валидность входных данных

    /**
     * Создаёт налог на недвижимость.
     *
     * @param propertyId идетнтификатор недвижимости
     * @return http-ответ, в теле которого находится налоговая квитанция
     * @throws NullPointerException если не удасться найти недвижимостьея
     *          или прейскурант
     * @throws FeignException если не удасться обратиться к Банковскому сервису
     */
    public ResponseEntity<PropertyTax> calculatePropertyTax(final Long propertyId) {
        ResponseEntity<PropertyTax> response;
        final PropertyTax propertyTax = new PropertyTax();

        try {
            final Property property = this.propertyRepository.
                findPropertyByPropertyId(propertyId);
            final PropertyTaxValue propertyTaxValue =
                this.propertyTaxValueRepository
                    .findPropertyTaxValueByRegion(property.getRegion());

            propertyTax.setPropertyId(property.getPropertyId());
            propertyTax.setCitizenId(property.getCitizenId());
            propertyTax.setIsPaid(false);
            propertyTax.setDate(new Date());
            propertyTax.setTaxAmount(
                this.calculatePropertyTaxAmount(
                    Double.valueOf(property.getApartmentSize()),
                    Double.valueOf(propertyTaxValue.getPricePerSquareMeter()),
                    Double.valueOf(propertyTaxValue.getCadastralValue()))
            );
            Long paymentRequestId = this.bankService
                .requestPayment(new PaymentPayload(
                    SERVICE_ID,
                    propertyTax.getCitizenId(),
                    propertyTax.getTaxAmount(),
                    propertyTax.getTaxAmount() / TAX_PERCENT
                )).getBody();
            propertyTax.setPaymentRequestId(paymentRequestId);
            this.propertyTaxRepository.save(propertyTax);

            logger.info("PropertyTax successfully created");
            response = ResponseEntity.ok(propertyTax);
        } catch (NullPointerException npe) {
            logger.error("Data of property not found");
            npe.printStackTrace();
            response = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(propertyTax);
        } catch (FeignException fe) {

            logger.error("Failed to create PropertyTax!");
            logger.error("Failed to send a request to the BankService.");
            fe.printStackTrace();
            response = ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(propertyTax);
        } catch (Exception e) { // (ЗАГЛУШКА) нужно добавить проверок
            logger.error("Database error");
            e.printStackTrace();
            response = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(propertyTax);
        }
        return response;
    }

    /**
     * Проверяет и изменяет статуса опалаты налоговой квитанции.
     *
     * @param propertyTaxId идентификатор квитанции с данными о налоге
     * @return http-ответ, в теле которого находится
     *          изменённая налоговая квитанция
     * @throws NullPointerException если не удасться найти налоговую квитанцию
     * @throws FeignException если не удасться обратиться к Банковскому сервису
     */
    public ResponseEntity<PropertyTax> changePropertyTaxStatus(final Long propertyTaxId) {
        ResponseEntity<PropertyTax> response;
        final PropertyTax propertyTax = this.getPropertyTaxById(propertyTaxId);

        try {
            if (this.bankService.checkPaymentStatus(propertyTax.getPaymentRequestId())) {

                propertyTax.setIsPaid(true);
                this.propertyTaxRepository.save(propertyTax);
                logger.info("PropertyTax with ID = {} has been paid", propertyTaxId);

                response = ResponseEntity.ok(propertyTax);
            } else {
                logger.error("PropertyTax with ID = {} was not paid", propertyTax.getPropertyTaxId());
                response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(propertyTax);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            logger.error("PropertyTax with ID = {} not found", propertyTaxId);

            response = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(propertyTax);
        } catch (FeignException fe) {
            fe.printStackTrace();
            logger.info("Failed to verify payment");

            response = ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
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
     * @return Налоговая квитанция
     */
    public PropertyTax getPropertyTaxById(final Long propertyTaxId) {
        return this.propertyTaxRepository.findPropertyTaxByPropertyTaxId(propertyTaxId);
    }
}
