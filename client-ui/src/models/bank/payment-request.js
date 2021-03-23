export default class PaymentRequest {
    constructor(
        paymentRequestId,
        serviceId,
        citizenId,
        status,
        amount,
        taxId
    ) {
        this.paymentRequestId = paymentRequestId
        this.serviceId = serviceId
        this.citizenId = citizenId
        this.status = status
        this.amount = amount
        this.taxId = taxId
    }
}