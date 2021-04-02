import axios from 'axios'

const API_URL = 'http://localhost:8083/communal/property/'

export default {
    GET_PROPERTIES_FROM_API({commit}, citizenId) {
        return axios
            .get(
                API_URL + 'citizen/' + citizenId,
                {},
            )
            .then((properties) => {
                commit('SET_PROPERTY_TO_STATE', properties.data);
                return properties;
            })
            .catch((error) => {
                console.log('Failed to get information about the user\'s property. \n' + error);
                return error;
            })
    }
}