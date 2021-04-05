// import axios from "axios";
//
// const API_URL = 'http://localhost:8083/communal/property/'
//
// const propertyModule = {
//     state: {
//         property: []
//     },
//     mutations: {
//         SET_PROPERTY_TO_STATE(state, properties) {
//             state.property = properties;
//         },
//     },
//     actions: {
//         GET_PROPERTIES_FROM_API({commit}, citizenId) {
//             return axios
//                 .get(
//                     API_URL + 'property/' + citizenId,
//                     {},
//                 )
//                 .then((properties) => {
//                     commit('SET_PROPERTY_TO_STATE', properties);
//                     return properties;
//                 })
//                 .catch((error) => {
//                     console.log('Failed to get information about the user\'s property. \n' + error);
//                     return error;
//                 })
//         }
//     },
//     getters: {
//         GET_PROPERTIES() {
//             return this.state.properties.sort((a, b) => (a.id - b.id));
//         }
//     }
// };
//
// export default propertyModule;