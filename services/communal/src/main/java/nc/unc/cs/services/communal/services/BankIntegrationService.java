package nc.unc.cs.services.communal.services;

import feign.FeignException;
import nc.unc.cs.services.common.clients.bank.BankService;
import nc.unc.cs.services.common.clients.bank.PaymentPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankIntegrationService {
    /** Банковский сервис. */
    private final BankService bankService;

    /**
     * Конструктор.
     * @param bankService внедрение банковского сервиса
     */
    @Autowired
    public BankIntegrationService(final BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * Отправляет данные о проведённой операции в банк, для регистрации.
     *
     * @param serviceId идентификатор услуги
     * @param citizenId идентификатор гражданина
     * @param amount сумма к оплате
     * @param percent процент налога от стоимости услуги
     * @return идентификатор сформированного счёта
     * @throws FeignException если не удасться обратиться к Банковскому сервису
     */
    public Long bankRequest(
        final Long serviceId,
        final Long citizenId,
        final Integer amount,
        final Integer percent
    ) throws FeignException {
        final PaymentPayload paymentPayload = new PaymentPayload(
            serviceId,
            citizenId,
            amount,
            amount / percent
        );
        return this.bankService
            .requestPayment(paymentPayload).getBody();
    }

    /**
     * Возвращает статус оплаты выставленного счёта.
     *
     * @param paymentRequestId идентификатор выставленного счёта
     * @return статус оплаты
     * @throws FeignException если не удасться обратиться к Банковскому сервису
     */
    public Boolean checkPaymentStatus(
        final Long paymentRequestId
    ) throws FeignException {
        return this.bankService.checkPaymentStatus(paymentRequestId);
    }
}
