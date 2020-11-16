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
var frontendUrl = 'https://' + config.build.host + ':' + config.build.port
var backendUrl = 'https://' + config.build.backendHost + ':' + config.build.backendPort
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
    
    var url = window.location.href.split('/')
    const category = url[url.length - 1]
    AXIOS.get('/artwork/onPremise')
    .then(response => {
      
      this.categoryArtworks = response.data
    })
  }
}
</script>