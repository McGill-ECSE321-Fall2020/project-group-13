// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import App from './App'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'bootstrap'
import firebase from 'firebase';
import IconBase from './assets/icons/IconBase.vue'
Vue.component('icon-base', IconBase)

import IconProfile from './assets/icons/IconProfile.vue'
Vue.component('icon-profile', IconProfile)

import IconShoppingCart from './assets/icons/IconShoppingCart.vue'
Vue.component('icon-shopping-cart', IconShoppingCart)

Vue.use(BootstrapVue)
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App },
  created(){
    // Your web app's Firebase configuration
  var firebaseConfig = {
    apiKey: "AIzaSyDECzrRD68sfzAfLK2HskhPm3MK02yUKdg",
    authDomain: "shareart-40bdb.firebaseapp.com",
    databaseURL: "https://shareart-40bdb.firebaseio.com",
    projectId: "shareart-40bdb",
    storageBucket: "shareart-40bdb.appspot.com",
    messagingSenderId: "146077861724",
    appId: "1:146077861724:web:3c2c23c4c1f5375c90e399"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  }
})
