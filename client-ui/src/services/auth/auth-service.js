import axios from 'axios';
const API_URL = 'http://localhost:8888/auth';


class AuthService {
    login(user) {
        return axios
            .post(API_URL + '/sign-in', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                if (response.data.citizenId && response.data.role) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(registration) {
        return axios
            .post(API_URL + '/sign-up', {
                username: registration.username,
                password: registration.password,
                name: registration.name,
                surname: registration.surname,
                dateOfBirth: registration.dateOfBirth,
                registration: registration.registration
            })
            .then(response => {
                return response;
            })
            .catch(error => {
                console.error("RegistrationData failed! \n", error)
                return error;
            });
    }
}

export default new AuthService();