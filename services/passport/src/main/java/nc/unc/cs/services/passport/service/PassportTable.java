package nc.unc.cs.services.passport.service;


import nc.unc.cs.services.passport.exceptions.DomesticPassportNotFoundException;
import nc.unc.cs.services.passport.integration.bank_service.BankService;
import nc.unc.cs.services.passport.integration.bank_service.PaymentPayload;
import nc.unc.cs.services.passport.integration.tax_service.IdInfo;
import nc.unc.cs.services.passport.integration.tax_service.TaxService;
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

import java.util.Random;

@Service
public class PassportTable {
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

    public Iterable<International> getInternational() {
        return this.internationalRepository.findAll();
    }

    public International getInternationalById(Long id) {
        return this.internationalRepository.findById(id).orElseGet(null);
    }

    public Iterable<Domestic> getDomestic() {
        return this.domesticRepository.findAll();
    }

    public Domestic getDomesticById(Long id) {
        return this.domesticRepository.findById(id).orElseGet(null);
    }

    public Domestic registerDomesticPassport(Citizen citizen) {
        Domestic domestic = new Domestic();
                domestic.setRegistration(citizen.getRegistration());
                domestic.setName(citizen.getName());
                domestic.setSurname(citizen.getSurname());
                domestic.setDateOfBirth(citizen.getDateOfBirth());
                domestic.setIsActive(false);
                domestic.setCitizenId(citizen.getCitizenId());
                domestic.setSeries(random.nextInt(8999) + 1000);
                domestic.setNumber(random.nextInt(899999) + 100000);
//        Сохранять в базу, только после успешной регистрации в банке и как это сделать?
        try {
            this.bankService.requestPayment(new PaymentPayload(citizen.getCitizenId(), 2L, 2000, 200));
            this.domesticRepository.save(domestic);
        } catch (Exception e) {
            logger.error("Услуга не зарегистрирована");
        }
        return domestic;
    }

    public International registerInternationalPassport(Citizen citizen) {
        International international = new International();
        international.setName(citizen.getName());
        international.setSurname(citizen.getSurname());
        international.setDateOfBirth(citizen.getDateOfBirth());
        international.setIsActive(false);
        international.setCitizenId(citizen.getCitizenId());
        try {
           international.setLocked(this.taxService.getListUnpaidTaxes(new IdInfo(citizen.getCitizenId(), 2L))); // 2 - номер моего сервиса, добавть сшешяутШd
        } catch (Exception e) {
            logger.error("Не удалось проверить налоги");
        }
        try {
            this.bankService.requestPayment(new PaymentPayload(citizen.getCitizenId(), 2L, 3500, 350));
            this.internationalRepository.save(international);
        } catch (Exception e) {
            logger.error("Услуга не зарегистрирована");
        }
          return international;
    }
// Правильно ли использовать ResponseEntity или возвращать объект
    public ResponseEntity<Domestic> updateDomestic(Long id, Domestic domestic)  {
        Domestic updateDomestic = domesticRepository.findById(id).orElseThrow(()-> new DomesticPassportNotFoundException(id));
        updateDomestic.setRegistration(domestic.getRegistration());
        updateDomestic.setName(domestic.getName());
        updateDomestic.setSurname(domestic.getSurname());
        updateDomestic.setDateOfBirth(domestic.getDateOfBirth());
        updateDomestic.setIsActive(domestic.getIsActive());
        updateDomestic.setSeries(domestic.getSeries());
        updateDomestic.setNumber(domestic.getNumber());
        try {
            this.bankService.requestPayment(new PaymentPayload(updateDomestic.getCitizenId() , 2L, 3500, 350));
            this.domesticRepository.save(updateDomestic);
            return ResponseEntity.ok(updateDomestic);
        } catch (Exception e) {
            logger.error("Услуга не зарегистрирована");
            return ResponseEntity.badRequest().body(domestic);
        }
    }

    public Domestic activateDomestic(Long id) throws Exception {
        Domestic updateDomestic = domesticRepository.findById(id).orElse(null);
        if (updateDomestic == null) {
            throw new Exception("Cannot find domestic with id:" + id);
        }
        updateDomestic.setIsActive(true);
        return domesticRepository.save(updateDomestic);
    }
}
