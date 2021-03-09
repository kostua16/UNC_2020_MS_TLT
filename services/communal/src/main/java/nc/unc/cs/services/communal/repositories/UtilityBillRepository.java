package nc.unc.cs.services.communal.repositories;

import java.util.Date;
import java.util.List;
import nc.unc.cs.services.communal.entities.UtilityBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityBillRepository extends JpaRepository<UtilityBill, Long> {
    UtilityBill findUtilityBillIdByUtilityBillId(Long utilityBillId);

    List<UtilityBill> findUtilityBillsByCitizenId(Long citizenId);

    List<UtilityBill> findUtilityBillsByCitizenIdAndIsPaid(Long citizenId, Boolean isPaid);

    List<UtilityBill> findUtilityBillsByCitizenIdAndDateBetween(
            Long citizenId, Date start, Date end);
}
