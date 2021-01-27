package nc.unc.cs.services.passport.repository;

import nc.unc.cs.services.passport.model.Domestic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomesticRepository extends CrudRepository<Domestic, Long> {

}

