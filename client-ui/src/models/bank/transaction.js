export default class Transaction {
    constructor(
        transactionId,
        creationDate,
        amount,
        paymentRequestId
    ) {
        this.transactionId = transactionId
        this.creationDate = creationDate
        this.amount = amount
        this.paymentRequestId = paymentRequestId
    }
}