<template>
  
    <div class="bg-white shadow-sm rounded pb-2 mx-1 d-flex flex-column overflow-hidden">
      <span class="border" w-100><b-row w-100>
        <b-col>
          <div class="d-block position-relative h-48 w-48 overflow-hidden clickable" v-on:click = "artworkClicked">
            <img
              :src="artwork.imageUrl"
              class="object-cover object-center w-100 h-100 d-block bg-secondary"
            />
          </div>
        </b-col>
        <b-col>
          <div class="px-3 flex-1">
            <h2 class="text-secondary font-medium mt-3 mb-0 clickable" v-on:click = "artworkClicked">{{ artwork.title }}</h2>
            <p class="mb-3 text-lg font-bold">{{ artistNameList }}</p>
          </div>
        </b-col>
        <b-col>
        <div class="px-3">
          <h3 class="mb-3 text-lg font-bold mt-3 mb-0">$ {{ artwork.worth.toFixed(2) }}</h3>
          <b-button v-on:click = "removeFromCart" class="text-light self-align-center">Remove from cart</b-button>
        </div>
        </b-col>
      </b-row></span>
    </div>
</template>

<script>

import axios from 'axios'
import Router from '../router'
var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({baseURL: backendUrl, headers: { 'Access-Control-Allow-Origin': frontendUrl }})


export default {
  name: 'CartObject',
  props: {
    urlForPath: {
      type: String,
      required: true
    },
    artwork: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      artistNameList: "By: "
    }
  },
  created() {
    var i
    for (i = 0; i < this.artwork.artist.length - 1; i++) {
      this.artistNameList = this.artistNameList + this.artwork.artist[i].username + ", "
    }
    this.artistNameList = this.artistNameList + this.artwork.artist[i].username
  },
  methods: {
    removeFromCart: function () {
      
      ///user/{username}/edit-/cart
      AXIOS.put('/user/' + document.cookie.substring(6) + '/edit-/cart' + '?artid=' + this.artwork.artworkID)
        .then((response) => {
          this.$emit('removedFromCart')
        })
    },
    artworkClicked() {
      console.log("clicked")
      Router.push({path: '/artwork/' + this.artwork.artworkID})
    }
  }
}
</script>

<style scoped>
  .clickable {
    cursor: pointer;
  }
  .flex-1 {
    flex: 1 1 0%;
  }
  img {
  max-width: 300px;
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