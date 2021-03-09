package nc.unc.cs.services.bank.services;

import feign.FeignException;
import java.util.Date;
import nc.unc.cs.services.common.clients.tax.CreationTax;
import nc.unc.cs.services.common.clients.tax.TaxPayment;
import nc.unc.cs.services.common.clients.tax.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaxIntegrationService {
  private final TaxService taxService;

  @Autowired
  public TaxIntegrationService(final TaxService taxService) {
    this.taxService = taxService;
  }

  /**
   * Создание налога на предоставленную услугу.
   *
   * @param serviceId идентификатор сервиса предоставившего услугу
   * @param citizenId идентификатор гражданина
   * @param taxAmount сумма налога
   * @return идентификатор созданного налога;
   * @throws FeignException если не удасться обратиться к Банковскому сервису
   */
  public Long createTax(final Long serviceId, final Long citizenId, final Integer taxAmount)
      throws FeignException {
    final CreationTax creationTax =
        CreationTax.builder()
            .serviceId(serviceId)
            .citizenId(citizenId)
            .taxAmount(taxAmount)
            .build();

    return this.taxService.createTax(creationTax);
  }

  /**
   * Изменяет статус оплаты налога на "оплачен".
   *
   * @param taxId идентификатор налога
   * @param date дата оплаты выставленного счёта
   * @return http-ответ, в теле которого находится идентификатор оплаченного налога
   * @throws FeignException если не удасться обратиться к Банковскому сервису
   */
  public ResponseEntity<Long> payTax(final Long taxId, final Date date) throws FeignException {
    final TaxPayment taxPayment = new TaxPayment(taxId, date);
    return this.taxService.payTax(taxPayment);
  }
}
