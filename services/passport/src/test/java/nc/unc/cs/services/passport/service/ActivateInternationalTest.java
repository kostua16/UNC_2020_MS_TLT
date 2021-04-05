package nc.unc.cs.services.passport.service;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;
import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.passport.model.International;
import nc.unc.cs.services.passport.repository.InternationalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ActivateInternationalTest {

  @InjectMocks private PassportTable passportTable;

  @Mock private BankService bankService;

  @Mock private InternationalRepository internationalRepository;

  private International createInternational() {
    return International.builder()
        .internationalId(1L)
        .locked(false)
        .name("Nikita")
        .surname("Sss")
        .dateOfBirth(new Date(5555))
        .isActive(false)
        .citizenId(111L)
        .build();
  }

  @Test
  void getInternationalPassport() throws Exception {

    final International international = this.createInternational();

    when(this.internationalRepository.findById(international.getInternationalId()))
        .thenReturn(Optional.of(international));
    when(this.internationalRepository.save(international)).thenReturn(international);

    final International response =
        this.passportTable.activateInternational(international.getInternationalId());

    Assertions.assertTrue(response.getIsActive());
  }
}
