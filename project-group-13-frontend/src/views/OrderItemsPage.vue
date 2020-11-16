<template>
    <div>
        <Navbar /> 
        <h2><strong>Items in order:</strong></h2>
        <b-row align-h="center">
            <b-col cols="5">
                <div
                    v-for="artwork in artworks"
                    :key="artwork.id"
                    class="mx-0 mt-0" 
                >
                    <div class="bg-white shadow-sm rounded pb-2 mx-1 d-flex flex-column overflow-hidden">
                        <span class="border" w-100><b-row w-100>
                            <b-col>
                            <div class="d-block position-relative h-48 w-48 overflow-hidden clickable" v-on:click="artworkClicked(artwork)">
                                <img
                                :src="artwork.imageUrl"
                                class="object-cover object-center w-100 h-100 d-block bg-secondary"
                                />
                            </div>
                            </b-col>
                            <b-col>
                                <div class="px-3 flex-1">
                                    <h2 class="text-secondary font-medium mt-3 mb-0 clickable" v-on:click="artworkClicked(artwork)">{{ artwork.title }}</h2>
                                </div>
                            </b-col>
                            <b-col>
                            <div class="px-3">
                                <h3 class="mb-3 text-lg font-bold mt-3 mb-0">$ {{ artwork.worth.toFixed(2) }}</h3>
                            </div>
                            </b-col>
                        </b-row></span>
                    </div>
                </div>
            </b-col>
        </b-row>
    </div>
</template>


<script>
import axios from 'axios'
import Router from '../router'
var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({baseURL: backendUrl, headers: { 'Access-Control-Allow-Origin': frontendUrl }})
function getUsernameCookie(){
  if(document.cookie.length<6) return null
  else return document.cookie.substr(6).split(" ")[0]
}
import Navbar from '../components/Navbar'
export default {
    name: 'OrderItemsPage',
    components: { Navbar },
    data () {
        return {
            artworks: Array,
        }
    },
    created: function () {
        var url = window.location.href.split('/')
        const id = url[url.length - 2] // order id
        //getting order items from backend
        AXIOS.get('/order/' + id + '/items')
        .then(response => {
        // JSON responses are automatically parsed.
        this.artworks = response.data
        }).catch(error => {
        console.log(error)
        })
    },
    methods: {
        artworkClicked(artwork) {
            console.log("clicked")
            Router.push({path: '/artwork/' + artwork.artworkID})
        }
    }
}
</script>

<style>
.clickable {
    cursor: pointer;
  }
  .flex-1 {
    flex: 1 1 0%;
  }
  img {
  max-width: 120px;
  height: auto;
  }
  span {
  padding: 10px;
  }
  b-row {
    padding-left: 10px;
  }
  button {
    display: fit-content;
  }
</style>