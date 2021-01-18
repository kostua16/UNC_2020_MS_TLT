package nc.unc.cs.services.tax.controllers.payloads;

public class IdInfo {
    private Long citizenId;
    private Long serviceId;

    public IdInfo() {
    }

    public IdInfo(
        final Long citizenId,
        final Long serviceId
    ) {
        this.citizenId = citizenId;
        this.serviceId = serviceId;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
