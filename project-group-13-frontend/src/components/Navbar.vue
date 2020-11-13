<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-navbar-brand href="#">NavBar</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto mr-auto">
          <b-nav-form>
            <b-form-input
              size="sm"
              class="mr-sm-2"
              placeholder="Search"
            ></b-form-input>
            <b-button size="sm" class="my-2 my-sm-0" type="submit"
              >Search</b-button
            >
          </b-nav-form>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          <b-button size="sm" class="my-2 my-sm-0" type="submit">
            <icon-base width="22" height="22" iconColor="rgba(100,100,100,100)" icon-name="shopping-cart"><icon-shopping-cart/></icon-base></b-button>

          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template slot="button-content">
              <icon-base width="22" height="22" iconColor="rgba(255,255,255,100)" icon-name="user-profile"><icon-profile/></icon-base>
            </template>
            <b-dropdown-item href="#">Account</b-dropdown-item>
            <b-dropdown-item href="#">Orders</b-dropdown-item>
            <b-dropdown-item href="#">Artwork</b-dropdown-item>
            <b-dropdown-item v-on:click = "logout">Sign Out</b-dropdown-item>
            <!-- <router-link to="/#/login" tag="b-dropdown-item">Sign Out</router-link> -->
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import Router from '../router'

export default {
  name: 'NavBar',

  methods: {
    /**
     * delete the existing cookie, which is equivalent to logging a user out.
     * Then, send them back to the log in page
     */
    logout: function () {
      document.cookie = 'Token=; Max-Age=-99999999;'
      Router.push({name: 'login'})
    }
  }
}
</script>
<style></style>
