package nc.unc.cs.services.bank.repositories;

import nc.unc.cs.services.bank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository
    extends JpaRepository<Transaction, Long> {}
