import axios from 'axios';

const HTTP_PROTOCOL = process.env.VUE_APP_PROTOCOL || 'http';
const ACCOUNT_URL = process.env.VUE_APP_ACCOUNT_URL || 'localhost:8888';
const API_URL = HTTP_PROTOCOL + '://' + ACCOUNT_URL + '/auth';

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
                email: registration.email
            })
            .then(response => {
                return response.status;
            })
            .catch(error => {
                console.error("RegistrationData failed! \n", error)
                return error.status;
            });
    }
}

export default new AuthService();