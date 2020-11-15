<template>
  <div id="PublicUser">
    <Navbar />
    <div class="d-flex flex-row justify-content-center align-items-center">
      <div class="p-2">
        <img :src="user.profilePictureURL" />
      </div>
      <div class="p-2">
        <h1>{{ $route.params.username }}</h1>

        <h3>
          Bio: <em>{{ user.bio }}</em>
        </h3>
        Email: {{ user.email }}

        <div v-if="isLoggedInAsUser() == true">
          <router-link to="/uploadArtwork">Add Artwork</router-link><br />
          <router-link :to="'/edituser/'+$route.params.username"
            >Edit Profile</router-link
          >
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <ArtObjectDisplay
        displayHeading="Artworks"
        :artworks="this.artworks"
        urlForPath="artwork"
        :isEditMode="isLoggedInAsUser()"
      />
    </div>
    <br>
  </div>
</template>

<script>
import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});
function userDto(username, email, bio, profilePictureURL) {
  this.username = username;
  this.email = email;
  this.bio = bio;
  this.profilePictureURL = profilePictureURL;
  this.artworks = [];
}
function artworkDto(name) {
  this.name = name;
}

import Navbar from "../components/Navbar";
import ArtObjectDisplay from "../components/ArtObjectDisplay";

export default {
  name: "Artwork",
  components: { Navbar, ArtObjectDisplay },
  data() {
    return {
      user: [],
      artworks: [],
      errorUser: "",
      errorArtwork: "",
      response: []
    };
  },
  created: function() {
    const p1 = new userDto("John", "fds", "ds", "gfd");
    this.user = p1;

    AXIOS.get("user/".concat(this.$route.params.username).concat("/view"))
      .then(response => {
        this.user = response.data;
      })
      .catch(e => {
        errorMsg = e.response.data.message;
        console.log(errorMsg);
        this.errorUser = errorMsg;
      });
    AXIOS.get("artwork/".concat(this.$route.params.username).concat("/all"))
      .then(response => {
        console.log(response);
        this.artworks = response.data.slice(0, 8);
      })
      .catch(e => {
        errorMsg = e.response.data.message;
        console.log(errorMsg);
        this.errorArtwork = errorMsg;
      });
  },
  methods: {
    isLoggedInAsUser: function() {
      console.log("Cookie" + document.cookie);

      if (document.cookie.substr(6).split(" ")[0] == this.user.username) {
        console.log("logged in: true");
        return true;
      } else {
        console.log("logged in: false");
        return false;
      }
    }
  }
};
</script>

<style scoped>
img {
  width: 300px;
  height: auto;
}
</style>