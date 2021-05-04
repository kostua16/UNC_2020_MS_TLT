package nc.unc.cs.services.communal.exceptions;

public class UtilityBillPaymentException extends RuntimeException {
  public UtilityBillPaymentException(final Long paymentRequestId) {
    super("Failed to change utility bill payment status with paymentRequestId = "+ paymentRequestId + "!");
  }
}
