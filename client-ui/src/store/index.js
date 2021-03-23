import Vue from 'vue'
import Vuex from 'vuex'
// import propertyModule from "@/store/communal/property/property-module";
import mutations from './mutations/mutations'
import actions from './actions/actions'
import getters from './getters/getters'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        properties: [],
        propertyTaxValue: [],
        utilityBill: [],
        utilityBillPriceList: []
    },
    mutations,
    actions,
    getters,
    // modules: {
    //     propertyModule
    // }
})