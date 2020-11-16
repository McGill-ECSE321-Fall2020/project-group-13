<template>
  <div class="orders-page">
    <Navbar /> 
    <b-container class="bv-example-row">
      <b-row>
        <br><br>
      </b-row>
      <b-row>
        <h2 v-if="orders.length !== 0">&emsp;Orders</h2>
        <h2 v-if="orders.length === 0">&emsp;You have no past orders</h2>
      </b-row>
      
      <b-row>
        <b-col cols="8">
          <div
            v-for="order in orders"
            :key="order.orderID"
            class="mx-0 mt-0" 
          >
            <div class="bg-white shadow-sm rounded pb-2 mx-1 d-flex flex-column overflow-hidden">
                <span class="border" w-100><b-row w-100>
                    <b-col>
                    <div class="d-block position-relative h-48 w-48 overflow-hidden">
                        <h3 style="margin-top: 0.4em;">Order ID: {{order.orderID}}</h3>
                    </div>
                    </b-col>
                    <b-col>
                    <div class="px-3 flex-1">
                        <h3 class="text-secondary font-medium" style="margin-top: 0.4em;">Order Total: ${{ order.totalAmount }}</h3>
                    </div>
                    </b-col>
                    <b-col>
                      <h4 class="text-secondary font-medium" style="margin-top: 0.4em;">{{ order.isDelivery }}</h4>
                    </b-col>
                    <b-col>
                        <div class="px-3">
                            <b-button v-on:click="itemsPage(order.orderID)" class="text-light self-align-center" style="margin-top: 1em;">View order items</b-button>
                        </div>
                    </b-col>
                </b-row></span>
            </div>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import axios from 'axios'
import Router from '../router'
var config = require('../../config')
var frontendUrl = 'https://' + config.build.host + ':' + config.build.port
var backendUrl = 'https://' + config.build.backendHost + ':' + config.build.backendPort
var AXIOS = axios.create({baseURL: backendUrl, headers: { 'Access-Control-Allow-Origin': frontendUrl }})
function getUsernameCookie(){
  if(document.cookie.length<6) return null
  else return document.cookie.substr(6).split(" ")[0]
}
import Navbar from '../components/Navbar'
export default {
    name: 'OrdersPage',
    components: { Navbar },
    data () {
        return {
        orders: Array,
        artworks: Array
        }
    },
    created: function () {
        // getting username from cookie
        const username = getUsernameCookie()
        // Fetching orders from backend
        AXIOS.get('/user/' + username + '/orders')
        .then(response => {
        // JSON responses are automatically parsed.
        this.orders = response.data
        })
    },
    methods: {
        // method to reroute to order items page
        itemsPage: function (id) {
            Router.push({path: '/order/' + id + '/items'})
        }
    }

}
</script>

<style>
</style>