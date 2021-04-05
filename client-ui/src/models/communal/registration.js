export default class Registration {
    constructor(
        registrationId,
        region,
        city,
        street,
        house,
        apartment,
        isActive,
        citizenId
    ) {
        this.registrationId = registrationId
        this.region = region
        this.city = city
        this.street = street
        this.house = house
        this.apartment = apartment
        this.isActive = isActive
        this.citizenId = citizenId
    }
}