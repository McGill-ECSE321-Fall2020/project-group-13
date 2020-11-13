<template>
  <div>
    <Navbar />
    <Checkout />
    <ArtObjectDisplay displayName="Check out also"/>
    
    </div>
</template>

<script>
import Navbar from '../components/Navbar'
import ArtObjectDisplay from '../components/ArtObjectDisplay'
import Checkout from '../components/Checkout'
export default {
  name: 'CheckoutPage',
  components: { Checkout, Navbar, ArtObjectDisplay }
}
</script>