package nc.unc.cs.services.passport.repository;

import nc.unc.cs.services.passport.controller.dto.DomesticDTO;
import nc.unc.cs.services.passport.model.Domestic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomesticRepository extends JpaRepository<Domestic, Long> {

    Domestic findDomesticByDomesticId(Long domesticId);
}
