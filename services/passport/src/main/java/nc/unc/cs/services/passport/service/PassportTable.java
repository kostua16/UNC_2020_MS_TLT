package nc.unc.cs.services.passport.service;


import nc.unc.cs.services.passport.model.Citizen;
import nc.unc.cs.services.passport.model.Domestic;
import nc.unc.cs.services.passport.model.International;
import nc.unc.cs.services.passport.repository.DomesticRepository;
import nc.unc.cs.services.passport.repository.InternationalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PassportTable {
    private final InternationalRepository internationalRepository;
    private final DomesticRepository domesticRepository;
    private final Random random = new Random();


    @Autowired
    public PassportTable(
            InternationalRepository internationalRepository,
            DomesticRepository domesticRepository

    ) {
        this.internationalRepository = internationalRepository;
        this.domesticRepository = domesticRepository;
    }

    public Iterable<International> getInternational(){
        return this.internationalRepository.findAll();
    }

    public International getInternationalById(Long id){
        return this.internationalRepository.findById(id).orElseGet(null);
    }

    public Iterable<Domestic> getDomestic(){
        return this.domesticRepository.findAll();
    }

    public Domestic getDomesticById(Long id){
        return this.domesticRepository.findById(id).orElseGet(null);
    }

    public Domestic registerDomesticPassport(Citizen citizen) {
        return this.domesticRepository.save(new Domestic(
                citizen.getRegistration(),
                citizen.getName(),
                citizen.getSurname(),
                citizen.getDateOfBirth(),
                false,
                random.nextInt(8999) + 1000,
                random.nextInt(899999) + 100000
        ));
    }

    public International registerInternationalPassport(Citizen citizen) {
        return this.internationalRepository.save(new International(
                false,
                citizen.getName(),
                citizen.getSurname(),
                citizen.getDateOfBirth(),
                false
        ));
    }

    public Domestic updateDomestic(Long id,Domestic domestic) throws Exception {
        Domestic updateDomestic = domesticRepository.findById(id).orElse(null);
        if (updateDomestic == null) {
            throw new Exception("Cannot find domestic with id:" + id);
        }
        updateDomestic.setRegistration(domestic.getRegistration());
        updateDomestic.setName(domestic.getName());
        updateDomestic.setSurname(domestic.getSurname());
        updateDomestic.setDateOfBirth(domestic.getDateOfBirth());
        updateDomestic.setIsActive(domestic.getIsActive());
        updateDomestic.setSeries(domestic.getSeries());
        updateDomestic.setNumber(domestic.getNumber());
        return domesticRepository.save(updateDomestic);
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
