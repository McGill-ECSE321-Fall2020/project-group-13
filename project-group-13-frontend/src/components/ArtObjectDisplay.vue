<template>
<div class="mt-2 pt-2">
    <b-container class="shadow-lg rounded">
        <b-row class="max-w-5xl mx-auto px-1 pt-3 m-0">
            <div class=""><h4 class="d-inline">{{displayHeading}} | </h4><p class="d-inline"> See more &rarr;</p></div>
        </b-row>
        <b-row>
            <div class="max-w-5xl mx-auto px-1 py-1 m-0">

        <!-- The line below is for instantiating multiple artobjects -->
      <div
        v-for="artwork in artworks"
        :key="artwork.id"
        class="d-flex flex-wrap mx-0 mt-0 justify-content-center"
      >
        <ArtObject :artwork="artwork" :urlForPath="urlForPath"/>
      </div>
    
  </div>
        </b-row>
    </b-container>
  
  </div>
</template>

<script>
import ArtObject from './ArtObject.vue'
export default {
  name: 'Home',
  components: { ArtObject },
  props: {
    urlForPath: {
      type: String,
      required: true
    },
    displayHeading: {
      type: String,
      required: true
    },
    artworks: {
      type: Array,
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