/* eslint-disable */

import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ArtworkPage from '@/views/ArtworkPage'
import Login from '@/views/login'
import CreateAccount from '@/views/CreateAccount'
import UploadArtowrk from '@/views/UploadArtwork'
import CheckoutPage from '@/views/CheckoutPage'
import PublicUserPage from '@/views/PublicUserPage'
import ArtworkByCategoryResultPage from '@/views/ArtworkByCategoryResultPage'
import ViewCart from '@/views/ViewCart'
import EditProfile from '@/views/EditProfile'
import EditArtwork from '@/views/EditArtwork'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/artwork*',
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
      path: '/viewuser/:username',
      name: 'PublicUserPage',
      component: PublicUserPage
    },
    {
      path: '/byCategory/category*',
      name: 'CategoryDisplay',
      component: ArtworkByCategoryResultPage,
      props: true
    },
    {
      path: '/cart',
      name: 'CartPage',
      component: ViewCart,
      props: true
    },
    {
      path: '/edituser/:username',
      name: 'EditProfile',
      component: EditProfile
    },
    {
      path: '/editartwork/:artworkID',
      name: 'EditArtwork',
      component: EditArtwork
    },
  ]
})
