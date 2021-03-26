package nc.unc.cs.services.communal.exceptions;

public class PropertyTaxValueNotFoundException extends RuntimeException {
  public PropertyTaxValueNotFoundException(final String region) {
    super("PropertyTaxValue with region = " + region + " not found!");
  }
}
