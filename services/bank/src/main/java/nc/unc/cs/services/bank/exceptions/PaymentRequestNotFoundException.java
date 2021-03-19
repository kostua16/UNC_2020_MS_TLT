package nc.unc.cs.services.bank.exceptions;

public class PaymentRequestNotFoundException extends RuntimeException {
  public PaymentRequestNotFoundException(final Long paymentId) {
    super("Payment request with ID: " + paymentId + " was not found!");
  }
}
