<template>
  <div>
    <Navbar />
    <br>
    <ArtworkDetails />
    <!-- <ArtObjectDisplay displayHeading="Similar Artwork" urlForPath="artwork"/>
     -->
    </div>
</template>

<script>
import Navbar from '../components/Navbar'
import ArtObjectDisplay from '../components/ArtObjectDisplay'
import ArtworkDetails from '../components/ArtworkDetails.vue'
export default {
  name: 'Artwork',
  components: { ArtworkDetails, Navbar, ArtObjectDisplay }
}
</script>