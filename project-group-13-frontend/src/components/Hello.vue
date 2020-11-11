<template>
  <div>
  <Home/>
  </div>
</template>

<script>
import Navbar from './Navbar'
import Home from '../views/Home'

export default {
  name: 'hello',
  components: {
    Navbar,
    Home
  },
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
