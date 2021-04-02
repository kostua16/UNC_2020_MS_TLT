export default class PaymentPayload {
    constructor(
        serviceId,
        citizenId,
        amount,
        taxAmount
    ) {
        this.serviceId = serviceId
        this.citizenId = citizenId
        this.amount = amount
        this.taxAmount = taxAmount
    }
}