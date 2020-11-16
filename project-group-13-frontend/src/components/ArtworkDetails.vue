<template>
    <div id="artworkdetails">
        <b-container class="artwork info shadow-lg p-4">
          <b-row no-gutters align-v="stretch">
            <b-col cols="5">
              <img :src="artwork.imageUrl" alt="No Images for Artwork" class="art-image rounded">
              <h4 style="margin-top: 2em;">Artists</h4>
              <div class="mb-4">
                <router-link v-for="(artistn,i) in artwork.artist" v-bind:key="`artist-${i}`" :to="`/viewuser/`+artistn.username">
                  <b-avatar :text="artistn.username.charAt(0)" :id="`popover-1-${artistn.username}`"></b-avatar>
                  <b-popover
                    :target="`popover-1-${artistn.username}`"
                    placement="bottom"
                    triggers="hover focus"
                    :content="`${artistn.username}`"
                  ></b-popover>
        </router-link>
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
                  <b-col>{{ !(artwork.artworkSold) }}</b-col>
                </b-row>
                <b-row>
                  <b-col><p>On premise</p></b-col>
                  <b-col>{{ artwork.isOnPremise }}</b-col>
                </b-row>
              </b-container>
            </b-col>
            <b-col cols="2" style="margin-top: 2em;">
              <router-link v-if="this.canEdit" tag="button" class="myClass" id="button" :to="this.editPath">Edit Artwork</router-link>
              
              <b-button v-on:click = "editCartEvent" :class="buttonDisable" variant="outline-primary">{{ buttonLabel }}</b-button>
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

function getUsernameCookie(){
  if(document.cookie.length<6) return null
  else return document.cookie.substr(6).split(" ")[0]
}
export default {
  name: 'ArtworkDetails',
  data () {
    return {
      artwork: '',
      buttonDisable: '',
      buttonLabel: 'Add to Cart',
      editPath: '',
      artists:[],
      canEdit: false
    }
  },
  created: function () {
    //from URL get ID
    var url = window.location.href.split('/')
    const id = url[url.length - 1] // artwork id
  
    this.editPath='/editartwork/'+id
    // Fetching artwork from backend
    AXIOS.get('/artwork/byId/' + id)
    .then(response => {
      // JSON responses are automatically parsed.
      this.artwork = response.data
      var artistUsernames=this.artwork.artist.map(a=>a.username)
      this.artists = artistUsernames
      if(this.artists.includes(document.cookie.substr(6).split(' ')[0])){
        this.canEdit=true
      }
      this.artwork.imageURL = decodeURIComponent(this.artwork.imageURL)
      console.log(this.artwork.imageURL)

      // check if user is logged in
      const username = getUsernameCookie()
      if (username.length !== 0){
        // getting cart of user
        AXIOS.get('/user/' + username + '/cart')
        .then(response => {
        // JSON responses are automatically parsed.
          const cart = response.data
          var i
          if(cart.artwork){
            for (i = 0; i < cart.artwork.length; i++) {
            if (cart.artwork[i].artworkID === parseInt(id)) {
              console.log('equal')
              // this.buttonDisable = 'disabled'
              this.buttonLabel = 'Remove from Cart'
            } // otherwise button can be clicked
          }
          }
          // checking if artwork was already in cart
          
        })
      }
    })
  },
  methods: {
    editCartEvent: function () {
      if (this.buttonLabel === 'Remove from Cart') {
        this.removeFromCart()
      } else {
        this.addToCart()
      }
    },
    addToCart: function () {
      if (getUsernameCookie()==null) {
        Router.push({path: '/login?returnTo=' + window.location.href})
      } else {
        var url = window.location.href.split('/')
        const id = url[url.length - 1] // artwork id
        AXIOS.put('/user/' + getUsernameCookie() + '/edit+/cart' + '?artid=' + id)
        .then((response) => {
          // this.buttonDisable = 'disabled'
          this.buttonLabel = 'Remove from Cart'
        })
      }
    },
    removeFromCart: function () {
      if (document.cookie.length < 6) {
        Router.push({path: '/login?returnTo=' + window.location.href})
      } else {
        var url = window.location.href.split('/')
        const id = url[url.length - 1] // artwork id
        AXIOS.put('/user/' + getUsernameCookie() + '/edit-/cart' + '?artid=' + id)
        .then((response) => {
          // this.buttonDisable = 'disabled'
          this.buttonLabel = 'Add to Cart'
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