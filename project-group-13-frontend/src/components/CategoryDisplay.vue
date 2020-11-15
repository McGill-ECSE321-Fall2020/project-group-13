

<template>
<div class="mt-2 pt-2">
    <b-container class="shadow-lg rounded">
        <b-row class="max-w-5xl mx-auto px-1 pt-3 m-0">
            <div class=""><h4 class="d-inline">{{displayHeading}} | </h4><p class="d-inline"> See more &rarr;</p></div>
        </b-row>
        <b-row>
            <div class="max-w-5xl mx-auto px-1 py-1 m-0">
    <div class="d-flex flex-wrap mx-0 mt-0 justify-content-center">
      <CategoryObject
    urlForPath="byCategory/category"  
    v-bind:artwork="{
    artworkID: 'sculpture',
    title: 'Sculpture',
    worth: 10,
    imageUrl: require('@/assets/images/sculpture.jpg')
  }
  
  "></CategoryObject>
   <CategoryObject
    urlForPath="byCategory/category"
    v-bind:artwork="{
    artworkID: 'painting',
    title: 'Painting',
    worth: 0,
    imageUrl: require('@/assets/images/painting.jpg')
  }"></CategoryObject>
   <CategoryObject
    urlForPath="byCategory/category"  
    v-bind:artwork="{
    artworkID: 'object',
    title: 'Objects',
    worth: 0,
    imageUrl: require('@/assets/images/object.jpg')
  }"></CategoryObject>
   <CategoryObject
    urlForPath="byCategory/category"
    v-bind:artwork="{
    artworkID: 'drawing',
    title: 'Drawing',
    worth: 0,
    imageUrl: require('@/assets/images/drawing.jpg')
  }"></CategoryObject>
    </div>
  </div>
        </b-row>
    </b-container>
  
  </div>
</template>

<script>

/* eslint-disable */

import ArtObject from './ArtObject.vue'
import CategoryObject from './CategoryObject.vue'
export default {
  name: 'Home',
  components: { ArtObject, CategoryObject },
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