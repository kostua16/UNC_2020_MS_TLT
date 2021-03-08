// package nc.unc.cs.services.passport.service;
//
// import nc.unc.cs.services.common.clients.bank.BankService;
// import nc.unc.cs.services.common.clients.bank.PaymentPayload;
// import nc.unc.cs.services.passport.PassportApp;
// import nc.unc.cs.services.common.account.Citizen;
// import nc.unc.cs.services.passport.model.Domestic;
// import nc.unc.cs.services.passport.repository.DomesticRepository;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
//
// import java.util.Date;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.anyObject;
// import static org.mockito.Mockito.when;
//
//@ExtendWith(SpringExtension.class)
// public class PassportTest {
//
//
//    @InjectMocks
//    private static PassportTable passportTable;
//
//    @Mock
//    private BankService bankService;
//
//    @Mock
//    private DomesticRepository domesticRepository;
//
////    private Domestic createDomestic() {
////        return Domestic
////                .builder()
////
////                .build();
////    }
//
//    @Test
//    public void getPassport() {
//        Citizen citizen = new Citizen();
//        citizen.setCitizenId(21L);
//        citizen.setSurname("Pupkin");
//        citizen.setName("Vasya");
//        citizen.setRegistration("Samara");
//        citizen.setDateOfBirth(new Date(1234L));
//
////        final Domestic domestic = Domestic
////                .builder()
////                .name(citizen.getName())
////                .surname(citizen.getSurname())
////                .registration(citizen.getRegistration())
////                .dateOfBirth(citizen.getDateOfBirth())
////                .citizenId(citizen.getCitizenId())
//
//        when(bankService.requestPayment(anyObject()))
//                .thenReturn(ResponseEntity.ok(213L));
//
//        when(domesticRepository.save(anyObject())).thenReturn("domestic");
//        Domestic domestic =
//        passportTable.registerDomesticPassport(citizen).getBody().
//
//        assertAll(
//                () -> assertEquals(domestic.getDateOfBirth(),
//                citizen.getDateOfBirth()),
//                () -> assertEquals(domestic.getRegistration(),
//                citizen.getRegistration()),
//                () -> assertEquals(domestic.getName(), citizen.getName()),
//                () -> assertEquals(domestic.getSurname(),
//                citizen.getSurname()),
//                () -> assertEquals(domestic.getCitizenId(),
//                citizen.getCitizenId()),
//                () -> assertEquals(domestic.getIsActive(), false),
//                () -> assertTrue(domestic.getSeries() >= 1000 &&
//                domestic.getSeries() <= 9999),
//                () -> assertTrue(domestic.getNumber() >= 100000 &&
//                domestic.getNumber() <= 999999)
//        );
//    }
//
//}