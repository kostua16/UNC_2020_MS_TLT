import axios from 'axios'

const API_URL = 'http://localhost:8082/tax'

class TaxService {
    getMyTax() {
        return axios.post(API_URL + '/my-debt/', {citizenId: 111});
    }
    getTaxStatus() {
        return axios.get(API_URL + '/2')
    }

    getALlTaxes() {
        return axios.get(API_URL)
            .then(response => {
                console.log(response.data)
                response.data
            });
    }

}

export default new TaxService();