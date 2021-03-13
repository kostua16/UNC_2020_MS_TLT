package nc.unc.cs.services.communal.exceptions;

public class PropertyNotFoundException extends RuntimeException {
  public PropertyNotFoundException(final Long id) {
    super("Property with ID = " + id + " not found!");
  }
}
