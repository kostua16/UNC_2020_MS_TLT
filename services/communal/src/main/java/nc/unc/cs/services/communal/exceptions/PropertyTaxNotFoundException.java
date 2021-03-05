package nc.unc.cs.services.communal.exceptions;

public class PropertyTaxNotFoundException extends RuntimeException {
    public PropertyTaxNotFoundException(final Long id) {
        super("PropertyTax with Id = " + id + " not found!");
    }
}
