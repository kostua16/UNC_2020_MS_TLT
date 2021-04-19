export default class CreationProperty {
    constructor(apartmentSize) {
        this.region = ''
        this.city = ''
        this.street = ''
        this.house = ''
        this.apartment = ''
        this.apartmentSize = apartmentSize
        this.citizenId = 0
    };
    constructor(
        region,
        city,
        street,
        house,
        apartment,
        apartmentSize,
        citizenId
    ) {
        this.region = region
        this.city = city
        this.street = street
        this.house = house
        this.apartment = apartment
        this.apartmentSize = apartmentSize
        this.citizenId = citizenId
    };
}