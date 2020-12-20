package nc.unc.cs.services.tax.controllers.payloads;

import java.time.LocalDateTime;


public class UpdateTax {
    private Long taxId;
    private LocalDateTime taxPaymentDate;

    public UpdateTax() {
    }

    public UpdateTax(
        final Long taxId,
        final LocalDateTime taxPaymentDate
    ) {
        this.taxId = taxId;
        this.taxPaymentDate = taxPaymentDate;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public LocalDateTime getTaxPaymentDate() {
        return taxPaymentDate;
    }

    public void setTaxPaymentDate(LocalDateTime taxPaymentDate) {
        this.taxPaymentDate = taxPaymentDate;
    }
}
