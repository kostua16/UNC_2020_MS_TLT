export default {
    // bank
    SET_PROPERTY_TO_STATE(state, properties) {
        state.properties = properties;
    },
    SET_ALL_PROPERTIES_TO_STATE(state, properties) {
        state.allProperties = properties;
    },
    SET_PAYMENT_REQUESTS_TO_STATE(state, paymentRequests) {
        state.paymentRequests = paymentRequests;
    },

    PAY_PAYMENT_REQUEST(state, paymentRequestId) {
        const id = state
            .paymentRequests
            .findIndex(
                item => item.paymentRequestId === paymentRequestId
            );
        state.paymentRequests.splice(id, 1)
    },

    // communal
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
    },
    SET_PERIOD_TRANSACTION_TO_STATE(state, transactions) {
        state.transactions = transactions;
    },

    // passport
    SET_DOMESTIC_TO_STATE(state, domestic) {
        state.domestic = domestic;
    },
    UPDATE_DOMESTIC(state, updateDomestic) {
        state.domestic = updateDomestic;
    },
    SET_INTERNATIONAL_TO_STATE(state, international) {
        state.international = international;
    },
    UPDATE_INTERNATIONAL(state, updateInternational) {
        state.international = updateInternational;
    },
}