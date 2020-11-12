import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ArtworkDetails from '@/components/ArtworkDetails'
import Login from '@/views/login'
import CreateAccount from '@/views/CreateAccount'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/artwork',
      name: 'ArtworkDetails',
      component: ArtworkDetails
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/newAccount',
      name: 'newAccount',
      component: CreateAccount
    }
  ]
})
