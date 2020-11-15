<template>
  <div class="bg-white shadow-sm rounded pb-2 mx-1 d-flex flex-column overflow-hidden">
    <div class="d-block position-relative h-48 overflow-hidden">
      <img
        :src="artwork.imageUrl"
        class="object-cover object-center w-100 h-100 d-block bg-secondary"
      />
    </div>
    <div class="px-3 flex-1">
      <h2 class="text-secondary font-medium mt-3 mb-0">{{ artwork.title }}</h2>
      <p class="mb-3 text-lg font-bold">$ {{ artwork.worth }}</p>
    </div>
    <div class="px-3">
       <button v-on:click = "goToPage" type="submit" class="btn btn-primary">See More</button>
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
<style>
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