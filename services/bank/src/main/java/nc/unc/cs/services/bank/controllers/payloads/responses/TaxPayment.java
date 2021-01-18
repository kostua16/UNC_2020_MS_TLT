package nc.unc.cs.services.bank.controllers.payloads.responses;

import java.util.Date;

public class TaxPayment {
    private Long taxId;
    private Date taxPaymentDate;

    public TaxPayment() {
    }

    public TaxPayment(
        final Long taxId,
        final Date taxPaymentDate
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

    public Date getTaxPaymentDate() {
        return taxPaymentDate;
    }

    public void setTaxPaymentDate(Date taxPaymentDate) {
        this.taxPaymentDate = taxPaymentDate;
    }
}
