export default {
    GET_PROPERTIES(state) {
        return state.properties;
    },
    GET_ALL_PROPERTIES(state) {
        return state.allProperties;
    },
    GET_MY_REQUEST_PAYMENTS(state) {
        return state.paymentRequests;
    },
    SORTED_UTILITIES_PRICE_LIST(state) {
        // return state.utilityBillPriceLists.sort((a,b) => (a.region[0] - b.region[0]));
        // const utilityBillPriceLists = state.utilityBillPriceLists.region.sort();
        // utilityBillPriceLists.region.reverse();
        return state.utilityBillPriceLists;
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
}