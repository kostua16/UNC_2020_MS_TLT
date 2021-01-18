package nc.unc.cs.services.bank.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import nc.unc.cs.services.bank.controllers.payloads.responses.TaxPayment;
import nc.unc.cs.services.bank.entities.PaymentRequest;
import nc.unc.cs.services.bank.entities.Transaction;
import nc.unc.cs.services.bank.exceptions.PaymentRequestNotFoundException;
import nc.unc.cs.services.bank.repositories.PaymentRequestRepository;
import nc.unc.cs.services.bank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {
    private final PaymentRequestRepository paymentRequestRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public BankService(
        final PaymentRequestRepository paymentRequestRepository,
        final TransactionRepository transactionRepository
    ) {
        this.paymentRequestRepository = paymentRequestRepository;
        this.transactionRepository = transactionRepository;
    }

    public PaymentRequest findPaymentRequestById(Long paymentId) {
        return this.paymentRequestRepository
            .findById(paymentId)
            .orElseThrow(
                () -> new PaymentRequestNotFoundException(paymentId)
            );
    }

    public Long requestPayment(
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

        paymentRequest.setTaxId(11111L); // заглушка

        return this.paymentRequestRepository.save(paymentRequest).getPaymentRequestId();
    }

    public TaxPayment payment(final Long paymentId) {
        PaymentRequest paymentRequest = findPaymentRequestById(paymentId);

        paymentRequest.setStatus(true);

        Transaction transaction = new Transaction();

        transaction.setPaymentRequestId(paymentRequest.getPaymentRequestId());
        transaction.setAmount(paymentRequest.getAmount());
        transaction.setCreationDate(new Date());

        this.transactionRepository.save(transaction);
        this.paymentRequestRepository.save(paymentRequest);

        return new TaxPayment(paymentRequest.getPaymentRequestId(), transaction.getCreationDate());
    }

    public Boolean isPaid(Long paymentId) {
        return findPaymentRequestById(paymentId).getStatus();
    }

}
