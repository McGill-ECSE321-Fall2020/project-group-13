import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ArtworkPage from '@/views/ArtworkPage'
import Login from '@/views/login'
import CreateAccount from '@/views/CreateAccount'
import UploadArtowrk from '@/views/UploadArtwork'
import CheckoutPage from '@/views/CheckoutPage'

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
      name: 'ArtworkPage',
      component: ArtworkPage
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
    },
    {
      path: '/uploadArtwork',
      name: 'UploadArtwork',
      component: UploadArtowrk
    },
    {
      path: '/checkout',
      name: 'CheckoutPage',
      component: CheckoutPage
    },
    {
      path: '/viewuser',
      name: 'PublicUserPage',
      component: PublicUserPage
    },
  ]
})
