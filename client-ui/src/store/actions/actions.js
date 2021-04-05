import axios from 'axios'
import ParseLocalStorage from '@/services/auth/parse-local-storage'

const HTTP_PROTOCOL = 'http';

// hostnames
const HOST_AND_PORT_BANK = 'localhost:8084';
const HOST_AND_PORT_COMMUNAL = 'localhost:8083';
const HOST_AND_PORT_TAX = 'localhost:8082';
const HOST_AND_PORT_PASSPORT = 'localhost:8095';
const HOST_AND_PORT_GIBDD = 'localhost:8088';
const HOST_AND_PORT_LOGGING = 'localhost:8089';

const URL_BANK = HTTP_PROTOCOL + '://' + HOST_AND_PORT_BANK + '/bank'
const URL_COMMUNAL = HTTP_PROTOCOL + '://' + HOST_AND_PORT_COMMUNAL + '/communal'
const URL_TAX = HTTP_PROTOCOL + '://' + HOST_AND_PORT_TAX + '/tax'
const URL_PASSPORT = HTTP_PROTOCOL + '://' + HOST_AND_PORT_PASSPORT + '/passport'

const PROPERTY_API_URL = URL_COMMUNAL + '/property';

export default {
    GET_PROPERTIES_FROM_API({commit}) {
        return axios
            .get(
                PROPERTY_API_URL + '/citizen/' + ParseLocalStorage(),
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

    GET_MY_PAYMENT_REQUESTS_FROM_API({commit}) {
        return axios
            .get(
                URL_BANK + '/check/' + ParseLocalStorage(),
                {},
            )
            .then((properties) => {
                commit('SET_PAYMENT_REQUESTS_TO_STATE', properties.data);
                return properties;
            })
            .catch((error) => {
                console.log('Failed to get information about payment requests. \n' + error);
                return error;
            })
    }
}