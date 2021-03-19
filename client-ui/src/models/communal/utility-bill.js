export default class UtilityBill {
    constructor(
        utilityBillId,
        date,
        isPaid,
        coldWater,
        hotWater,
        electricity,
        coldWaterAmount,
        hotWaterAmount,
        electricityAmount,
        utilityAmount,
        paymentRequestId,
        propertyId,
        citizenId
    ) {
        this.utilityBillId = utilityBillId
        this.date = date
        this.isPaid = isPaid
        this.coldWater = coldWater
        this.hotWater = hotWater
        this.electricity = electricity
        this.coldWaterAmount = coldWaterAmount
        this.hotWaterAmount = hotWaterAmount
        this.electricityAmount = electricityAmount
        this.utilityAmount = utilityAmount
        this.paymentRequestId = paymentRequestId
        this.propertyId = propertyId
        this.citizenId = citizenId
    }
}