package nc.unc.cs.services.communal.exceptions;

public class UtilitiesPriceListNotFoundException extends RuntimeException {
  public UtilitiesPriceListNotFoundException(final String region) {
    super("UtilitiesPriceList with region = " + region + " not found!");
  }
}
