import axios from 'axios'

const PROPERTY_TAX_API_URL = 'http://localhost:8083/communal/property-tax';

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