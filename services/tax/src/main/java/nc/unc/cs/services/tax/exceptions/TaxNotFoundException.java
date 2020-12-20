package nc.unc.cs.services.tax.exceptions;

public class TaxNotFoundException extends RuntimeException {
    public TaxNotFoundException(final Long serviceId) {
        super("Tax with ID: " + serviceId + " was not found!");
    }

    public TaxNotFoundException() {
        super("The citizen has no tax debts.");
    }
}
