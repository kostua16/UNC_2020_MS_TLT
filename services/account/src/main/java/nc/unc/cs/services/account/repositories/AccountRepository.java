package nc.unc.cs.services.account.repositories;

import nc.unc.cs.services.account.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findAccountByUsernameAndPassword(final String username, final String password);

  Account findAccountByUsername(final String username);

  Account findAccountByCitizenId(final Long citizenId);
}
