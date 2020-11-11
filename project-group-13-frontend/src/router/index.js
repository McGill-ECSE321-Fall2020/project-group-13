import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ArtworkDetails from '@/components/ArtworkDetails'

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
    }
  ]
})
