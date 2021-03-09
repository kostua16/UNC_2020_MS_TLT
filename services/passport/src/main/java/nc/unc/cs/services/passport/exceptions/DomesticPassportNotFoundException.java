package nc.unc.cs.services.passport.exceptions;

public class DomesticPassportNotFoundException extends RuntimeException {
  public DomesticPassportNotFoundException(Long domesticId) {
    super("Domestic Passport with ID = " + domesticId + "not found");
  }
}
