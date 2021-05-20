export default class Transaction {
    constructor(
        transactionId,
        creationDate,
        amount,
        paymentRequestId,
        serviceTitle
    ) {
        this.transactionId = transactionId
        this.creationDate = creationDate
        this.amount = amount
        this.paymentRequestId = paymentRequestId
        this.serviceTitle = serviceTitle
    }
}