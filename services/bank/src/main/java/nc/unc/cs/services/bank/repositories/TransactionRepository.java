package nc.unc.cs.services.bank.repositories;

import java.util.Date;
import java.util.List;
import nc.unc.cs.services.bank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByCreationDateBetweenAndCitizenId(
      Date startDate, Date endDate, Long citizenId
  );
}
