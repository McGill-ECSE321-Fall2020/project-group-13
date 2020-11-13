

<template>
<div class="mt-2 pt-2">
    <b-container class="shadow-lg rounded">
        <b-row class="max-w-5xl mx-auto px-1 pt-3 m-0">
            <div class=""><h4 class="d-inline">{{displayHeading}} | </h4><p class="d-inline"> See more &rarr;</p></div>
        </b-row>
        <b-row>
            <div class="max-w-5xl mx-auto px-1 py-1 m-0">
    <div class="d-flex flex-wrap mx-0 mt-0 justify-content-center">
      <ArtObject v-bind:artwork="{
    title: 'Sculpture',
    price: 10,
    thumbnail: require('../assets/images/sculpture.jpg')
  }"></ArtObject>
   <ArtObject v-bind:artwork="{
    title: 'Painting',
    price: 0,
    thumbnail: require('../assets/images/painting.jpg')
  }"></ArtObject>
   <ArtObject v-bind:artwork="{
    title: 'Objects',
    price: 0,
    thumbnail: require('../assets/images/object.jpg')
  }"></ArtObject>
   <ArtObject v-bind:artwork="{
    title: 'Drawing',
    price: 0,
    thumbnail: require('../assets/images/drawing.jpg')
  }"></ArtObject>
    </div>
  </div>
        </b-row>
    </b-container>
  
  </div>
</template>

<script>

/* eslint-disable */

import ArtObject from './ArtObject.vue'
export default {
  name: 'Home',
  components: { ArtObject },
  props: {
    displayHeading: {
      type: String,
      required: true
    }
  }
}
</script>

<style scoped>
    .max-w-5xl {
        max-width: 70rem;
    }
</style>