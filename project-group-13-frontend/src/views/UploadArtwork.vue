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

        <div v-if="imageData != null">
          <img class="preview" height="268" width="356" :src="img1" />
          <br />
        </div>
        <div class="form-group">
          <label for="title" class="mb-0 mt-1">Title</label><br />
          <input
            class="form-control"
            type="text"
            v-model="title"
            id="title"
            name="title"
            multiple
          /><br />
        </div>
        <div class="form-group">
          <label for="price" class="mb-0 mt-1">Price</label><br />
          <input
            class="form-control"
            type="number"
            min="0"
            oninput="validity.valid||(value='');"
            v-model="price"
            id="price"
            name="price"
            multiple
          /><br />
        </div>

        <div class="form-group">
          <label for="description" class="mb-0 mt-1">Description</label><br />
          <textarea
            class="form-control"
            id="description"
            v-model="description"
            name="description"
            rows="5"
            cols="23"
            multiple
          /><br />
        </div>

        <div class="form-group">
          <label for="collection" class="mb-0 mt-1">Collection</label><br />
          <input
            class="form-control"
            type="text"
            id="collection"
            v-model="collection"
            name="collection"
            multiple
          /><br />
        </div>

        <div class="form-group">
          <label for="collection" class="mb-0 mt-1">Dimensions</label><br />
          <input
            class="form-control"
            type="text"
            id="dimensions"
            v-model="dimensions"
            name="dimensions"
            multiple
          /><br />
        </div>

        <div class="form-group">
          <label for="medium" class="mb-0 mt-1">Category/Medium</label><br />
          <select
            v-model="medium"
            class="form-control"
            id="medium"
            name="medium"
          >
            <option disabled value="">Please select one</option>
            <option>sculpture</option>
            <option>painting</option>
            <option>objects</option>
            <option>drawing</option>
            <option>photograph</option>
          </select>
        </div>

        <div class="form-group">
          <label for="onpremises" class="mb-0 mt-1">On Premises</label>
          <input
            class="form-control"
            type="checkbox"
            v-model="isOnPremise"
            id="onpremises"
            name="onpremises"
            multiple
          /><br />
        </div>

        <div class="form-group">
          <label for="artists" class="mb-0 mt-1"
            >other collaborating artists (comma separated)</label
          ><br />
          <input
            class="form-control"
            type="text"
            id="artists"
            v-model="artists"
            name="artists"
            multiple
          /><br />
        </div>
        <span v-if="this.img1 == null" style="color:red"
          >Please wait for image upload to finish.</span
        >
        <button
          :disabled="this.img1 == null"
          v-on:click="createArtwork"
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
import Navbar from "../components/Navbar";
import firebase from "firebase";
import Router from "../router";
export default {
  components: { Navbar },
  name: "Login",
  data() {
    return {
      caption: "",
      img1: null,
      imageData: null,
      title: "",
      price: 0,
      description: "",
      collection: "",
      isOnPremise: false,
      artists: "",
      artwork: null,
      artworkID: -1,
      error: "",
      dimensions: "",
      medium: ""
    };
  },
  created() {
    if (document.cookie.length <= 6) {
      Router.push({ path: "/", name: "" });
    }
  },
  methods: {
    create() {
      const post = {
        photo: this.img1,
        caption: this.caption
      };
      //initialize firebase
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
    //upload image to firebase cloud. Get URL. Code heavily adapted from firebase documentation
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
    createArtwork() {
      if (this.img1 == null) {
        this.error = "please wait until image is done uploading";
        return;
      }
      var errorMsg = "";
      var optionalComma = "";
      if (this.artists !== "") optionalComma = ","; //artists include current user and collaborating artists. We must put a comma between them
      AXIOS.post(
        "artwork/new?title=" +
          this.title +
          "&artist=" +
          document.cookie.substr(6).split(" ")[0] +
          optionalComma +
          this.artists +
          "&worth=" +
          this.price +
          "&imageURL=" +
          this.img1
      )
        .then(response => {
          console.log(response);
          this.artwork = response.data;
          this.artworkID = response.data.artworkID;
          

          today = mm + "/" + dd + "/" + yyyy;
          AXIOS.put(
            "artwork/" +
              this.artworkID +
              "/update?description=" +
              this.description +
              "&creationDate=" +
              today +
              "&OnPremise=" +
              this.isOnPremise +
              "&medium=" +
              this.medium +
              "&dimensions=" +
              this.dimensions +
              "&collection=" +
              this.collection
          )

            .then(response => {
              console.log(response);
              this.artwork = response.data;
              this.artworkID = response.data.artworkID;
            })
            .catch(e => {
              errorMsg = e.response.data.message;
              console.log(errorMsg);
              this.error = errorMsg;
            });
        })
        .catch(e => {
          errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.error = errorMsg;
        });

      Router.push({ path: "/", name: "" });
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
