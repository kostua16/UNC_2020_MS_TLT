package nc.unc.cs.services.passport.repository;

import nc.unc.cs.services.passport.model.International;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface InternationalRepository extends JpaRepository<International, Long> {

    International findInternationalByInternationalId(Long internationalId);
}
