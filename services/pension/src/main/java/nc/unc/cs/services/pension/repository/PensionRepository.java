package nc.unc.cs.services.pension.repository;

import java.util.List;
import nc.unc.cs.services.pension.model.Pension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PensionRepository extends JpaRepository<Pension, Long> {
  List<Pension> findPensionCardByCitizenId(Long citizenId);
}
