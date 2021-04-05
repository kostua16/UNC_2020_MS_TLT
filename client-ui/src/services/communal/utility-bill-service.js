import axios from 'axios'

const UTILITY_BILL_API_URL = 'http://localhost:8083/communal/utilities';

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
                return response;
            })
            .catch(error => {
                console.error("Error! Utility bill was not created! \n", error)
                return error;
            })
    }
}

export default new UtilityBillService();