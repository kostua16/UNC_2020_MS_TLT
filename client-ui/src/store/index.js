import Vue from 'vue'
import Vuex from 'vuex'
// import propertyModule from "@/store/communal/property/property-module";
import mutations from './mutations/mutations'
import actions from './actions/actions'
import getters from './getters/getters'
import auth from './auth.module'
import Domestic from "@/models/passport/domestic";
import International from "@/models/passport/international";

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
        domestic: new Domestic(),
        international: new International(),
    },
    mutations,
    actions,
    getters,
    modules: {
        auth
    }
})