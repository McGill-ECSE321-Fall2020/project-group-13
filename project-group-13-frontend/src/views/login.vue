<template>
  <div class="createAccount-page shadow-sm p-4">
    <b-row align-h="center">
      <router-link to="/" tag="h2">ShareArt</router-link>
    </b-row>
    <b-row align-h="center">
      <h3>Login</h3>
    </b-row>
    <b-row align-h="center">
      <span class="border"
        ><form>
            <div class="form-group">
              <label for="InputUsername">Username</label>
              <input
                type="username"
                class="form-control"
                id="InputUsername"
                placeholder="Enter Username"
                v-model="inputUsername"
              >
            </div>
            <div class="form-group">
              <label for="InputPassword1">Password</label>
              <input
                type="password"
                class="form-control"
                id="InputPassword1"
                placeholder="Password"
                v-model="inputPassword1"
              > 
            </div>
            <div id="wrapper">
              <span v-if="visible" style="color:red">Username or password is incorrect </span>
              <span v-else style="color:red"></span>
              <br>
              <button v-on:click = "loginAttempt" type="submit" class="btn btn-primary">Continue</button>
            </div>
          </form></span
      >
    </b-row>
    <b-row align-h="center">
            Don't have an account?&nbsp;<br>
            <router-link to="newAccount">Create account</router-link>
    </b-row>
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
  name: 'Login',
  data () {
    return {
      inputPassword1: '',
      inputUsername: '',
      visible: false
    }
  },

  /**
   * The login page should only be accessible when the user is logged out.
   * Redirect to landing page if user is already logged in
   */
  created () {
    if (document.cookie.length > 6) {
      Router.push({path: '/', name: ''})
    }
  },

  methods: {
    /**
     * loginAttempt gathers the username and password input by the user and sends an authentication request to the backend.
     * Success: A cookie containing the logged-in user's username is created
     * Failure: An error message is displayed to the user notifying them that their username/password pair is invalid
     */
    loginAttempt: function () {
      AXIOS.get('/user/' + this.inputUsername + '/login?password=' + this.inputPassword1)
      .then((response) => {
        this.visible = false
        // Store username of logged in user inside a cookie.
        document.cookie = 'Token=' + response.data.username + ';path=/'
        Router.push({path: '/', name: ''})
      })
      .catch(error => {
        this.visible = true
        console.log(error)
      })
    }
  }
}
</script>



<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
td {
  padding: 2px;
  text-align: left;
}
button {
  padding: 5px;
  text-align: center;
}
span {
  padding: 10px;
}
h2.shareArt {
  padding-bottom: 20px;
}
.mt-0 {
  margin-top: 0 !important;
}
h2 {
    cursor: pointer;
}
</style>
