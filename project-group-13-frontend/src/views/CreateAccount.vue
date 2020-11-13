 /* eslint-disable no-eval */ 
<template>
  <div class="createAccount-page shadow-sm p-4">
    <b-row align-h="center">
      <router-link to="/" tag="h2">ShareArt</router-link>
    </b-row>
    <b-row align-h="center">
      <h3>Create Account</h3>
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
                aria-describedby="userHelp"
                placeholder="Enter Username"
                v-model="inputUsername"
              />
              <small id="userHelp" class="form-text text-muted"
                >Your username will be visible to other users</small
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
              />
            </div>
            <div class="form-group">
              <label for="InputPassword2">Verify password</label>
              <input
                type="password"
                class="form-control"
                id="InputPassword2"
                placeholder="Reenter password"
                v-model="inputPassword2"
              />
            </div>
            <div class="form-check">
              <input
                type="checkbox"
                class="form-check-input"
                id="Check1"
              />
              <label class="form-check-label" for="Check1"
                >I understand this website is not a real marketplace</label
              >
            </div>
                        
            <div class="form-check">
              <input
                type="checkbox"
                class="form-check-input"
                id="Check2"
              />
              <label class="form-check-label" for="Check2"
                >I understand that I should never post personal information that is real or legitimate</label
              >
            </div>
            <br>
            <button v-on:click = "createAccountAttempt" type="submit" class="btn btn-primary">Submit</button>
          </form></span> 
    </b-row>
    <b-row align-h="center">
            Have an account?&nbsp;<br>
            <router-link to="login">Login</router-link>
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
  name: 'CreateAccount',
  data () {
    return {
      inputPassword1: '',
      inputPassword2: '',
      inputUsername: '',
      inputEmail: '',
      visible: false
    }
  },
  methods: {
    createAccountAttempt: function () {
      AXIOS.post('/newuser/?username=' + this.inputUsername + '&password=' + this.inputPassword1 + '&email=' + this.inputEmail)
      .then((response) => {
        this.visible = false
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
div#app {
  margin-top: 10px;
}
label.form-check-label {
  white-space:normal; 
  max-width: 250px;
  text-align: left;
}
h2 {
    cursor: pointer;
}
</style>
