package nc.unc.cs.services.bank.services;

import java.util.Date;
import java.util.List;
import nc.unc.cs.services.bank.controllers.payloads.responses.TaxPayment;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.exceptions.PaymentRequestNotFoundException;
import nc.unc.cs.services.bank.integration.CreationTax;
import nc.unc.cs.services.bank.integration.TaxService;
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

    @Autowired
    public BankService(
        final PaymentRequestRepository paymentRequestRepository,
        final TransactionRepository transactionRepository,
        final TaxService taxService
    ) {
        this.paymentRequestRepository = paymentRequestRepository;
        this.transactionRepository = transactionRepository;
        this.taxService = taxService;
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
     * Service Provided Registration Method.
     *
     * @param serviceId The ID of the service that provided the service;
     * @param citizenId The Id of the citizen (account);
     * @param amount The cost of the service provided;
     * @param taxAmount Service tax;
     *
     * @return Payment ID;
     */
    public ResponseEntity<PaymentRequest> requestPayment(
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
            this.paymentRequestRepository.save(paymentRequest);

            return ResponseEntity.ok(paymentRequest);
        } catch (Exception e) {
            logger.error("No tax has been created.");

            return ResponseEntity.status(503).body(paymentRequest);
        }
    }

    /**
     * Payment of invoice and tax.
     *
     * @param paymentId;
     * @return ResponseEntity with transaction id;
     */
    public ResponseEntity<Transaction> payment(final Long paymentId) {

        PaymentRequest paymentRequest = findPaymentRequestById(paymentId);
        paymentRequest.setStatus(true);

        Transaction transaction = new Transaction();

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
            logger.info("Tax paid.");

            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            logger.error("Failed to pay tax!");
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

    public List<PaymentRequest> getPayments(Long citizenId) {
        return this.paymentRequestRepository.findAllByCitizenId(citizenId);
    }

}
