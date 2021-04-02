export default class PropertyTaxValue {
    constructor(
        propertyTaxValueId,
        region,
        pricePerSquareMeter,
        cadastralValue
    ) {
        this.propertyTaxValueId = propertyTaxValueId
        this.region = region
        this.pricePerSquareMeter = pricePerSquareMeter
        this.cadastralValue = cadastralValue
    }
}