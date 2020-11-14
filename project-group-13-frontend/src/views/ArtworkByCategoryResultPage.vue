<template>
  <div>
    <Navbar />
    <ArtObjectDisplay displayHeading="Similar Artwork" :artwork="this.categoryArtworks"/>
    
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
  name: 'Category Artwork Display',
  components: { Navbar, ArtObjectDisplay},
  data() {
    return {
      categoryArtworks: [],
      
    }
  },
  created: function() {
    var url = window.location.href.split('/')
    const category = url[url.length - 1]
    AXIOS.get('/artwork/' + category)
    .then(response => {
      console.log('ResponseData' + response.data)
      this.categoryArtworks = response.data.slice(0, 8)
    })
  }
}
</script>