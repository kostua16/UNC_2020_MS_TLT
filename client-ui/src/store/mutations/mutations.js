export default {
    SET_PROPERTY_TO_STATE(state, properties) {
        state.properties = properties;
    },
    SET_ALL_PROPERTIES_TO_STATE(state, properties) {
        state.allProperties = properties;
    },
    SET_PAYMENT_REQUESTS_TO_STATE(state, paymentRequests) {
        state.paymentRequests = paymentRequests;
    }
}