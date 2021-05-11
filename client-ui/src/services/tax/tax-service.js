import axios from 'axios'
import AuthModule from "@/store/auth.module";

const HTTP_PROTOCOL = process.env.VUE_APP_PROTOCOL || 'http';
const TAX_URL = process.env.VUE_APP_TAX_URL || 'localhost:8082';
const API_URL = HTTP_PROTOCOL + '://' + TAX_URL + '/tax';

class TaxService {
    getMyTax() {
        return axios.post(API_URL + '/my-debt/', {citizenId: AuthModule.state.user.citizenId});
    }

    getTaxStatus() { // переделать
        return axios.get(API_URL + '/2')
    }

    getALlTaxes() {
        return axios.get(API_URL)
            .then(response => {
                return response
            })
            .catch(error => {
                console.error('Не удалосб получить налоги!\n', error.response.status)
                return error
            });
    }
}

export default new TaxService();