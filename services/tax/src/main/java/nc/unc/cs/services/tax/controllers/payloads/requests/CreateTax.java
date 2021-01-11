package nc.unc.cs.services.tax.controllers.payloads.requests;

public class CreateTax {
    private Long serviceId;
    private Long citizenId;
    private Integer taxAmount;

    public CreateTax() {
    }

    public CreateTax(
        final Long serviceId,
        final Long citizenId,
        final Integer taxAmount
    ) {
        this.serviceId = serviceId;
        this.citizenId = citizenId;
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

    public Integer getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Integer taxAmount) {
        this.taxAmount = taxAmount;
    }
}
