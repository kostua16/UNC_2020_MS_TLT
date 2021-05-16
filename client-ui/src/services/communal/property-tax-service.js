import axios from 'axios'

const HTTP_PROTOCOL = process.env.VUE_APP_PROTOCOL || 'http';
const COMMUNAL_URL = process.env.VUE_APP_API_URL || 'localhost:8880';
const PROPERTY_TAX_API_URL = HTTP_PROTOCOL + '://' + COMMUNAL_URL + '/communal/property-tax';

class PropertyTaxService {
    createPropertyTax(propertyId) {
        return axios
            .post(
                PROPERTY_TAX_API_URL + '/',
                {propertyId: propertyId},
                {}
            )
            .then(response => {
                return response;
            })
            .catch(error => {
                console.error("Error! Property tax was not created! \n", error)
                return error;
            })
    }
}

export default new PropertyTaxService();