<template>
  <div class="login-page">
    <Navbar />
    <b-row align-h="center">
      <h2 class="shareArt"></h2>
      <!-- <img src="../components/artImage.jpg" width="50" height="50"/> -->
    </b-row>
    <b-row align-h="center">
      <span v-if="error" style="color:red">Error: {{ error }} </span>
      <form>
        <div class="form-group">
          <label for="file" class="mb-0">Attachments</label><br />
          <input
            class="form-control"
            @change="previewImage"
            accept="image/*"
            type="file"
            name="file"
            id="file"
            multiple
          /><br />
        </div>

        <div>
          <img class="preview" height="268" width="356" :src="img1" />
          <br />
        </div>
        <div class="form-group">
          <label for="email" class="mb-0 mt-1">Email</label><br />
          <input
            class="form-control"
            type="text"
            v-model="email"
            
            id="email"
            name="email"
            multiple
          /><br />
        </div>

        <div class="form-group">
          <label for="bio" class="mb-0 mt-1">Bio</label><br />
          <textarea
            class="form-control"
            id="bio"
            v-model="bio"
            name="bio"
            
            rows="5"
            cols="23"
            multiple
          /><br />
        </div>

        <span v-if="this.img1 == null" style="color:red"
          >Please wait for image upload to finish.</span
        >
        <button
          :disabled="this.img1 == null"
          v-on:click="updateProfile"
          class="btn btn-primary"
        >
          Continue
        </button>
      </form>
    </b-row>
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
import Navbar from "../components/Navbar";
import firebase from "firebase";
import Router from "../router";
export default {
  components: { Navbar },
  name: "EditProfile",
  data() {
    return {
      caption: "",
      img1: '',
      imageData: null,
      bio: "",
      email: ""
    };
  },
  created() {
    if (document.cookie.substr(6).split(' ')[0]!==this.$route.params.username) {
      Router.push({ path: "/", name: "" });
      alert("no permission to access this page")
    }

    AXIOS.get("user/".concat(this.$route.params.username).concat("/view"))
      .then(response => {
        this.user = response.data;
        this.img1 = this.user.profilePictureURL;
        this.email=this.user.email;
        this.bio=this.user.bio;
      })
      .catch(e => {
        errorMsg = e.response.data.message;
        console.log(errorMsg);
        this.errorUser = errorMsg;
      });
  },
  methods: {
    create() {
      const post = {
        photo: this.img1,
        caption: this.caption
      };
      firebase
        .database()
        .ref("PhotoGallery")
        .push(post)
        .then(response => {
          console.log(response);
        })
        .catch(err => {
          console.log(err);
        });
    },
    previewImage(event) {
      this.uploadValue = 0;
      this.img1 = null;
      this.imageData = event.target.files[0];
      this.onUpload();
    },
    onUpload() {
      this.img1 = null;
      const storageRef = firebase
        .storage()
        .ref(`${this.imageData.name}`)
        .put(this.imageData);
      storageRef.on(
        `state_changed`,
        snapshot => {
          this.uploadValue =
            (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
        },
        error => {
          console.log(error.message);
        },
        () => {
          this.uploadValue = 100;
          storageRef.snapshot.ref.getDownloadURL().then(url => {
            this.img1 = url;
            console.log(this.img1);
          });
        }
      );
    },
    updateProfile() {
      if (this.img1 == null) {
        this.error = "please wait until image is done uploading";
        return;
      }
      var errorMsg = "";
      var optionalComma = "";
      const body = {
        email: this.email,
        bio:
          this.bio,
        profilePictureURL: this.img1
      };

      AXIOS.put("user/" + this.user.username + "/edit", body)
        .then(response => {
          console.log(response);
        })
        .catch(e => {
          errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.error = errorMsg;
        });
      //update cookie with new imageURL info
      document.cookie = 'Token=' + document.cookie.substr(6).split(' ')[0] + ' ' + this.img1 + ';path=/;'
      Router.push({ path: "/viewuser/" + this.user.username });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
td {
  padding: 2px;
  text-align: left;
}
td.button {
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
</style>
