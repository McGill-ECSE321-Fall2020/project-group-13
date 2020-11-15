<template>
  <div class="bg-white shadow-sm rounded pb-2 mx-1 d-flex flex-column overflow-hidden zoom" v-if="isDeleted==false" v-on:click = "artworkClicked">
    <div class="row align-self-start">
    <div class="d-block position-relative h-48 overflow-hidden">
      <img
        :src="artwork.imageUrl"
        class="object-cover object-center w-100 h-100 d-block bg-secondary"
      />
    </div>
    </div>
    <div class="row bottom-of-flex">
      <div class="col">
        <div class="px-3 flex-1">
          <h3 class="text-secondary font-medium mt-3 mb-0">{{ artwork.title }}</h3>
          <p class="mb-3 text-lg font-bold">$ {{ artwork.worth }}</p>
        </div>
        <div class="px-3">
          <b-button class="btn btn-danger text-light w-10 self-align-center" v-if="isEditMode" v-on:click = "deleteArtwork">Delete
          </b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Router from '../router'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
  name: 'ArtObject',
  data () {
      return {
        error:'',
        isDeleted:false
      }
    },
  props: {
    urlForPath: {
      type: String,
      required: true
    },
    artwork: {
      type: Object,
      required: true
    },
    isEditMode:{
      type: Boolean,
      default:false,
      required: false

    }
  },
  methods:{
    deleteArtwork(){
      AXIOS.delete('artwork/'+this.artwork.artworkID+'/delete')
        .then(response=>{
          this.isDeleted=true
        })
        .catch(e=>{
          errorMsg=e.response.data.message
          console.log(errorMsg)
          this.error=errorMsg
        })
    },
    artworkClicked() {
      Router.push({path: `/` + this.urlForPath + '/' + this.artwork.artworkID})
    }
  }
}
</script>
<style scoped>
.bottom-of-flex {
  margin-top: auto;
}
  .object-cover {
    object-fit: cover;
  }

  .object-center {
    object-position: center;
  }	

  .flex-1 {
    flex: 1 1 0%;
  }
  img {
  max-width: 300px;
  height: auto;
}
h3, p {
  max-width: 235px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: block;
}

.zoom {
  transition: transform .2s; /* Animation */
  margin: 0 auto;
  z-index:1;
}

.zoom:hover {
  transform: scale(1.1); /* (150% zoom - Note: if the zoom is too large, it will go outside of the viewport) */
  z-index:1000;
}

.center-cropped {
  height: 150px;
  background-position: center center;
  background-repeat: no-repeat;
}
</style>