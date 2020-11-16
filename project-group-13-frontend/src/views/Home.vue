<template>
  <div>
    <Navbar />
    <ArtObjectDisplay displayHeading="Featured Artwork" :artworks="this.featuredArtworks" urlForPath='artwork'/>
    <!-- <ArtObjectDisplay displayHeading="Featured Artists"/> -->
    <CategoryDisplay displayHeading="Browse by Category"/>
    <br>
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
var frontendUrl = 'http://' + config.build.host + ':' + config.build.port
var backendUrl = 'http://' + config.build.backendHost + ':' + config.build.backendPort
var AXIOS = axios.create({baseURL: backendUrl, headers: { 'Access-Control-Allow-Origin': frontendUrl }})

export default {
  name: 'Home',
  components: { Navbar, ArtObjectDisplay, CategoryDisplay},
  data() {
    return {
      featuredArtworks: [],
      featuredArtists: ''
    }
  },
  created: function() {
    var url = window.location.href.split('/')
    AXIOS.get('/artwork/onPremise/available')
    .then(response => {
      console.log('ResponseData' + JSON.stringify(response.data[0]))
      this.featuredArtworks = response.data.slice(0, 8)
    })
    .catch(error => {
        console.log(error)
      })
  }
}
</script>
