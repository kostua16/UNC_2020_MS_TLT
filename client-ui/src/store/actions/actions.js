import axios from 'axios'
import AuthModule from '@/store/auth.module'

const HTTP_PROTOCOL = process.env.VUE_APP_PROTOCOL || 'http';

const HOST_AND_PORT_BANK = process.env.VUE_APP_BANK_URL || 'localhost:8084';
const HOST_AND_PORT_COMMUNAL = process.env.VUE_APP_COMMUNAL_URL || 'localhost:8083';
// const HOST_AND_PORT_TAX = process.env.VUE_APP_TAX_URL || 'localhost:8082';
const HOST_AND_PORT_PASSPORT = process.env.VUE_APP_PASSPORT_URL || 'localhost:8095';
// const HOST_AND_PORT_GIBDD = process.env.VUE_APP_GIBDD_URL || 'localhost:8088';
// const HOST_AND_PORT_LOGGING = process.env.VUE_APP_LOGGING_URL || 'localhost:8089';

const URL_BANK = HTTP_PROTOCOL + '://' + HOST_AND_PORT_BANK + '/bank'
const URL_COMMUNAL = HTTP_PROTOCOL + '://' + HOST_AND_PORT_COMMUNAL + '/communal'
// const URL_TAX = HTTP_PROTOCOL + '://' + HOST_AND_PORT_TAX + '/tax'
const URL_PASSPORT = HTTP_PROTOCOL + '://' + HOST_AND_PORT_PASSPORT + '/passport'

const PROPERTY_API_URL = URL_COMMUNAL + '/property';

export default {
    GET_PROPERTIES_FROM_API({commit}) {
        return axios
            .get(
                PROPERTY_API_URL + '/citizen/' + AuthModule.state.user.citizenId,
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

    ADD_PROPERTY_ACTION({commit}, creationProperty) {
        return axios.post(
            PROPERTY_API_URL,
            {
                region: creationProperty.region,
                city: creationProperty.city,
                street: creationProperty.street,
                house: creationProperty.house,
                apartment: creationProperty.apartment,
                apartmentSize: creationProperty.apartmentSize,
                citizenId: AuthModule.state.user.citizenId
            },
            {}
        )
            .then(response => {
                commit('ADD_PROPERTY', response.data);
                return response.status
            })
            .catch(error => {
                console.error('Failed to add property', error.response.headers);
                return error.response.status;
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
                URL_BANK + '/check/' + AuthModule.state.user.citizenId,
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
    },

    PAY_PAYMENT_REQUEST_ACTION({commit}, paymentRequestId) {
        return axios
            .put(
                URL_BANK + '/payment/' + paymentRequestId,
                {},
                {},
            )
            .then(response => {
                commit('PAY_PAYMENT_REQUEST', paymentRequestId);
                return response.status;
            })
            .catch(error => {
                console.error('Не удалось оплатить выставленный счёт!')
                return error.response.status;
            })
    },

    GET_UTILITIES_PRICE_LIST_FROM_API({commit}) {
        return axios
            .get(
                URL_COMMUNAL + '/utilities/price-list/',
                {},
            )
            .then(response => {
                commit('SET_UTILITIES_PRICE_LIST_TO_STATE', response.data);
                return response;
            })
            .catch(error => {
                console.log('Failed to get information about utilities price lists. \n' + error);
                return error;
            })
    },

    ADD_UTILITIES_PRICE_LIST({commit, state}, priceList) {
        return axios
            .post(
                URL_COMMUNAL + '/utilities/price-list',
                {
                    region: priceList.region,
                    coldWaterPrice: priceList.coldWaterPrice,
                    hotWaterPrice: priceList.hotWaterPrice,
                    electricityPrice: priceList.electricityPrice
                },
                {}
            )
            .then(response => {
                const priceListFromApi = response.data;
                const utilitiesPriceListId = state
                    .utilityBillPriceLists
                    .findIndex(item => item.utilitiesPriceListId === priceListFromApi.utilitiesPriceListId);
                console.log("state ", state.utilityBillPriceLists)
                console.log("id ", utilitiesPriceListId)
                if (utilitiesPriceListId > -1) {
                    console.log("The price list is being updated...")
                    commit('UPDATE_UTILITIES_PRICE_LIST', priceListFromApi, utilitiesPriceListId)
                } else {
                    console.log("The price list began to be saved...")
                    commit('ADD_UTILITIES_PRICE_LIST', priceListFromApi)
                }
                return response.status;
            })
            .catch(error => {
                console.log("Failed to save utilities price list!\n", error);
                return error.response.status;
            })
    },

    GET_PROPERTY_TAX_VALUE_FROM_API({commit}) {
        return axios
            .get(
                URL_COMMUNAL + '/tax/price-list/',
                {},
            )
            .then(response => {
                commit('SET_PROPERTY_TAX_VALUE_TO_STATE', response.data);
                return response;
            })
            .catch(error => {
                console.log('Failed to get information about utilities price lists. \n' + error);
                return error;
            })
    },

    ADD_PROPERTY_TAX_VALUE({commit, state}, priceList) {
        return axios
            .post(
                URL_COMMUNAL + '/tax/price-list/',
                {
                    region: priceList.region,
                    pricePerSquareMeter: priceList.pricePerSquareMeter,
                    cadastralValue: priceList.cadastralValue
                },
                {}
            )
            .then(response => {
                const priceListFromApi = response.data;
                const propertyTaxValueId = state
                    .propertyTaxValues
                    .findIndex(item => item.propertyTaxValueId === priceListFromApi.propertyTaxValueId);
                if (propertyTaxValueId > -1) {
                    console.log("The price list is being updated...")
                    commit('UPDATE_PROPERTY_TAX_VALUE', priceListFromApi, propertyTaxValueId)
                } else {
                    console.log("The price list began to be saved...")
                    commit('ADD_PROPERTY_TAX_VALUE', priceListFromApi)
                }
                return response.status;
            })
            .catch(error => {
                console.log("Failed to save utilities price list!\n", error);
                return error.response.status;
            })
    },

    GET_PERIOD_TRANSACTION_FROM_API({commit}, period) {
        return axios
            .post(
                URL_BANK + '/transaction',
                {
                    citizenId: AuthModule.state.user.citizenId,
                    startDate: period.startDate,
                    endDate: period.endDate
                },
                {}
            )
            .then(response => {
                commit('SET_PERIOD_TRANSACTION_TO_STATE', response.data);
                return response.status;
            })
            .catch(error => {
                console.error("Не удалось получить список транзакций!\n", error.status)
                return error.response.status;
            })
    },

    GET_DOMESTIC_FROM_API({commit}) {
        return axios
            .get(
                URL_PASSPORT + '/domestic/citizen/' + AuthModule.state.user.citizenId,
                {}
            )
            .then(response => {
                commit('SET_DOMESTIC_TO_STATE', response.data);
                return response
            })
            .catch(error => {
                console.error('Passport not found!', error);
                return error;
            })
    },

    REGISTER_DOMESTIC_PASSPORT_ACTION({commit}, citizen) {
        return axios
            .post(
                URL_PASSPORT + '/registerDomestic',
                {
                    name: citizen.name,
                    surname: citizen.surname,
                    dateOfBirth: citizen.dateOfBirth,
                    registration: 'unknown', // todo: remove hard code
                    citizenId: AuthModule.state.user.citizenId,
                },
                {}
            )
            .then(response => {
                commit('SET_DOMESTIC_TO_STATE', response.data);
                return response.status;
            })
            .catch(error => {
                console.error('Failed to create domestic passport!', error.response.status);
                return error.response.status;
            })
    },

    // метод будет отредактирован после обновления api
    UPDATE_DOMESTIC_ACTION({commit}, updateDomestic) {
        return axios
            .post( // заменить на put
                URL_PASSPORT + '/updateDomestic/' + updateDomestic.domesticId,
                {
                    domesticId: updateDomestic.domesticId,
                    registration: updateDomestic.registration,
                    name: updateDomestic.name,
                    surname: updateDomestic.surname,
                    dateOfBirth: updateDomestic.dateOfBirth,
                    isActive: updateDomestic.isActive,
                    series: updateDomestic.series,
                    number: updateDomestic.number,
                    citizenId: updateDomestic.citizenId,
                },
                {}
            )
            .then(response => {
                commit('UPDATE_DOMESTIC', response.data);
                return response.status;
            })
            .catch(error => {
                console.error('Failed to update domestic passport.')
                return error.response.status;
            })
    },

    GET_INTERNATIONAL_FROM_API({commit}, citizenId) {
        return axios
            .get(URL_PASSPORT + '/international/citizen/' + citizenId, {})
            .then(response => {
                commit('SET_INTERNATIONAL_TO_STATE', response.data);
                return response.status;
            })
            .catch(error => {
                console.error('Failed to get international passport', error.statusMessage);
                return error.response.status;
            })
    },

    UPDATE_INTERNATIONAL_ACTION({commit}, international) {
        return axios
            .post(
                URL_PASSPORT + '/updateInternational/' + international.internationalId,
                {
                    internationalId: international.internationalId,
                    locked: international.locked,
                    name: international.name,
                    surname: international.surname,
                    dateOfBirth: international.dateOfBirth,
                    isActive: international.isActive,
                    citizenId: international.citizenId
                },
                {}
            )
            .then(response => {
                commit('UPDATE_INTERNATIONAL', response.data);
                return response.status;
            })
            .catch(error => {
                console.error('Failed to update international passport!', error.status)
                return error.response.status;
            })
    }

}