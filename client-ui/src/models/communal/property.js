export default class Property {
    constructor(
        propertyId,
        region,
        city,
        street,
        house,
        apartment,
        apartmentSize,
        citizenId
    ) {
        this.propertyId = propertyId
        this.region = region
        this.city = city
        this.street = street
        this.house = house
        this.apartment = apartment
        this.apartmentSize = apartmentSize
        this.citizenId = citizenId
    }
}