package nc.unc.cs.services.passport.service;

import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import nc.unc.cs.services.common.clients.bank.PaymentRequest;
import nc.unc.cs.services.passport.PassportApp;
import nc.unc.cs.services.passport.model.Citizen;
import nc.unc.cs.services.passport.model.Domestic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = PassportApp.class)
public class PassportTest {


    @InjectMocks
    private static PassportTable passportTable;

    @Mock
    private BankService bankService;


    @Test
    public void getPassport() {
        Citizen citizen = new Citizen();
        citizen.setCitizenId(21L);
        citizen.setSurname("Pupkin");
        citizen.setName("Vasya");
        citizen.setRegistration("Samara");
        citizen.setDateOfBirth(new Date(1234L));

        when(bankService.requestPayment(new PaymentPayload(2L, citizen.getCitizenId(), 2000, 200)))
                .thenReturn(ResponseEntity.ok(213L));

        Domestic domestic = passportTable.registerDomesticPassport(citizen).getBody();

        assertAll(
                () -> assertEquals(domestic.getDateOfBirth(), citizen.getDateOfBirth()),
                () -> assertEquals(domestic.getRegistration(), citizen.getRegistration()),
                () -> assertEquals(domestic.getName(), citizen.getName()),
                () -> assertEquals(domestic.getSurname(), citizen.getSurname()),
                () -> assertEquals(domestic.getCitizenId(), citizen.getCitizenId()),
                () -> assertEquals(domestic.getIsActive(), false),
                () -> assertTrue(domestic.getSeries() >= 1000 && domestic.getSeries() <= 9999),
                () -> assertTrue(domestic.getNumber() >= 100000 && domestic.getNumber() <= 999999)
        );
    }

}