import axios from 'axios'
import ParseLocalStorage from '../auth/parse-local-storage'

// const API_URL = 'https://nc-edu-2020-communal.herokuapp.com'
const API_URL = 'http://localhost:8083/communal/housing'

class CommunalService {
    addRegistration(registration) {
        registration.citizenId = ParseLocalStorage();
        return axios.post(API_URL + '/', {
                state: registration.state,
                city: registration.city,
                street: registration.street,
                house: registration.house,
                apartment: registration.apartment,
                citizenId: registration.citizenId
            }, {}
        )
            .then(response => {
                    console.log('RESPONSE REGISTRATION: ', response);
                    return response;
                }
            )
            .catch(error => {
                    console.error("Failed to add registration.", error);
                    return error;
                }
            );
    }
}

export default new CommunalService();
