<template>
    <div id="artworkdetails">
        <div class="left-column">
            <img src="./artImage.jpg" alt="">
            //add user section here
        </div>
        <div class="right-column">
            <h2 class="title">{{ artwork.title }}</h2>
            <p class="price">{{ artwork.price }}</p>
            <p class="description">{{ artwork.description }}</p>
        </div>
    </div>
</template>

<script>
export default {
  name: 'Artwork Details',
  props: {
    artwork: {
      type: Object,
      required: true
    }
  }
}
</script>

<style>
</style>