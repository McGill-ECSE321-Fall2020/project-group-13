<template>
  <div class="createAccount-page shadow-sm p-4">
    <b-row align-h="center">
      <router-link to="/" tag="h2">ShareArt</router-link>
    </b-row>
    <b-row align-h="center">
      <h3>Login</h3>
    </b-row>
    <b-row align-h="center">
      <span class="border"
        ><form>
            <div class="form-group">
              <label for="InputUsername">Username</label>
              <input
                type="username"
                class="form-control"
                id="InputUsername"
                placeholder="Enter Username"
              />
            </div>
            <div class="form-group">
              <label for="InputPassword1">Password</label>
              <input
                type="password"
                class="form-control"
                id="InputPassword1"
                placeholder="Password"
              />
            </div>
            <button type="submit" class="btn btn-primary">Continue</button>
          </form></span
      >
    </b-row>
    <b-row align-h="center">
            Don't have an account?&nbsp;<br>
            <router-link to="newAccount">Create account</router-link>
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
button {
  padding: 5px;
  text-align: center;
}
span {
  padding: 10px;
}
h2.shareArt {
  padding-bottom: 20px;
}
.mt-0 {
  margin-top: 0 !important;
}
h2 {
    cursor: pointer;
}
</style>
