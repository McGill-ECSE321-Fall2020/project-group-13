<template>
<div class="mt-2 pt-2">
    <b-container class="shadow-lg rounded">
        <b-row class="max-w-5xl mx-auto px-1 pt-3 m-0">
            <div class=""><h4 class="d-inline">{{displayHeading}} | </h4><p class="d-inline"> See more &rarr;</p></div>
        </b-row>
        <b-row>
            <div class="max-w-5xl mx-auto px-1 py-1 m-0">
    <div class="d-flex flex-wrap mx-0 mt-0 justify-content-center">
        <!-- The line below is for instantiating multiple artobjects -->
      <!-- <div
        v-for="artwork in artworks"
        :key="artwork.id"
        class="flex w-full p-3 md:w-1/2 lg:w-1/4"
      >
        <ArtObject :artwork="artwork"/>
      </div> -->
      <ArtObject v-bind:artwork="{
    title: 'Veronica',
    price: 10,
    thumbnail: require('./artImage.jpg')
  }"></ArtObject>
   <ArtObject v-bind:artwork="{
    title: 'Cesar',
    price: 114225,
    thumbnail: require('./artImage.jpg')
  }"></ArtObject>
   <ArtObject v-bind:artwork="{
    title: 'Veronica',
    price: 10,
    thumbnail: require('./artImage.jpg')
  }"></ArtObject>
   <ArtObject v-bind:artwork="{
    title: 'Veronica',
    price: 10,
    thumbnail: require('./artImage.jpg')
  }"></ArtObject>
   <ArtObject v-bind:artwork="{
    title: 'Veronica',
    price: 10,
    thumbnail: require('./artImage.jpg')
  }"></ArtObject>
  <ArtObject v-bind:artwork="{
    title: 'Veronica',
    price: 10,
    thumbnail: require('./artImage.jpg')
  }"></ArtObject>
  <ArtObject v-bind:artwork="{
    title: 'Veronica',
    price: 10,
    thumbnail: require('./artImage.jpg')
  }"></ArtObject>
  <ArtObject v-bind:artwork="{
    title: 'Veronica',
    price: 10,
    thumbnail: require('./artImage.jpg')
  }"></ArtObject>
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
    displayHeading: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      artworks: []
    }
  },
  computed: {
    slug () {
      return this.$route.params.slug ? this.$route.params.slug : 'all'
    }
  },
  watch: {
    $route: 'getArtworks'
  },
  mounted () {
    this.getArtworks()
  },
  methods: {
    async getArtworks () {
      try {
        const res = await fetch(`http://localhost:3000/artworks`)
        const json = await res.json()
        this.artworks = json
      } catch (e) {
        console.log(e)
      }
    }
  }
}
</script>

<style scoped>
    .max-w-5xl {
        max-width: 70rem;
    }
</style>