import Vue from 'vue'
import Vuetify from 'vuetify'
import App from './App.vue'
import {router} from './router/router'
import store from './store'
import Vuex from 'vuex'
import 'vuetify/dist/vuetify.min.css'

Vue.config.productionTip = false

Vue.use(Vuex)
Vue.use(Vuetify)

new Vue({
  store,
  router,
  render: h => h(App),
  vuetify: new Vuetify({})
}).$mount('#app')
