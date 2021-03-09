package nc.unc.cs.services.communal.repositories;

import java.util.List;
import nc.unc.cs.services.communal.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository
    extends JpaRepository<Registration, Long> {

  Registration findRegistrationByRegistrationId(Long registrationId);

  List<Registration> findRegistrationsByCitizenId(Long citizenId);

  Registration findRegistrationByCitizenIdAndIsActive(Long citizenId,
                                                      Boolean isActive);
}
