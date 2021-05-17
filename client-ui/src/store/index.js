import Vue from 'vue'
import Vuex from 'vuex'
import mutations from './mutations/mutations'
import actions from './actions/actions'
import getters from './getters/getters'
import auth from './auth.module'
import Domestic from "@/models/passport/domestic";
import Registration from "@/models/communal/registration";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        properties: [],
        allProperties: [],
        propertyTaxValues: [],
        utilityBills: [],
        utilityBillPriceLists: [],
        paymentRequests: [],
        transactions: [],
        propertyTaxes: [],
        domestic: new Domestic(),
        internationals: [],
        registration: new Registration(),
        registrations: '',
    },
    mutations,
    actions,
    getters,
    modules: {
        auth
    }
})