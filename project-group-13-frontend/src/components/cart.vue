<template>
  <div class="bg-white shadow-sm rounded pb-5 d-flex flex-column overflow-hidden">
    <div class="d-block position-relative h-48 overflow-hidden">
      <img
        :src="artwork.thumbnail"
        class="object-cover object-center w-100 h-100 d-block bg-secondary"
      />
    </div>
    <div class="px-3 flex-1">
      <h2 class="text-secondary font-medium mt-3 mb-0">{{ artwork.title }}</h2>
      <p class="mb-3 text-lg font-bold">$ {{ artwork.price }}</p>
    </div>
    <div class="px-3">
      <router-link :to="`//Insert path with artwork.id here`" class="button button-orange w-full rounded-full">Buy</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'cart',
  props: {
    artwork: {
      type: Object,
      required: true
    }
  }
}
</script>
<style>
  .object-cover {
    object-fit: cover;
  }

  .object-center {
    object-position: center;
  }	

  .flex-1 {
    flex: 1 1 0%;
  }
</style>