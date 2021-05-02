package nc.unc.cs.services.communal.exceptions;

import java.util.Date;

public class PropertyNotFoundException extends RuntimeException {
  public PropertyNotFoundException(final Long id) {
    super("Property with ID = " + id + " not found!");
  }

  public PropertyNotFoundException(final Date beforeDate) {
    super("Property before tax date = " + beforeDate + " not found!");
  }
}
