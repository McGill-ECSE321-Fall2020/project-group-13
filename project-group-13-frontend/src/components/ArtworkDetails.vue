<template>
    <div id="artworkdetails">
        <b-container class="artwork info shadow-lg p-4">
          <b-row no-gutters align-v="stretch">
            <b-col cols="6">
              <img src="./artImage.jpg" alt="Artwork image" class="art-image">
              <h4 style="margin-top: 2em;">Artists</h4>
              <div class="mb-4">
                <router-link v-for="(artistn,i) in artwork.artist" v-bind:key="`artist-${i}`" :to="`/user/`+artistn.username"><b-avatar text=""></b-avatar></router-link>
              </div>
            </b-col>
            <b-col cols="6">
              <h1 class="title">{{ artwork.title }}</h1>
              <h3 class="price"><span>$</span>{{ artwork.worth }}</h3>
              <p style="margin-top: 3.5em;"><u>Description</u></p>
              <p class="description">{{ artwork.description }}</p>

              <b-container style="margin-top: 5em;" class="shadow-sm">
                <b-row>
                  <b-col><p>Creation Date</p></b-col>
                  <b-col>{{ artwork.creation_date }}</b-col>
                </b-row>
                <b-row>
                  <b-col><p>Dimensions</p></b-col>
                  <b-col>{{ artwork.dimensions }}</b-col>
                </b-row>
                <b-row>
                  <b-col><p>Medium</p></b-col>
                  <b-col></b-col>
                </b-row>
                <b-row>
                  <b-col><p>Details</p></b-col>
                  <b-col></b-col>
                </b-row>
                <b-row>
                  <b-col><p>Available</p></b-col>
                  <b-col></b-col>
                </b-row>
                <b-row>
                  <b-col><p>On premise</p></b-col>
                  <b-col></b-col>
                </b-row>
              </b-container>
            </b-col>
          </b-row>
        </b-container>
    </div>
</template>

<script>
import axios from 'axios'
var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({baseURL: backendUrl, headers: { 'Access-Control-Allow-Origin': frontendUrl }})

export default {
  name: 'Artwork Details',
  data () {
    return {
      artwork: ''
    }
  },
  created: function () {
    var url = window.location.href.split('/')
    const id = url[url.length - 1] // artwork id
    // Fetching artwork from backend
    AXIOS.get('/artwork/' + id)
    .then(response => {
      // JSON responses are automatically parsed.
      this.artwork = response.data
    })
  }
}
</script>

<style>
  .art-image {
    width: 100%;
  }
</style>