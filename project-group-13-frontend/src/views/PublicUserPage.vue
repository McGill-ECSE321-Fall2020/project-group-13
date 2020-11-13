<template>
  <div>
    <Navbar/>
    
    
    
    <ArtObjectDisplay displayHeading="Artworks"/>
    
    </div>
</template>

<script>
import Navbar from '../components/Navbar'

export default {
  name: 'Artwork',
  components: { Navbar, ArtObjectDisplay }
}
</script>