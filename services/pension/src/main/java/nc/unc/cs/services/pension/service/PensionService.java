package nc.unc.cs.services.pension.service;
import nc.unc.cs.services.common.account.Citizen;
import nc.unc.cs.services.pension.model.Pension;
import nc.unc.cs.services.pension.repository.PensionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Service
public class PensionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PensionService.class);
    @Autowired
    private PensionRepository pensionRepository;

    private final int pensionKoef = 5;
    private final int pensionPrice = 200;

    public ResponseEntity<Pension> createPension(final Citizen citizen) throws Exception {
        LOGGER.info("{}",citizen);
        final ResponseEntity<Pension> response;
        final Pension pension = new Pension();
        final int age = this.getAge(citizen.getDateOfBirth());
        if(age < 63) {
            // нужно выкинуть ошибку и поймать в контроллере, и через респонс ексепшн отдать бэд реквест, с комментарием
            throw new Exception("Человеек не является пенсионером");
        } else {
            pension.setPensionExperience(age);
            pension.setAmountOfPension(this.createAmountOfPension(age));
            pension.setCitizenId(citizen.getCitizenId());
            this.pensionRepository.save(pension);
            LOGGER.info("Pension card for citizen with ID = {} has been created.", citizen.getCitizenId());
            response = ResponseEntity.ok(pension);
        }
        return response;
    }

    public Integer getAge(final Date date) {
        final LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final LocalDate now = LocalDate.now();
        final Period period = Period.between(localDate, now);
        return period.getYears();
    }

    public Integer createAmountOfPension(final Integer age) {
        return age * pensionKoef * pensionPrice;
    }
}



