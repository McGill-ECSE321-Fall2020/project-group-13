<template>
    <div id="artworkdetails">
        <b-container class="artwork info shadow-lg p-4">
          <b-row no-gutters align-v="stretch">
            <b-col cols="5">
              <img :src="artwork.imageUrl" alt="No Images for Artwork" class="art-image rounded">
              <h4 style="margin-top: 2em;">Artists</h4>
              <div class="mb-4">
                <router-link v-for="(artistn,i) in artwork.artist" v-bind:key="`artist-${i}`" :to="`/user/`+artistn.username"><b-avatar :text="artistn.username.charAt(0)"></b-avatar></router-link>
              </div>
            </b-col>
            <b-col cols="5">
              <h1 class="title">{{ artwork.title }}</h1>
              <h3 class="price"><span>$</span>{{ artwork.worth }}</h3>
              <p style="margin-top: 3.5em;"><u>Description</u></p>
              <p class="description">{{ artwork.description }}</p>

              <b-container style="margin-top: 5em;" class="shadow-sm">
                <b-row>
                  <b-col><p>Creation Date</p></b-col>
                  <b-col>{{ artwork.creationDate }}</b-col>
                </b-row>
                <b-row>
                  <b-col><p>Dimensions</p></b-col>
                  <b-col>{{ artwork.dimensions }}</b-col>
                </b-row>
                <b-row>
                  <b-col><p>Medium</p></b-col>
                  <b-col>{{ artwork.medium }}</b-col>
                </b-row>
                <b-row>
                  <b-col><p>Collection</p></b-col>
                  <b-col>{{ artwork.collection }}</b-col>
                </b-row>
                <b-row>
                  <b-col><p>Available</p></b-col>
                  <b-col>{{ artwork.artworkSold }}</b-col>
                </b-row>
                <b-row>
                  <b-col><p>On premise</p></b-col>
                  <b-col>{{ artwork.isOnPremise }}</b-col>
                </b-row>
              </b-container>
            </b-col>
            <b-col cols="2" style="margin-top: 2em;">
              <b-button v-on:click = "addToCart" :class="buttonDisable" variant="outline-primary">{{ buttonLabel }}</b-button>
            </b-col>
          </b-row>
        </b-container>
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
  name: 'ArtworkDetails',
  data () {
    return {
      artwork: '',
      buttonDisable: '',
      buttonLabel: 'Add to Cart'
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
      this.artwork.imageURL = decodeURI(this.artwork.imageURL)
      // getting cart of user
      const username = document.cookie.substring(6)
      AXIOS.get('/user/' + username + '/cart')
      .then(response => {
      // JSON responses are automatically parsed.
        const cart = response.data
        var i
        // checking if artwork was already in cart
        for (i = 0; i < cart.artwork.length; i++) {
          if (cart.artwork[i].artworkID === parseInt(id)) {
            console.log('equal')
            this.buttonDisable = 'disabled'
            this.buttonLabel = 'In Cart'
          } // otherwise button can be clicked
        }
      })
    })
  },
  methods: {
    addToCart: function () {
      if (document.cookie.length < 6) {
        Router.push({path: '/login?returnTo=' + window.location.href})
      } else {
        var url = window.location.href.split('/')
        const id = url[url.length - 1] // artwork id
        AXIOS.put('/user/' + document.cookie.substring(6) + '/edit+/cart' + '?artid=' + id)
        .then((response) => {
          this.buttonDisable = 'disabled'
          this.buttonLabel = 'In Cart'
        })
      }
    }
  }
}
</script>

<style>
  .art-image {
    width: 100%;
  }
</style>