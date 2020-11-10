// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import App from './App'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'bootstrap'

import IconBase from './assets/icons/IconBase.vue'
Vue.component('icon-base', IconBase)

import IconProfile from './assets/icons/IconProfile.vue'
Vue.component('icon-profile', IconProfile)

import IconShoppingCart from './assets/icons/IconShoppingCart.vue'
Vue.component('icon-shopping-cart', IconShoppingCart)

Vue.use(BootstrapVue)
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
