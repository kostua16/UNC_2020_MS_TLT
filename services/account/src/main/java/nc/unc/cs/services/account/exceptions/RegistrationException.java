package nc.unc.cs.services.account.exceptions;

public class RegistrationException extends RuntimeException {
  public RegistrationException(final String username) {
    super(String.format("Citizen with username = %d is already registered!", username));
  }
}
