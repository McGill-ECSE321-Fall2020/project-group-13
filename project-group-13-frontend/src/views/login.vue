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
                :class="errorClass"
                id="InputUsername1"
                placeholder="Enter Username"
                v-model="inputUsername"
                onfocus=""
              >
            </div>
            <div class="form-group">
              <label for="InputPassword">Password</label>
              <input
                type="password"
                class="form-control"
                :class="errorClass"
                id="InputPassword"
                placeholder="Password"
                v-model="inputPassword"
                aria-describedby="errorInCredentials"
              > 
              <small v-if="error" id="userHelp" class="form-text" style="color:red"
                >Username or password is incorrect</small
              >
            </div>
            <button v-on:click = "loginAttempt" type="submit" class="btn btn-primary">Continue</button>
          </form></span
      >
    </b-row>
    <b-row align-h="center">
            Don't have an account?&nbsp;<br>
            <router-link to="newAccount">Create account</router-link>
    </b-row>
    <br>
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
  // props: {
  //   returnTo: {
  //     type: String,
  //     required: false
  //   }
  // },
  data () {
    return {
      inputPassword: '',
      inputUsername: '',
      error: false,
      errorClass: '',
      returnTo: ''
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

  /**
   * Set eventListeners for all input areas.
   * All errors should be cleared when a user makes a change to their input
   */
  mounted () {
    var self = this

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('form-control')
    // Loop over them and make them reset errors whenever they are altered
    Array.prototype.filter.call(forms, function (form) {
      form.addEventListener('input', function (event) {
        self.error = false
        self.errorClass = ''
      }, false)
    })
    this.returnTo = this.$route.query.returnTo + this.$route.hash
    console.log(this.returnTo)
  },

  methods: {
    /**
     * loginAttempt gathers the username and password input by the user and sends an authentication request to the backend.
     * Success: A cookie containing the logged-in user's username is created
     * Failure: An error message is displayed to the user notifying them that their username/password pair is invalid
     */
    loginAttempt: function () {
      // User needs to input a nonempty username and password to login
      if (this.inputUsername === '' || this.inputPassword === '') {
        console.log('no request')
        this.error = true
        this.errorClass = 'is-invalid'
        return
      }

      // User needs to fix their username/password before attempting to login
      if (this.error) {
        console.log('no request')
        return
      }

      AXIOS.get('/user/' + this.inputUsername + '/login?password=' + this.inputPassword)
      .then((response) => {
        this.error = false
        this.errorClass = ''
        // Store username of logged in user inside a cookie.
        document.cookie = 'Token=' + response.data.username + ';path=/'

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

    /**
     * This method is called by the input eventhandlers.
     * Clears all errors from screen
     */
    resetError: function () {
      this.error = false
      this.errorClass = ''
    }
  }
}
</script>



<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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
