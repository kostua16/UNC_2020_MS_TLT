export default class UtilitiesPayload {
    constructor(
        propertyId,
        coldWater,
        hotWater,
        electricity
    ) {
        this.propertyId = propertyId
        this.coldWater = coldWater
        this.hotWater = hotWater
        this.electricity = electricity
    }
}