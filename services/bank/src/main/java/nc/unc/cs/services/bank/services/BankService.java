package nc.unc.cs.services.bank.services;

import java.util.Date;
import java.util.List;
import nc.unc.cs.services.common.clients.logging.LogEntry;
import nc.unc.cs.services.common.clients.logging.LoggingService;
import nc.unc.cs.services.common.clients.tax.TaxPayment;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.exceptions.PaymentRequestNotFoundException;
import nc.unc.cs.services.common.clients.tax.CreationTax;
import nc.unc.cs.services.common.clients.tax.TaxService;
import nc.unc.cs.services.bank.repositories.PaymentRequestRepository;
import nc.unc.cs.services.bank.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    private static final Logger logger = LoggerFactory.getLogger(BankService.class);

    private final PaymentRequestRepository paymentRequestRepository;
    private final TransactionRepository transactionRepository;
    private final TaxService taxService;

    private final LoggingService logging;

    @Autowired
    public BankService(
        final PaymentRequestRepository paymentRequestRepository,
        final TransactionRepository transactionRepository,
        final TaxService taxService,
        final LoggingService logging
    ) {
        this.paymentRequestRepository = paymentRequestRepository;
        this.transactionRepository = transactionRepository;
        this.taxService = taxService;
        this.logging = logging;
    }

    /**
     * @param paymentId The ID of the payment by which the payment is searched in the database.
     */
    public PaymentRequest findPaymentRequestById(Long paymentId) {
        return this.paymentRequestRepository
            .findById(paymentId)
            .orElseThrow(
                () -> new PaymentRequestNotFoundException(paymentId)
            );
    }

    /**
     * Registration of provided services.
     *
     * @param serviceId The ID of the service that provided the service;
     * @param citizenId The Id of the citizen (account);
     * @param amount The cost of the service provided;
     * @param taxAmount Service tax;
     *
     * @return Payment ID;
     */
    public ResponseEntity<Long> requestPayment(
        final Long serviceId,
        final Long citizenId,
        final Integer amount,
        final Integer taxAmount
    ) {
        PaymentRequest paymentRequest = new PaymentRequest();

        paymentRequest.setAmount(amount + taxAmount);
        paymentRequest.setServiceId(serviceId);
        paymentRequest.setCitizenId(citizenId);
        paymentRequest.setStatus(false);

        try {
            Long taxId = this.taxService.createTax(new CreationTax(serviceId, citizenId, taxAmount));
            paymentRequest.setTaxId(taxId);

            logger.info("Tax with ID = {} has been created", taxId);
            logging.addLog(
                LogEntry
                    .builder()
                    .service("bank")
                    .created(new Date())
                    .message(
                        String.format(
                            "Tax with ID = %d has been created for serviceId = %d, citizenId = %d",
                            taxId,
                            serviceId,
                            citizenId
                        )
                    )
                    .build()
            );
            this.paymentRequestRepository.save(paymentRequest);

            return ResponseEntity.ok(paymentRequest.getPaymentRequestId());
        } catch (Exception e) {
            logger.error("No tax has been created.", e);
            logging.addLog(
                LogEntry
                    .builder()
                    .service("bank")
                    .created(new Date())
                    .message(
                        String.format(
                            "Tax wasn't created for serviceId = %d, citizenId = %d due %.3900s",
                            serviceId,
                            citizenId,
                            e.getMessage()
                        )
                    )
                    .build()
            );
            return ResponseEntity.status(503).body(-1L);
        }
    }

    /**
     * Payment of invoice and tax.
     *
     * @param paymentId;
     * @return ResponseEntity with transaction id;
     */
    public ResponseEntity<Transaction> payment(final Long paymentId) {

        Transaction transaction = new Transaction();
        PaymentRequest paymentRequest;

        try {
            paymentRequest = findPaymentRequestById(paymentId);
            if (paymentRequest.getStatus()) {
                logger.error("Payment Request with ID = {} already paid!", paymentId);
                return ResponseEntity.status(400).body(transaction);
            }
        } catch (PaymentRequestNotFoundException ne) {
            logger.error("Payment Request with ID = {} not found!", paymentId);
            ne.printStackTrace();
            return ResponseEntity.status(400).body(transaction);
        }

        paymentRequest.setStatus(true);

        transaction.setPaymentRequestId(paymentRequest.getPaymentRequestId());
        transaction.setAmount(paymentRequest.getAmount());
        transaction.setCreationDate(new Date());

        TaxPayment taxPayment = new TaxPayment();
        taxPayment.setTaxId(paymentRequest.getTaxId());
        taxPayment.setTaxPaymentDate(transaction.getCreationDate());

        try {
            this.taxService.payTax(taxPayment);
            this.transactionRepository.save(transaction);
            this.paymentRequestRepository.save(paymentRequest);
            logger.info("new payment request: {}", paymentRequest); ////////////////////
            logger.info("Tax paid.");
            logging.addLog(
                LogEntry
                    .builder()
                    .service("bank")
                    .created(new Date())
                    .message(String.format("Tax paid for id = %d", paymentId))
                    .build()
            );

            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            logger.error("Failed to pay tax!", e);
            logging.addLog(
                LogEntry
                    .builder()
                    .service("bank")
                    .created(new Date())
                    .message(
                        String.format(
                            "Failed to pay tax for id = %d due %.3900s", paymentId, e.getMessage()
                        )
                    )
                    .build()
            );
            return ResponseEntity.status(503).body(transaction);
        }
    }

    /**
     * Checking the status of the issued invoice.
     *
     * @param paymentId;
     * @return payment status;
     */
    public Boolean isPaid(Long paymentId) {
        return findPaymentRequestById(paymentId).getStatus();
    }

    public List<PaymentRequest> getDebtPaymentRequests(Long citizenId) {
        return this.paymentRequestRepository.findAllByCitizenIdAndStatus(citizenId, false);
    }

}
