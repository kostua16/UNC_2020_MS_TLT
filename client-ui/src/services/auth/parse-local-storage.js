export default function getCitizenIdFromStorage() {
    let user = JSON.parse(localStorage.getItem('user'));

    if (user && user.citizenId) {
        return user.citizenId;
    } else {
        return {};
    }
}