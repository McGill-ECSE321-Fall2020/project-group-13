<template>
  <div class="bg-white shadow-sm rounded pb-2 mx-1 d-flex flex-column overflow-hidden zoom" v-on:click = "goToPage">
    <div class="d-block position-relative h-48 overflow-hidden">
      <img
        :src="artwork.imageUrl"
        class="object-cover object-center w-100 h-100 d-block bg-secondary"
      />
    </div>
    <div class="bottom-of-flex">
      <div class="px-3 flex-1">
        <h2 class="text-secondary font-medium mt-3 mb-0">{{ artwork.title }}</h2>
      </div>
    </div>
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
  name: 'Art Object',
  props: {
    artwork: {
      type: Object,
      required: true
    }
  },
  methods: {
  goToPage: function () {
    console.log(this.artwork.artworkID)
    console.log('Go to page clicked...')

    AXIOS.get('/artwork/byCategory/?category=' + this.artwork.artworkID)
      .then((response) => {
        console.log('here is all artworks of category')
        console.log(response.data)
        Router.push({ name: 'CategoryDisplay', params: {artwork: response}})
      })
  }
  }
}
</script>
<style scoped>
  .bottom-of-flex {
    margin-top: auto;
  }
  .zoom {
    transition: transform .2s; /* Animation */
    margin: 0 auto;
    z-index:1;
  }
  .zoom:hover {
    transform: scale(1.1); /* (150% zoom - Note: if the zoom is too large, it will go outside of the viewport) */
    z-index:1000;
  }
  img {
    max-width: 300px;
    height: auto;
  }
  .object-cover {
    object-fit: cover;
  }

  .object-center {
    object-position: center;
  }	

  .flex-1 {
    flex: 1 1 0%;
  }
</style>