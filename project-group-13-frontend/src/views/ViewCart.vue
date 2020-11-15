<template>
  <div>
    <Navbar />
    <b-container class="bv-example-row">
      <b-row>
        <br><br>
      </b-row>
      <b-row>
        <h2 v-if="artworks.length !== 0">&emsp;Cart</h2>
        <h2 v-if="artworks.length === 0">&emsp;Your cart is empty :(</h2>
      </b-row>
      
      <b-row>
        <b-col cols="8">
          <div
            v-for="artwork in artworks"
            :key="artwork.id"
            class="mx-0 mt-0" 
          >
            <CartObject :artwork="artwork" :urlForPath="'artwork'" @removedFromCart="refreshCart"/>
          </div>
        </b-col>
        <b-col>
          <div class="sticky-top" v-if="artworks.length !== 0">
            <br><br v-if="artworks.length > 1"><br v-if="artworks.length > 1"><br v-if="artworks.length > 1">
            <span class="border"><b-container>
              <b-row align-h="center">
                <h2>Subtotal:</h2>
              </b-row>
              <b-row align-h="center">
                <h3 class="mb-3 text-lg font-bold">$ {{ cart.totalCost.toFixed(2)}}</h3>
              </b-row>
              <b-row align-h="center">
                <button  v-on:click = "gotoCheckout" type="submit" class="btn btn-primary">Proceed to checkout</button>
              </b-row>
            </b-container></span>
          </div>
        </b-col>
      </b-row>
    </b-container>
    </div>
</template>

<script>
/* eslint-disable */

import Navbar from '../components/Navbar'
import CartObject from '../components/CartObject'
import CategoryDisplay from '../components/CategoryDisplay'
import axios from 'axios'
import Router from '../router'

var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({baseURL: backendUrl, headers: { 'Access-Control-Allow-Origin': frontendUrl }})

export default {
  name: 'ViewCart',
  components: { Navbar, CartObject, CategoryDisplay},
  data() {
    return {
      user: '',
      cart: JSON,
      artworks: Array,
      refreshFlag: 0
    }
  },

  beforeCreate: function() {
    if (document.cookie.length <= 6) {
      Router.push({path: '/login?returnTo=' + window.location.href})
    }
    this.user = document.cookie.substr(6)
    AXIOS.get('/user/' + this.user + '/cart')
    .then(response => {
      this.cart = response.data
      this.artworks = this.cart.artwork
    })
  },
  methods: {
    gotoCheckout: function () {
      Router.push({path: '/checkout' })
    },
    refreshCart: function () {
      this.user = document.cookie.substr(6)
      AXIOS.get('/user/' + this.user + '/cart')
      .then(response => {
      this.cart = response.data
      this.artworks = this.cart.artwork
    })
    }
  }
}
</script>

<style scoped>
b-col {
  background: rgb(126, 143, 143);
}
span {
  padding: 10px;
  display: inline-block;
}
</style>