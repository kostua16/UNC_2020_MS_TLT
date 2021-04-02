export default class Domestic {
    constructor(
        domesticId,
        registration,
        name,
        surname,
        dateOfBirth,
        isActive,
        series,
        number,
        citizenId,
    ) {
        this.domesticId = domesticId
        this.registration = registration
        this.name = name
        this.surname = surname
        this.dateOfBirth = dateOfBirth
        this.isActive = isActive
        this.series = series
        this.number = number
        this.citizenId = citizenId
    }
}