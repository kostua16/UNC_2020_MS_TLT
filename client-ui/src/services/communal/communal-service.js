import axios from 'axios'
import AuthModule from "@/store/auth.module";

const HTTP_PROTOCOL = process.env.VUE_APP_PROTOCOL || 'http';
const COMMUNAL_URL = process.env.VUE_APP_API_URL || 'localhost:8880';
const API_URL = HTTP_PROTOCOL + '://' + COMMUNAL_URL + '/communal/housing';

class CommunalService {
    addRegistration(registration) {
        registration.citizenId = AuthModule.state.user.citizenId;
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
