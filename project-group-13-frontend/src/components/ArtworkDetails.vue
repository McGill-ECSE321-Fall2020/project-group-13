<template>
    <div id="artworkdetails">
        <div class="left-column">
             <img src="./artImage.jpg" alt="">
        </div>
        <div class="right-column">
            <h2 class="title">TITLE</h2>
            <p class="price">3042948</p>
            <p class="description">pocterfdu;foerhfe;foiefweofehverferhfe;fwiwe;fiwej</p>
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