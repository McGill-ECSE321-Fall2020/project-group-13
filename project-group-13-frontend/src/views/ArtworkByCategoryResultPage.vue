<template>
  <div>
    <Navbar />
    <ArtObjectDisplay displayHeading="Similar Artwork" :artworks="this.artwork.data" urlForPath="artwork"/>
    
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
  name: 'CategoryArtworkDisplay',
  components: { Navbar, ArtObjectDisplay, CategoryDisplay},
   props: {
    artwork: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      categoryArtworks: [],
      
    }
  },
  created: function() {
    console.log('here is the artwork data from within RESULTSPAGE !!!')
    console.log(this.artwork.data)
    var url = window.location.href.split('/')
    const category = url[url.length - 1]
    AXIOS.get('/artwork/onPremise')
    .then(response => {
      console.log('ResponseData' + response.data)
      this.categoryArtworks = response.data
    })
  }
}
</script>