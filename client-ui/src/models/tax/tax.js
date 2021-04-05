export default class Tax {
    constructor(taxId, taxAmount, status, creationDate, taxPaymentDate, citizenId, serviceId) {
        this.taxId = taxId
        this.taxAmount = taxAmount
        this.status = status
        this.creationDate = creationDate
        this.taxPaymentDate = taxPaymentDate
        this.citizenId = citizenId
        this.serviceId = serviceId
    }
}