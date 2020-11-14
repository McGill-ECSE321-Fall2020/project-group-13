<template>
    <div class="root">
        <b-container class="artwork info shadow-lg p-4">
          <b-row no-gutters align-h="center">
            <b-col cols="8">
              <b-card header="Items in cart:" class="card w-75" style="margin-top: 1em;">
                <b-list-group>
                  <b-list-group-item v-for="(artwork,i) in cart.artwork" v-bind:key="`artwork-${i}`">
                    <span class="border"><b-row>
                      <b-col cols="3">
                        <div class="d-block position-relative h-48 overflow-hidden">
                            <img
                              :src="artwork.imageUrl"
                              class="object-cover object-center w-100 h-100 d-block bg-secondary"
                            />
                        </div>
                      </b-col>
                      <b-col>
                        <div class="px-3 flex-1">
                          <h3 class="text-secondary font-medium mt-3 mb-0">{{ artwork.title }}</h3>
                          <!-- <p class="mb-3 text-lg font-bold">{{ artistNameList }}</p> -->
                        </div>
                      </b-col>
                      <b-col>
                        <div class="px-3">
                          <h3 class="mb-3 text-lg font-bold mt-3 mb-0">$ {{ artwork.worth.toFixed(2) }}</h3>
                        </div>
                      </b-col>
                    </b-row></span>
                  </b-list-group-item>
                </b-list-group>
                <p style="margin-top: 2em;"><strong>Subtotal: ${{ cart.totalCost.toFixed(2) }}</strong></p>
              </b-card>
            </b-col>

            <b-col cols="4">
              <b-form @submit="onSubmit" @reset="onReset" v-if="show">
                <b-form-group
                    id="input-group-1"
                    label="Card number:"
                    label-for="input-1"
                >
                    <b-form-input
                    id="input-1"
                    v-model="form.cardNumber"
                    type="number"
                    required
                    placeholder="Enter 16-digit Card Number"
                    ></b-form-input>
                </b-form-group>

                <b-form-group id="input-group-2" label="Expiry Date:" label-for="input-2">
                    <b-form-input
                    id="input-2"
                    v-model="form.expiryDate"
                    type="date"
                    required
                    placeholder="Enter Date"
                    ></b-form-input>
                </b-form-group>

                <b-form-group id="input-group-3" label="CVV:" label-for="input-3">
                    <b-form-input
                    id="input-3"
                    v-model="form.cvv"
                    type="number"
                    required
                    placeholder="Enter 3-digit CVV"
                    ></b-form-input>
                </b-form-group>

                <b-form-group id="input-group-4" label="Name on Card:" label-for="input-4">
                    <b-form-input
                    id="input-4"
                    v-model="form.name"
                    required
                    placeholder="Enter name as it appears on card"
                    ></b-form-input>
                </b-form-group>

                <b-button type="submit" variant="primary">Submit</b-button>
                <b-button type="reset" variant="danger">Reset</b-button>
                <p>We'll never share your information with anyone else.</p>
              </b-form>
            </b-col>
          </b-row>
        </b-container>
    </div>
</template>

<script>
  import axios from 'axios'
  import Router from '../router'
  var config = require('../../config')
  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
  var AXIOS = axios.create({baseURL: backendUrl, headers: { 'Access-Control-Allow-Origin': frontendUrl }})
  export default {
    data () {
      return {
        form: {
          cardNumber: '',
          expiryDate: '',
          cvv: '',
          name: ''
        },
        show: true,
        cart: null
      }
    },
    created: function () {
      // getting username from cookie
      const username = document.cookie.substring(6)
      // Fetching cart items from backend
      AXIOS.get('/user/' + username + '/cart')
      .then(response => {
      // JSON responses are automatically parsed.
        this.cart = response.data
      })
    },
    methods: {
      onSubmit (evt) {
        evt.preventDefault()
        alert(JSON.stringify(this.form))
      },
      onReset (evt) {
        evt.preventDefault()
        // Reset our form values
        this.form.cardNumber = ''
        this.form.expiryDate = ''
        this.form.cvv = ''
        // Trick to reset/clear native browser form validation state
        this.show = false
        this.$nextTick(() => {
          this.show = true
        })
      }
    }
  }
</script>

<style>
  img {
  max-width: 50px;
  height: auto;
  }
  .flex-1 {
    flex: 1 1 0%;
  }
  span {
  padding: 10px;
  }
</style>