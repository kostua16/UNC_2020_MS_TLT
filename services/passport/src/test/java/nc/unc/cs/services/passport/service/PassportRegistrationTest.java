package nc.unc.cs.services.passport.service;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.Date;
import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import nc.unc.cs.services.passport.model.Citizen;
import nc.unc.cs.services.passport.model.Domestic;
import nc.unc.cs.services.passport.repository.DomesticRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PassportRegistrationTest {

  @InjectMocks private static PassportTable passportTable;

  @Mock private BankService bankService;

  @Mock private DomesticRepository domesticRepository;

  @Test
  void getPassport() {
    Citizen citizen = new Citizen();
    citizen.setCitizenId(21L);
    citizen.setSurname("Pupkin");
    citizen.setName("Vasya");
    citizen.setDateOfBirth(new Date(1234L));

    final Domestic domestic =
        Domestic.builder()
            .name(citizen.getName())
            .surname(citizen.getSurname())
            .dateOfBirth(citizen.getDateOfBirth())
            .citizenId(citizen.getCitizenId())
            .build();

    PaymentPayload paymentPayload = new PaymentPayload(2L, citizen.getCitizenId(), 2000, 200);

    when(bankService.requestPayment(paymentPayload)).thenReturn(ResponseEntity.ok(1L));

    when(domesticRepository.save(anyObject())).thenAnswer(invocation -> invocation.getArgument(0));
    final ResponseEntity<Domestic> response = passportTable.registerDomesticPassport(citizen);

    Assertions.assertAll(
        () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> Assertions.assertEquals(citizen.getName(), response.getBody().getName()),
        () -> Assertions.assertFalse(response.getBody().getIsActive()));
  }
}
