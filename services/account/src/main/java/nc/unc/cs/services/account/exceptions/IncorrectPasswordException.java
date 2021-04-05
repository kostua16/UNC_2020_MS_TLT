package nc.unc.cs.services.account.exceptions;

public class IncorrectPasswordException extends RuntimeException {
  public IncorrectPasswordException(final String username) {
    super(String.format("Incorrect password for input username %s", username));
  }
}
