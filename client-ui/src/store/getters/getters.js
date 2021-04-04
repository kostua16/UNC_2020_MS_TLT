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