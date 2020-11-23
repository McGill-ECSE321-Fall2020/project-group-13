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
          <div class="form-group">
            <label for="InputEmail">Input email</label>
            <input
              type="username"
              class="form-control"
              id="InputEmail"
              placeholder="Please Input Email"
              v-model="inputEmail"
            />
          </div>
          <div class="form-check">
            <input type="checkbox" class="form-check-input" id="Check1" />
            <label class="form-check-label" for="Check1"
              >I understand this website is not a real marketplace</label
            >
          </div>

          <div class="form-check">
            <input type="checkbox" class="form-check-input" id="Check2" />
            <label class="form-check-label" for="Check2"
              >I understand that I should never post personal information that
              is real or legitimate</label
            >
            <small v-if="visible" id="userHelp" class="form-text" style="color:red"
                >Username or password is incorrect</small
              >
          </div>
          <br />
          <button
            v-on:click="createAccountAttempt"
            type="button"
            class="btn btn-primary"
          >
            Submit
          </button>
        </form></span
      >
    </b-row>
    <b-row align-h="center">
      Have an account?&nbsp;<br />
      <router-link to="login">Login</router-link>
    </b-row>
  </div>
</template>

<script>

import axios from 'axios'
import Router from '../router'
var config = require('../../config')
var frontendUrl = 'https://' + config.build.host + ':' + config.build.port
var backendUrl = 'https://' + config.build.backendHost + ':' + config.build.backendPort

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
      sleep: function (ms) {
        return new Promise(resolve => setTimeout(resolve, ms))
      },
      loginAttempt: function () {
      console.log('attempting to log user in using newly created account')
      this.sleep(1000)
     
      AXIOS.get('/user/' + this.inputUsername + '/login?password=' + this.inputPassword1)
      .then((response) => {
        console.log("successful login")
        this.error = false
        this.errorClass = ''
        // Store username of logged in user inside a cookie.
        document.cookie = 'Token=' + response.data.username +' '+response.data.profilePictureURL+';path=/;'

        // if need to return to a page, do so
        if (this.returnTo !== 'undefined') {
          window.location.href = this.returnTo
        } else {
          Router.push({name: 'Hello'})
        }
      })
      .catch(error => {
        this.error = true
        this.errorClass = 'is-invalid'
        console.log(error)
      })
    },
    createAccountAttempt: function () {
      console.log('entered here')
      //compares passwords. Only successful if they are same
      if (this.inputPassword1===this.inputPassword2) {
        console.log('/newuser/?username=' + this.inputUsername + '&password=' + this.inputPassword1 + '&email=' + this.inputEmail)
        AXIOS.post('/newuser/?username=' + this.inputUsername + '&password=' + this.inputPassword1 + '&email=' + this.inputEmail)
      .then((response) => {
        this.visible = false
        console.log('here is the response')
        console.log(response)
        this.loginAttempt(this.inputUsername, this.inputPassword1)
      })
      .catch(error => {
        this.visible = true
        console.log(error)
      })
      } else {
        console.log('input password is not the same as verification password')
        this.visible = true
        this.errorClass = 'is-invalid'
        return
      }
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
  white-space: normal;
  max-width: 250px;
  text-align: left;
}
h2 {
  cursor: pointer;
}
</style>
