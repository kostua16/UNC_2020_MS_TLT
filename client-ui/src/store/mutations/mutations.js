export default {
    SET_PROPERTY_TO_STATE(state, properties) {
        state.properties = properties;
    },
    SET_ALL_PROPERTIES_TO_STATE(state, properties) {
        state.allProperties = properties;
    },
    SET_PAYMENT_REQUESTS_TO_STATE(state, paymentRequests) {
        state.paymentRequests = paymentRequests;
    },

    SET_UTILITIES_PRICE_LIST_TO_STATE(state, utilitiesPriceLists) {
        state.utilityBillPriceLists = utilitiesPriceLists;
    },
    ADD_UTILITIES_PRICE_LIST(state, priceListFromApi) {
        state.utilityBillPriceLists.push(priceListFromApi);
    },
    UPDATE_UTILITIES_PRICE_LIST(state, priceListFromApi, utilitiesPriceListId) {
        state.utilityBillPriceLists.splice(utilitiesPriceListId, 1, priceListFromApi);
    },

    SET_PROPERTY_TAX_VALUE_TO_STATE(state, propertyTaxValues) {
        state.propertyTaxValues = propertyTaxValues;
    },
    ADD_PROPERTY_TAX_VALUE(state, priceListFromApi) {
        state.propertyTaxValues.push(priceListFromApi);
    },
    UPDATE_PROPERTY_TAX_VALUE(state, priceListFromApi, propertyTaxValueId) {
        state.propertyTaxValues.splice(propertyTaxValueId, 1, priceListFromApi);
    }
}