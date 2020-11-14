<template>
  <div>
    <Navbar />
    <b-container class="bv-example-row">
      <b-row>
        <b-col>1 of 2</b-col>
        <b-col>2 of 2</b-col>
      </b-row>
    </b-container>
    </div>
</template>

<script>
/* eslint-disable */

import Navbar from '../components/Navbar'
import ArtObjectDisplay from '../components/ArtObjectDisplay'
import CategoryDisplay from '../components/CategoryDisplay'
import axios from 'axios'
import Router from '../router'

var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({baseURL: backendUrl, headers: { 'Access-Control-Allow-Origin': frontendUrl }})

export default {
  name: 'ViewCart',
  components: { Navbar, ArtObjectDisplay, CategoryDisplay},
  data() {
    return {
      user: '',
      cart
    }
  },

  created: function() {
    // if (document.cookie.length <= 6) {
    //   Router.push({path: '/login?returnTo=' + window.location.href})
    // }
    this.user = document.cookie.substr(6)
    console.log('use: ' + this.user)
    AXIOS.get('/user/' + this.user + '/cart')
    .then(response => {
      // console.log(response.data)
      this.cart = response.data
      console.log(this.cart)
    })
  }
}
</script>

<style scoped>
b-col {
  background: rgb(126, 143, 143);
}
</style>