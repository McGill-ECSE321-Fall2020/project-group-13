<template>
  <div class="createAccount-page shadow-sm p-4">
    <b-row align-h="center">
      <h2 class="shareArt">ShareArt</h2>
    </b-row>
    <b-row align-h="center">
      <h3>Create Account</h3>
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
                aria-describedby="userHelp"
                placeholder="Enter Username"
              />
              <small id="userHelp" class="form-text text-muted"
                >Your username will be visible to other users</small
              >
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
            <div class="form-group">
              <label for="InputPassword2">Verify password</label>
              <input
                type="password"
                class="form-control"
                id="InputPassword2"
                placeholder="Reenter password"
              />
            </div>
            <div class="form-check">
              <input
                type="checkbox"
                class="form-check-input"
                id="Check1"
              />
              <label class="form-check-label" for="Check1"
                >I understand this website is not a real marketplace</label
              >
            </div>
                        
            <div class="form-check">
              <input
                type="checkbox"
                class="form-check-input"
                id="Check2"
              />
              <label class="form-check-label" for="Check2"
                >I understand that I should never post personal information that is real or legitimate</label
              >
            </div>
            <br>
            <button type="submit" class="btn btn-primary">Submit</button>
          </form></span
      >
    </b-row>
    <b-row align-h="center">
            Have an account?&nbsp;<br>
            <router-link to="login">Login</router-link>
    </b-row>
  </div>
</template>



<script>

export default {
  name: 'CreateAccount'
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
div#app {
  margin-top: 10px;
}
label.form-check-label {
  white-space:normal; 
  max-width: 250px;
  text-align: left;
}
</style>
