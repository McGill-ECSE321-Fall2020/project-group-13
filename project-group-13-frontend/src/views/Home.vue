<template>
  <div>
    <Navbar />
    <ArtObjectDisplay/>
    </div>
</template>

<script>
import Navbar from '../components/Navbar'
import ArtObjectDisplay from '../components/ArtObjectDisplay'
export default {
  name: 'Home',
  components: { Navbar, ArtObjectDisplay },
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
