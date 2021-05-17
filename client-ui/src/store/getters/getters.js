export default {
    // properties
    GET_PROPERTIES(state) {
        return state.properties;
    },
    GET_ALL_PROPERTIES(state) {
        return state.allProperties;
    },
    GET_REGISTRATION(state) {
        return state.registration;
    },
    GET_REGISTRATIONS(state) {
        return state.registrations;
    },

    // price lists
    SORTED_UTILITIES_PRICE_LIST(state) {
        // return state.utilityBillPriceLists.sort((a,b) => (a.region[0] - b.region[0]));
        // const utilityBillPriceLists = state.utilityBillPriceLists.region.sort();
        // utilityBillPriceLists.region.reverse();
        return state.utilityBillPriceLists;
    },

    SORTED_PROPERTY_TAX_VALUE(state) {
        return state.propertyTaxValues;
    },

    GET_CITIZEN_PROPERTY_TAXES(state) {
        return state.propertyTaxes;
    },

    // utility bills
    GET_UTILITY_BILLS(state) {
        return state.utilityBills.sort((a, b) => {
            return new Date(a.date) - new Date(b.date);
        });
    },

    // auth data
    GET_USER_CITIZEN_ID(state) {
        return state.auth.user.citizenId
    },
    GET_USER_ROLE(state) {
        return state.auth.user.role
    },
    IS_ADMIN_ROLE(state) {
        return state.auth.user.role === 'ROLE_ADMIN'
    },
    GET_USER_IS_ACTIVE(state) {
        return state.auth.status.loggedIn
    },

    //bank
    GET_MY_REQUEST_PAYMENTS(state) {
        return state.paymentRequests;
    },
    GET_TRANSACTIONS(state) {
        return state.transactions;
    },

    // passport
    GET_DOMESTIC(state) {
        return state.domestic;
    },

    GET_INTERNATIONAL_PASSPORTS(state) {
        return state.internationals;
    },
}