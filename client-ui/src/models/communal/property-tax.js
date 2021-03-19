export default class PropertyTax {
    constructor(
        propertyTaxId,
        taxAmount,
        isPaid,
        date,
        propertyId,
        paymentRequestId,
        citizenId
    ) {
        this.propertyTaxId = propertyTaxId
        this.taxAmount = taxAmount
        this.isPaid = isPaid
        this.date = date
        this.propertyId = propertyId
        this.paymentRequestId = paymentRequestId
        this.citizenId = citizenId
    }
}