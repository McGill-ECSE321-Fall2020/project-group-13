<template>
  <div>
    <Navbar />
    <ArtObjectDisplay displayHeading="Featured Artwork" :artworks="this.featuredArtworks"/>
    <!-- <ArtObjectDisplay displayHeading="Featured Artists"/> -->
    <CategoryDisplay displayHeading="Other Categories"/>


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
    AXIOS.get('/artwork/onPremise')
    .then(response => {
      this.featuredArtworks = response.data.slice(0, 8)
    })
  }
}
</script>
