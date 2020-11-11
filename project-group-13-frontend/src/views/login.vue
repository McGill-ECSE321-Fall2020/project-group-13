<template>
  <div class="login-page">
    <!-- <NavbarBare /> -->
    <b-row align-h="center">
      <h2 class='shareArt'>ShareArt</h2>
      <!-- <img src="../components/artImage.jpg" width="50" height="50"/> -->
    </b-row>
    <b-row align-h="center">
      <span class="border"><table class="rounded">
        <tr class='title'><h2>Login</h2></tr>
          <tr class='text'><td> 
            Username 

          </td></tr>
          <tr><td> 
            <input type="text"/>
          </td></tr>
          <tr class='text'><td> 
            Password
          </td></tr>
          <tr><td> 
            <input type="text"/>
          </td></tr>
          <tr><td class='button'> 
            <button>Login</button>
          </td></tr>
          <tr><td class='button'> 
            Don't have an account?<br>
            <a href="/">register here</a>
          </td></tr>
        <!-- <p>
          <span style="color: red">Error: Message text comes here</span>
        </p> -->
        </table></span>
    </b-row>
  </div>
</template>



<script>
import NavbarBare from '../components/Navbar-bare'

export default {
  name: 'Login',
  components: {
    NavbarBare
  }
}
</script>



<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
td {
  padding: 2px;
  text-align: left;
}
td.button {
  padding: 5px;
  text-align: center;
}
span {
  padding: 10px;
}
h2.shareArt {
  padding-bottom: 20px;
}
div#app {
  margin-top: 10px;
}
</style>
