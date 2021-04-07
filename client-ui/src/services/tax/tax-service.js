import axios from 'axios'
import ParseLocalStorage from '../auth/parse-local-storage'

const API_URL = 'http://localhost:8082/tax'

class TaxService {
    getMyTax() {
        return axios.post(API_URL + '/my-debt/', {citizenId: ParseLocalStorage()});
    }
    getTaxStatus() { // переделать
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