<template>
  <div class="bg-white shadow-sm rounded pb-5 d-flex flex-column overflow-hidden">
    <div class="d-block position-relative h-48 overflow-hidden">
      <img
        :src="artwork.thumbnail"
        class="object-cover object-center w-100 h-100 block bg-gray-500"
      />
    </div>
    <div class="px-3 flex-1">
      <h2 class="text-gray-900 text-base font-medium my-3">{{ artwork.title }}</h2>
      <p class="mb-3 text-lg font-bold text-orange">$ {{ artwork.price }}</p>
    </div>
    <div class="px-3">
      <router-link :to="`//Insert path with artwork.id here`" class="button button-orange w-full rounded-full">View Details</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Art Object',
  props: {
    artwork: {
      type: Object,
      required: true
    }
  }
}
</script>