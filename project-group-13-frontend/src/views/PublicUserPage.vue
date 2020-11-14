<template>
  <div id='PublicUser'>
    <Navbar/>
    <img :src="user.profilePictureURL">
    <h1>{{$route.params.username}}</h1>

    <h3>Bio:<em>{{user.bio}}</em></h3>
    Email: {{user.email}}
    
    <div v-if="isLoggedInAsUser() == true">
    <router-link to='/uploadArtwork'>Add Artwork</router-link>
    </div>
    <ArtObjectDisplay displayHeading="Artworks" :artworks="user.artworks"/>
    
    </div>
</template>





<script>

import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
function userDto(username, email, bio,profilePictureURL){
    this.username = username
    this.email=email
    this.bio=bio
    this.profilePictureURL = profilePictureURL
    this.artworks = []

}
function artworkDto(name){
    this.name = name
}

import Navbar from '../components/Navbar'
import ArtObjectDisplay from '../components/ArtObjectDisplay'

export default {
  name: 'Artwork',
  components: { Navbar, ArtObjectDisplay },
    data () {
      return {
        user: [],
        artworks: [],
        errorUser: '',
        errorArtwork: '',
        response: []
      }
    },
    created: function () {
        
        const p1 = new userDto('John','fds','ds','gfd')
        this.user = p1
        
        AXIOS.get('user/'.concat(this.$route.params.username).concat('/view'))
        .then(response=>{
          this.user=response.data
        })
        .catch(e=>{
          errorMsg=e.response.data.message
          console.log(errorMsg)
          this.errorUser=errorMsg
        })
        AXIOS.get('artwork/'.concat(this.$route.params.username).concat('/all'))
        .then(response=>{
          this.artworks=response.data.slice(0, 8)
        })
        .catch(e=>{
          errorMsg=e.response.data.message
          console.log(errorMsg)
          this.errorArtwork=errorMsg
        })
        
        },
    methods:{
      isLoggedInAsUser: function () {
      console.log('Cookie' + document.cookie)

      if (document.cookie == this.user.username) {
        console.log('logged in: true')
        return true
      } else {
        console.log('logged in: false')
        return false
      }
    }

        }
    
}
</script>