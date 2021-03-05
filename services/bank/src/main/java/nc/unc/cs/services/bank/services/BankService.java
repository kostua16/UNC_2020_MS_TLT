package nc.unc.cs.services.bank.services;

import feign.FeignException;
import java.util.Date;
import java.util.List;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.exceptions.PaymentRequestNotFoundException;
import nc.unc.cs.services.bank.repositories.PaymentRequestRepository;
import nc.unc.cs.services.bank.repositories.TransactionRepository;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import nc.unc.cs.services.common.clients.logging.LogEntry;
import nc.unc.cs.services.common.clients.logging.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BankService {

  private static final Logger logger =
      LoggerFactory.getLogger(BankService.class);

  private final PaymentRequestRepository paymentRequestRepository;
  private final TransactionRepository transactionRepository;
  private final LoggingService logging;
  private final TaxIntegrationService taxIntegrationService;

  @Autowired
  public BankService(final PaymentRequestRepository paymentRequestRepository,
                     final TransactionRepository transactionRepository,
                     final TaxIntegrationService taxIntegrationService,
                     final LoggingService logging) {
    this.paymentRequestRepository = paymentRequestRepository;
    this.transactionRepository = transactionRepository;
    this.taxIntegrationService = taxIntegrationService;
    this.logging = logging;
  }

  /**
   * Возвращает выставленный счёт (PaymentRequest).
   * @param paymentRequestId идентификатор ыставленного счёта.
   */
  public PaymentRequest findPaymentRequestById(final Long paymentRequestId)
      throws PaymentRequestNotFoundException {
    return this.paymentRequestRepository.findById(paymentRequestId)
        .orElseThrow(
            () -> new PaymentRequestNotFoundException(paymentRequestId));
  }

  /**
   * Регистрация проведённых услуг.
   *
   * @param paymentPayload информация для регистрации усуги
   * @return идентификатор выставленного счёта;
   * @throws PaymentRequestNotFoundException
   *      если не удалсться найти PaymentRequest
   */
  public ResponseEntity<Long>
  requestPayment(final PaymentPayload paymentPayload) throws FeignException {
    final PaymentRequest paymentRequest =
        PaymentRequest.builder()
            .serviceId(paymentPayload.getServiceId())
            .citizenId(paymentPayload.getCitizenId())
            .status(false)
            .amount(paymentPayload.getAmount())
            .build();

    final Long taxId = this.taxIntegrationService.createTax(
        paymentPayload.getServiceId(), paymentPayload.getCitizenId(),
        paymentPayload.getTaxAmount());
    paymentRequest.setTaxId(taxId);

    logger.info("Tax with ID = {} has been created", taxId);
    logging.addLog(
        LogEntry.builder()
            .service("bank")
            .created(new Date())
            .message(String.format(
                "Tax with ID = %d has been created for serviceId = %d, citizenId = %d",
                taxId, paymentPayload.getServiceId(),
                paymentPayload.getCitizenId()))
            .build());
    this.paymentRequestRepository.save(paymentRequest);

    return ResponseEntity.ok(paymentRequest.getPaymentRequestId());
  }

  /**
   * Payment of invoice and tax.
   *
   * @param paymentRequestId идентификатор выставленного счёта
   * @return http-ответ, в теле которого находится чек
   * @throws FeignException если не удасться обратиться к Банковскому сервису
   * @throws PaymentRequestNotFoundException
   *      если не удалсться найти PaymentRequest
   */
  public ResponseEntity<Transaction> payment(final Long paymentRequestId)
      throws FeignException, PaymentRequestNotFoundException {

    final PaymentRequest paymentRequest;
    final ResponseEntity<Transaction> response;

    paymentRequest = findPaymentRequestById(paymentRequestId);
    Boolean isPaid = paymentRequest.getStatus();
    if (Boolean.TRUE.equals(isPaid)) {
      logger.error("Payment Request with ID = {} already paid!",
                   paymentRequestId);
      response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } else {
      paymentRequest.setStatus(true);
      final Transaction transaction = Transaction.builder()
                                          .paymentRequestId(paymentRequestId)
                                          .amount(paymentRequest.getAmount())
                                          .creationDate(new Date())
                                          .build();
      this.taxIntegrationService.payTax(paymentRequest.getTaxId(),
                                        transaction.getCreationDate());
      logging.addLog(
          LogEntry.builder()
              .service("bank")
              .created(new Date())
              .message(String.format("Tax paid for id = %d", paymentRequestId))
              .build());

      this.transactionRepository.save(transaction);
      this.paymentRequestRepository.save(paymentRequest);
      response = ResponseEntity.ok(transaction);
    }
    return response;
  }

  /**
   * Checking the status of the issued invoice.
   *
   * @param paymentId;
   * @return payment status;
   */
  public Boolean isPaid(final Long paymentId) {
    return findPaymentRequestById(paymentId).getStatus();
  }

  public List<PaymentRequest> getDebtPaymentRequests(final Long citizenId) {
    return this.paymentRequestRepository.findAllByCitizenIdAndStatus(citizenId,
                                                                     false);
  }
}
