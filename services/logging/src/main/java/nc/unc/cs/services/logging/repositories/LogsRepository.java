package nc.unc.cs.services.logging.repositories;

import nc.unc.cs.services.logging.entities.LogEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends JpaRepository<LogEntry, Long> {

  Page<LogEntry> findLogEntriesByService(String service, Pageable pageable);
}
