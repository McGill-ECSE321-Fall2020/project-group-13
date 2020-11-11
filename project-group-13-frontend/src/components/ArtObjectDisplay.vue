<template>
  <div class="max-w-5xl mx-auto px-6 py-12">
    <div class="d-flex flex-wrap m-3 justify-content-center">
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
    </div>
  </div>
</template>

<script>
import ArtObject from './ArtObject.vue'
export default {
  name: 'Home',
  components: { ArtObject },
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
        max-width: 64rem;
    }
</style>