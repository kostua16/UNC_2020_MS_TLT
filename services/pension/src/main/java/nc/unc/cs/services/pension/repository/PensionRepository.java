package nc.unc.cs.services.pension.repository;



import nc.unc.cs.services.pension.model.Pension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PensionRepository extends JpaRepository<Pension, Long> {
    List<Pension> findPensionCardByCitizenId(Long citizenId);
}

