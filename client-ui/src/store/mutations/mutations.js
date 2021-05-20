export default {
    // bank
    SET_PROPERTY_TO_STATE(state, properties) {
        state.properties = properties;
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

    // property
    SET_ALL_PROPERTIES_TO_STATE(state, properties) {
        state.allProperties = properties;
    },
    ADD_PROPERTY(state, property) {
        state.properties.push(property);
    },
    SET_REGISTRATION_TO_STATE(state, registration) {
        state.registration = registration
    },
    SET_REGISTRATIONS_TO_STATE(state, registrations) {
        state.registrations = registrations;
    },

    // utilities price list
    SET_UTILITIES_PRICE_LIST_TO_STATE(state, utilitiesPriceLists) {
        state.utilityBillPriceLists = utilitiesPriceLists;
    },
    ADD_UTILITIES_PRICE_LIST(state, priceListFromApi) {
        state.utilityBillPriceLists.push(priceListFromApi);
    },
    UPDATE_UTILITIES_PRICE_LIST(state, priceListFromApi, utilitiesPriceListId) {
        state.utilityBillPriceLists.splice(utilitiesPriceListId, 1, priceListFromApi);
    },

    // property tax value
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

    // property tax
    SET_CITIZEN_PROPERTY_TAX_TO_STATE(state, propertyTaxes) {
        state.propertyTaxes = propertyTaxes;
    },
    CHANGE_PROPERTY_TAX_PAYMENT_STATUS(state, propertyTaxId) {
        const id = state
            .propertyTaxes
            .findIndex(
                item => item.propertyTaxId === propertyTaxId
            );
        state.propertyTaxes[id].isPaid = true;
    },

    // utility bills
    SET_UTILITY_BILLS_TO_STATE(state, utilityBills) {
      state.utilityBills = utilityBills;
    },
    UPDATE_UTILITY_BILL_PAY_STATUS(state, utilityBillId) {
        const id = state
            .utilityBills
            .findIndex(
                item => item.utilityBillId === utilityBillId
            );
        state.utilityBills[id].isPaid = true;
    },

    // passport
    SET_DOMESTIC_TO_STATE(state, domestic) {
        state.domestic = domestic;
    },
    UPDATE_DOMESTIC(state, updateDomestic) {
        state.domestic = updateDomestic;
    },
    SET_INTERNATIONAL_PASSPORTS_TO_STATE(state, internationals) {
        state.internationals = internationals;
    },
    SET_INTERNATIONAL_TO_STATE(state, international) {
        state.internationals.push(international);
    },
    UPDATE_INTERNATIONAL(state, updateInternational) {
        state.international = updateInternational;
    },
}