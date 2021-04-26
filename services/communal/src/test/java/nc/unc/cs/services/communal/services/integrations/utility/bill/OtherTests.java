package nc.unc.cs.services.communal.services.integrations.utility.bill;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import nc.unc.cs.services.communal.entities.UtilityBill;
import nc.unc.cs.services.communal.repositories.UtilityBillRepository;
import nc.unc.cs.services.communal.services.CommunalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class OtherTests {
  @Mock private UtilityBillRepository utilityBillRepository;
  @InjectMocks private CommunalService communalService;

  @Test
  void getCitizenUtilityBillsTest() {
    final List<UtilityBill> utilityBills = new ArrayList<>();
    given(this.utilityBillRepository.findUtilityBillsByCitizenId(1L)).willReturn(utilityBills);
    final List<UtilityBill> response = this.communalService.getCitizenUtilityBills(1L);

    Assertions.assertNotNull(response);
    Assertions.assertTrue(response instanceof ArrayList);
  }
}
