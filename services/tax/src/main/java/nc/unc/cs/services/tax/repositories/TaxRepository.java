package nc.unc.cs.services.tax.repositories;

import java.util.List;
import nc.unc.cs.services.tax.entities.Tax;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

  List<Tax> findTaxesByServiceIdAndCitizenIdAndStatus(
      Long serviceId, Long citizenId, Boolean status);

  Page<Tax> findAllByStatus(Boolean status, Pageable pageable);
}

// передеать репозиторий, чтобы возвращал "page"
