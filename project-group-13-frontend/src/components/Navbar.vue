/* eslint-disable */
<template>
  <div class="sticky-top">
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-navbar-brand href="/">ShareArt</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto mr-auto">
          <b-nav-form>
            <b-form-input
              v-on:keypress.native="searchTitle"
              size="sm"
              class="mr-sm-2"
              placeholder="Search"
              v-model="titleSearch"
            ></b-form-input>
            <b-button v-on:click = "searchTitle" size="sm" class="my-2 my-sm-0" type="submit"
              >Search</b-button
            >
          </b-nav-form>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          

          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template slot="button-content">
              <b-avatar :src="this.profileURL"></b-avatar>
              
            </template>
            <div v-if="isLoggedIn() == true">
            <b-dropdown-item :href="this.accountPath">My Profile</b-dropdown-item>
            <b-dropdown-item href="#/cart">View Cart</b-dropdown-item>
            <b-dropdown-item href="#/orders">Past Orders</b-dropdown-item>
            <b-dropdown-item v-on:click = "logout">Sign Out</b-dropdown-item>
            <!-- <router-link to="/#/login" tag="b-dropdown-item">Sign Out</router-link> -->
            </div>
            <div v-else>
            <b-dropdown-item v-on:click = "login">Sign In</b-dropdown-item>
            <!-- <router-link to="/#/login" tag="b-dropdown-item">Sign Out</router-link> -->
            </div>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
/* eslint-disable */
import axios from 'axios'
import Router from '../router'
var config = require('../../config')
var frontendUrl = 'https://' + config.build.host + ':' + config.build.port
var backendUrl = 'https://' + config.build.backendHost + ':' + config.build.backendPort
var AXIOS = axios.create({baseURL: backendUrl, headers: { 'Access-Control-Allow-Origin': frontendUrl }})
function getUsernameCookie(){
  if(document.cookie.length<6) return null
  else return document.cookie.substr(6).split(" ")[0]
}
function getProfilePicCookie(){
  if(document.cookie.length<6) return null
  else return document.cookie.substr(6).split(" ")[1]
}
export default {
  name: 'NavBar',
  data () {
    return {
      titleSearch: '',
      accountPath: '/',
      profileURL:"https://placekitten.com/300/300"
    }
  },
  created: function(){
    this.profileURL=getProfilePicCookie()
    window.addEventListener( 'keydown', (e) => {
      if (e.keyCode == 13) {
        this.searchTitle()
      }
    })
  },
  methods: {
    /**
     * delete the existing cookie, which is equivalent to logging a user out.
     * Then, send them back to the log in page
     */
    logout: function () {
      document.cookie = 'Token=; Max-Age=-99999999;'
      Router.push({name: 'login'})
    },
    login: function () {
      Router.push({path: '/login?returnTo=' + window.location.href})
    },
    searchTitle: function () {
    console.log(this.titleSearch)
    console.log('Go to page clicked...')

    AXIOS.get('/artwork/byTitle/?title=' + this.titleSearch)
      .then((response) => {
        console.log('here is all artworks of category')
        console.log(response.data)
        Router.push({ name: 'CategoryDisplay', params: {artwork: response}})
      })
  },
    isLoggedIn: function () {
      
      console.log('Cookie' + document.cookie)
      this.accountPath = '#/viewuser/'.concat(getUsernameCookie())
      if (document.cookie == '') {
        console.log('logged in: false')
        return false
      } else {
        console.log('logged in: true')
        return true
      }
    }
  }
}
</script>
<style></style>
