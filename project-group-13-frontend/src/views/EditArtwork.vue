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
          <label for="file" class="mb-0">Replace your previous image here</label><br />
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

        
        <span v-if="this.img1 == null" style="color:red"
          >Please wait for image upload to finish.</span
        >
        <button
          :disabled="this.img1 == null"
          v-on:click="updateArtwork"
          type="submit"
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

var frontendUrl = 'https://' + config.build.host + ':' + config.build.port;
var backendUrl =
  'https://' + config.build.backendHost + ':' + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});
function ArtworkDto(artworkID,isOnPremise,worth,artworkSold,description,title,creationDate,dimensions,medium,collection,imageUrl,artists){
      
      
        this.isOnPremise = isOnPremise;
        this.artists = artists;
        this.artworkID = artworkID;
        this.worth = worth;
        this.artworkSold = artworkSold;
        this.description = description;
        this.title = title;
        this.creationDate = creationDate;
        this.dimensions = dimensions;
        this.medium = medium;
        this.collection = collection;
        this.img1 = imageUrl;
}
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
      price: -1,
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
    
    AXIOS.get("artwork/byId/"+this.$route.params.artworkID)
      .then(response => {
        this.artwork = response.data;
        this.isOnPremise = this.artwork.isOnPremise;
        this.artworkID = this.artwork.artworkID;
        this.price = this.artwork.worth;
        this.artworkSold = this.artwork.artworkSold;
        this.description = this.artwork.description;
        this.title = this.artwork.title;
        this.creationDate = this.artwork.creationDate;
        this.dimensions = this.artwork.dimensions;
        this.medium = this.artwork.medium;
        this.collection = this.artwork.collection;
        this.img1 = this.artwork.imageUrl;
        var artistUsernames=this.artwork.artist.map(a=>a.username)
        if (!artistUsernames.includes(document.cookie.substr(6).split(' ')[0])) {
          Router.push({ path: "/", name: "" });
          alert("no permission to access page")
        }
      })
      .catch(e => {
        
        console.log(e);
        this.error = e;
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
    updateArtwork() {
      if (this.img1 == null) {
        this.error = "please wait until image is done uploading";
        return;
      }
      var errorMsg = "";
      var optionalComma = "";
      if (this.artists !== "") optionalComma = ",";
      
      AXIOS.put(
        "artwork/" + this.artworkID+"/update?title="+
          this.title +
          "&artist=" +
          document.cookie.substr(6).split(" ")[0] +
          optionalComma +
          this.artists +
          "&worth=" +
          this.price +
          "&imageURL=" +
          this.img1+
          "&description=" +
          this.description +
              "&creationDate=" +
              this.creationDate +
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
        })
        .catch(e => {
          ;
          console.log(e);
          this.error = e;
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
