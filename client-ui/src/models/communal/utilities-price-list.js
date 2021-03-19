export default class UtilitiesPriceList {
    constructor(
        utilitiesPriceListId,
        region,
        coldWaterPrice,
        hotWaterPrice,
        electricityPrice
    ) {
        this.utilitiesPriceListId = utilitiesPriceListId
        this.region = region
        this.coldWaterPrice = coldWaterPrice
        this.hotWaterPrice = hotWaterPrice
        this.electricityPrice = electricityPrice
    }
}