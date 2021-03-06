package nc.unc.cs.services.passport.service;

import feign.FeignException;
import java.util.List;
import java.util.Random;
import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import nc.unc.cs.services.common.clients.tax.IdInfo;
import nc.unc.cs.services.common.clients.tax.TaxService;
import nc.unc.cs.services.passport.controller.dto.DomesticDto;
import nc.unc.cs.services.passport.controller.dto.InternationalDto;
import nc.unc.cs.services.passport.exceptions.DomesticPassportNotFoundException;
import nc.unc.cs.services.passport.exceptions.InternationalPassportNotFoundException;
import nc.unc.cs.services.passport.model.Citizen;
import nc.unc.cs.services.passport.model.Domestic;
import nc.unc.cs.services.passport.model.International;
import nc.unc.cs.services.passport.repository.DomesticRepository;
import nc.unc.cs.services.passport.repository.InternationalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PassportTable {
  /** Логгер. */
  private static final Logger logger = LoggerFactory.getLogger(PassportTable.class);

  private final InternationalRepository internationalRepository;
  private final DomesticRepository domesticRepository;
  private final Random random = new Random();
  private final BankService bankService;
  private final TaxService taxService;

  @Autowired
  public PassportTable(
      InternationalRepository internationalRepository,
      DomesticRepository domesticRepository,
      BankService bankService,
      TaxService taxService) {
    this.internationalRepository = internationalRepository;
    this.domesticRepository = domesticRepository;
    this.bankService = bankService;
    this.taxService = taxService;
  }

  /** Возвращает список заграничных паспортов. */
  public Iterable<International> getInternational() {
    return this.internationalRepository.findAll();
  }

  /**
   * Возвращает активную регистрацю пользователя.
   *
   * @param internationalId идентификатор гражданина
   * @return заграничный паспорт гражданина
   */
  public International getInternationalById(final Long internationalId) {
    return this.internationalRepository.findInternationalByInternationalId(internationalId);
  }

  /** Возвращает список отечественных паспортов. */
  public Iterable<Domestic> getDomestic() {
    return this.domesticRepository.findAll();
  }

  /**
   * Возвращает активную регистрацю пользователя.
   *
   * @param domesticId идентификатор гражданина
   * @return отечественный паспорт гражданина
   */
  public Domestic getDomesticById(final Long domesticId) {
    return this.domesticRepository.findDomesticByDomesticId(domesticId);
  }

  /**
   * Создает отечественный паспорт гражданина.
   *
   * @param citizen данные о гражданине
   * @return http-ответ, в теле которого находится данные об отечественном паспорте
   */
  public ResponseEntity<Domestic> registerDomesticPassport(Citizen citizen) {
    Domestic domestic = new Domestic();
    domestic.setName(citizen.getName());
    domestic.setSurname(citizen.getSurname());
    domestic.setDateOfBirth(citizen.getDateOfBirth());
    domestic.setIsActive(false);
    domestic.setCitizenId(citizen.getCitizenId());
    domestic.setSeries(random.nextInt(8999) + 1000);
    domestic.setNumber(random.nextInt(899999) + 100000);
    //        Сохранять в базу, только после успешной регистрации в банке и как это сделать?
    try {
      this.bankService.requestPayment(new PaymentPayload(2L, citizen.getCitizenId(), 2000, 200));
      this.domesticRepository.save(domestic);
      return ResponseEntity.ok(domestic);
    } catch (Exception e) {
      logger.error("Услуга не зарегистрирована");
      e.printStackTrace();
      return ResponseEntity.status(503).body(domestic);
    }
  }

  /**
   * Создает заграничный паспорт гражданина.
   *
   * @param citizen данные о гражданине
   * @return http-ответ, в теле которого находится данные о заграничном паспорте
   */
  public ResponseEntity<International> registerInternationalPassport(Citizen citizen) {
    International international = new International();
    international.setName(citizen.getName());
    international.setSurname(citizen.getSurname());
    international.setDateOfBirth(citizen.getDateOfBirth());
    international.setIsActive(false);
    international.setCitizenId(citizen.getCitizenId());
    try {
      international.setLocked(
          this.taxService.getListUnpaidTaxes(
              new IdInfo(
                  citizen.getCitizenId(), 3L))); // 2 - номер моего сервиса, добавть сшешяутШd
    } catch (Exception e) {
      logger.error("Не удалось проверить налоги");
    }
    try {
      this.bankService.requestPayment(new PaymentPayload(citizen.getCitizenId(), 2L, 3500, 350));
      return ResponseEntity.ok(this.internationalRepository.save(international));
    } catch (Exception e) {
      logger.error("Услуга не зарегистрирована");
      return ResponseEntity.status(503).body(international);
    }
  }

  /**
   * Обновление заграничного паспорта гражданина.
   *
   * @param id данные о гражданине
   * @param domestic данные о гражданине
   * @return http-ответ, в теле которого находится данные о заграничном паспорте
   */
  public ResponseEntity<Domestic> updateDomestic(final Long id, final DomesticDto domestic)
      throws FeignException {
    Domestic updateDomestic =
        domesticRepository
            .findById(id)
            .orElseThrow(() -> new DomesticPassportNotFoundException(id));
    updateDomestic.setName(domestic.getName());
    updateDomestic.setSurname(domestic.getSurname());
    updateDomestic.setDateOfBirth(domestic.getDateOfBirth());
    updateDomestic.setIsActive(domestic.getIsActive());
    updateDomestic.setSeries(domestic.getSeries());
    updateDomestic.setNumber(domestic.getNumber());
    try {
      this.bankService.requestPayment(
          new PaymentPayload(2L, updateDomestic.getCitizenId(), 3500, 350));
      this.domesticRepository.save(updateDomestic);
      return ResponseEntity.ok(updateDomestic);
    } catch (Exception e) {
      logger.error("Услуга не зарегистрирована");
      return ResponseEntity.status(503).body(updateDomestic);
    }
  }

  /**
   * Обновление отечественного паспорта гражданина.
   *
   * @param id данные о гражданине
   * @param international данные о гражданине
   * @return http-ответ, в теле которого находится данные о заграничном паспорте
   */
  public ResponseEntity<International> updateInternational(
      final Long id, InternationalDto international) {
    International updateInternational =
        internationalRepository
            .findById(id)
            .orElseThrow(() -> new InternationalPassportNotFoundException(id));
    updateInternational.setInternationalId(international.getInternationalId());
    updateInternational.setName(international.getName());
    updateInternational.setSurname(international.getSurname());
    updateInternational.setDateOfBirth(international.getDateOfBirth());
    updateInternational.setIsActive(international.getIsActive());
    try {
      this.bankService.requestPayment(
          new PaymentPayload(2L, updateInternational.getCitizenId(), 3500, 350));
      this.internationalRepository.save(updateInternational);
      return ResponseEntity.ok(updateInternational);
    } catch (Exception e) {
      logger.error("Услуга не зарегистрирована");
      return ResponseEntity.status(503).body(updateInternational);
    }
  }

  /**
   * активация заграничного паспорта гражданина.
   *
   * @param id данные о гражданине
   * @return http-ответ, в теле которого находится данные о заграничном паспорте
   */
  public Domestic activateDomestic(Long id) throws Exception {
    Domestic updateDomestic = domesticRepository.findById(id).orElse(null);
    if (updateDomestic == null) {
      throw new Exception("Cannot find domestic with id:" + id);
    }
    updateDomestic.setIsActive(true);
    return domesticRepository.save(updateDomestic);
  }

  /**
   * активация заграничного паспорта гражданина.
   *
   * @param id данные о гражданине
   * @return http-ответ, в теле которого находится данные о заграничном паспорте
   */
  public International activateInternational(Long id) throws Exception {
    International updateInternational = internationalRepository.findById(id).orElse(null);
    if (updateInternational == null) {
      throw new Exception("Cannot find domestic with id:" + id);
    }
    updateInternational.setIsActive(true);
    return internationalRepository.save(updateInternational);
  }

  /**
   * Возвращает паспорт гражданина.
   *
   * @param citizenId идентификатор гражданина
   * @return http-отве, в теле которого находится паспорт гражданина
   */
  public Domestic getDomesticByCitizenId(final Long citizenId) {
    return this.domesticRepository.findDomesticByCitizenId(citizenId);
  }

  /**
   * Возвращает паспорт гражданина.
   *
   * @param citizenId идентификатор гражданина
   * @return http-отве, в теле которого находится паспорт гражданина
   */
  public List<International> getInternationalsByCitizenId(final Long citizenId) {
    return this.internationalRepository.findInternationalsByCitizenId(citizenId);
  }

  /**
   * Обнавляет прописку пользователя.
   *
   * @param citizenId идентификатор пользователя
   * @param registrationId идентификатор прописки
   * @return паспорт гражданина
   * @throws DomesticPassportNotFoundException если не удасться найти пасспорт
   */
  public ResponseEntity<Long> updateDomesticRegistration(
      final Long citizenId, final Long registrationId) throws DomesticPassportNotFoundException {
    final Domestic domestic = this.domesticRepository.findDomesticByCitizenId(citizenId);
    if (domestic == null) {
      throw new DomesticPassportNotFoundException();
    } else {
      domestic.setRegistrationId(registrationId);
      this.domesticRepository.save(domestic);
      return ResponseEntity.ok(domestic.getDomesticId());
    }
  }
}
