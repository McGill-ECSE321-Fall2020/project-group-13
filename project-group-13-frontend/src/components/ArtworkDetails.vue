<template>
    <div id="artworkdetails">
        <b-container class="artwork info shadow-lg p-4">
          <b-row no-gutters align-v="stretch">
            <b-col cols="6">
              <img src="./artImage.jpg" alt="Artwork image" class="art-image">
              <h4 style="margin-top: 2em;">Artists</h4>
              <div class="mb-4">
                <router-link :to="`//Insert path with user.id here`"><b-avatar></b-avatar></router-link>
                <router-link :to="`//Insert path with user.id here`"><b-avatar variant="primary" text="BV"></b-avatar></router-link>
                <router-link :to="`//Insert path with user.id here`"><b-avatar variant="info" src="https://placekitten.com/300/300"></b-avatar></router-link>
                <router-link :to="`//Insert path with user.id here`"><b-avatar variant="success" icon="people-fill"></b-avatar></router-link>
              </div>
            </b-col>
            <b-col cols="6">
              <h1 class="title">TITLE</h1>
              <h3 class="price"><span>$</span>3042948</h3>
              <p style="margin-top: 3.5em;"><u>Description</u></p>
              <p class="description">Lorem ipsum dolor sit amet consectetur, adipisicing elit. Laudantium eveniet excepturi nemo. Odit reprehenderit aliquid error neque optio aliquam temporibus iste, laboriosam, commodi ad voluptates quis alias quam, officiis porro.</p>

              <b-container style="margin-top: 5em;" class="shadow-sm">
                <b-row>
                  <b-col><p>Creation Date</p></b-col>
                  <b-col>12-02-2020</b-col>
                </b-row>
                <b-row>
                  <b-col><p>Dimensions</p></b-col>
                  <b-col>200x120x180</b-col>
                </b-row>
                <b-row>
                  <b-col><p>Medium</p></b-col>
                  <b-col></b-col>
                </b-row>
                <b-row>
                  <b-col><p>Details</p></b-col>
                  <b-col></b-col>
                </b-row>
                <b-row>
                  <b-col><p>Available</p></b-col>
                  <b-col></b-col>
                </b-row>
                <b-row>
                  <b-col><p>On premise</p></b-col>
                  <b-col></b-col>
                </b-row>
              </b-container>
            </b-col>
          </b-row>
        </b-container>
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
  .art-image {
    width: 100%;
  }
</style>