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
    }
}

export default new UtilityBillService();