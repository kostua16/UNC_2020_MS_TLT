import axios from 'axios'
import AuthModule from "@/store/auth.module";

// const API_URL = 'https://nc-edu-2020-communal.herokuapp.com'
const API_URL = 'http://localhost:8083/communal/property/'

class PropertyService {
    addProperty(creationProperty) {
        creationProperty.citizenId = AuthModule.state.user.citizenId;
        return axios.post(API_URL, {
                region: creationProperty.region,
                city: creationProperty.city,
                street: creationProperty.street,
                house: creationProperty.house,
                apartment: creationProperty.apartment,
                apartmentSize: creationProperty.apartmentSize,
                citizenId: creationProperty.citizenId
            }, {}
        )
            .then(response => {
                    console.log('REGISTRATE PROPERTY: ', response)
                }
            );
    }
}

export default new PropertyService();
