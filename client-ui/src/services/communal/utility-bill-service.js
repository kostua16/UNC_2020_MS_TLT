import axios from 'axios'

const HTTP_PROTOCOL = process.env.VUE_APP_PROTOCOL || 'http';
const COMMUNAL_URL = process.env.VUE_APP_COMMUNAL_URL || 'localhost:8880';
const UTILITY_BILL_API_URL = HTTP_PROTOCOL + '://' + COMMUNAL_URL + '/communal/utilities';

class UtilityBillService {
    createPropertyTax(utilitiesPayload) {
        return axios
            .post(
                UTILITY_BILL_API_URL + '/create',
                {
                    propertyId: utilitiesPayload.propertyId,
                    coldWater: utilitiesPayload.coldWater,
                    hotWater: utilitiesPayload.hotWater,
                    electricity: utilitiesPayload.electricity
                },
                {}
            )
            .then(response => {
                console.log("Utility bill has been created.")
                return response.status;
            })
            .catch(error => {
                console.error("Error! Utility bill was not created!")
                return error.response.status;
            })
    }
}

export default new UtilityBillService();