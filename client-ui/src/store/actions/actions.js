import axios from 'axios'

const COMMUNAL_API_URL = 'http://localhost:8083/communal';
const PROPERTY_API_URL = COMMUNAL_API_URL + '/property';

export default {
    GET_PROPERTIES_FROM_API({commit}, citizenId) {
        return axios
            .get(
                PROPERTY_API_URL + '/citizen/' + citizenId,
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
    },
    GET_ALL_PROPERTIES_FROM_API({commit}) {
        return axios
            .get(
                PROPERTY_API_URL + '/all',
                {},
            )
            .then(response => {
                commit('SET_ALL_PROPERTIES_TO_STATE', response.data);
                return response;
            })
            .catch(error => {
                console.log("Error! Failed to get all properties from Communal API \n" + error)
                return error;
            })
    },
}