package nc.unc.cs.services.passport.exceptions;

public class InternationalPassportNotFoundException extends RuntimeException {
  public InternationalPassportNotFoundException(Long internationalId) {
    super("International Passport with ID = " + internationalId + "not found");
  }
}
