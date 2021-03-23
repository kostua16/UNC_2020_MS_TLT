export default class International {
    constructor(
        internationalId,
        locked,
        name,
        surname,
        dateOfBirth,
        isActive,
        citizenId,
    ) {
        this.internationalId = internationalId
        this.locked = locked
        this.name = name
        this.surname = surname
        this.dateOfBirth = dateOfBirth
        this.isActive = isActive
        this.citizenId = citizenId
    }
}