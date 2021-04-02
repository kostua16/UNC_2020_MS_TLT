package nc.unc.cs.services.account.exceptions;

public class AccountNotFoundException extends RuntimeException {
  public AccountNotFoundException(
      final String username,
      final String password
  ) {
    super(String.format("Citizen with username = %d and password = %d not found!", username, password));
  }
}
