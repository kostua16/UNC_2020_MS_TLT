package nc.unc.cs.services.communal.integrations;

public class PaymentPayload {
    private Long serviceId;
    private Long citizenId;
    private Integer amount;
    private Integer taxAmount;

    public PaymentPayload() {
    }

    public PaymentPayload(
        final Long serviceId,
        final Long citizenId,
        final Integer amount,
        final Integer taxAmount
    ) {
        this.serviceId = serviceId;
        this.citizenId = citizenId;
        this.amount = amount;
        this.taxAmount = taxAmount;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Integer taxAmount) {
        this.taxAmount = taxAmount;
    }
}