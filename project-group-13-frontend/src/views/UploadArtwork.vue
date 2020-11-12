<template>
  <div class="login-page">
    <!-- <NavbarBare /> -->
    <b-row align-h="center">
      <h2 class="shareArt">ShareArt</h2>
      <!-- <img src="../components/artImage.jpg" width="50" height="50"/> -->
    </b-row>
    <b-row align-h="center">
      <form>
        <label for="file">Attachments</label><br />
        <input type="file" name="file" id="file" multiple /><br />
        <label for="title">Title</label><br />
        <input type="text" id="title" name="title" multiple /><br />
        <label for="price">Price</label><br />
        <input type="number" id="price" name="price" multiple /><br />
        <label for="descripion">Description</label><br />
        <textarea id="description" name="description" rows="10" cols="30" multiple /><br />
        <label for="collection">Collection</label><br />
        <input type="text" id="collection" name="collection" multiple /><br />
        <label for="onpremises">On Premises</label><br />
        <input type="checkbox" id="onpremises" name="onpremises" multiple /><br />
        <label for="email">Email Address</label><br />
        <input type="email" id="email" name="email" multiple /><br />

        <input type="submit" value="Send" />
      </form>
    </b-row>
  </div>
</template>

<script>
export default {
  name: 'Login'
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
