<template>
    <div class="root">
        <b-container class="artwork info shadow-lg p-4">
          <b-row no-gutters align-h="center">
            <b-col cols="5">
              <b-card header="Items in cart:" class="card w-100" style="margin-top: 0em;">
                <b-list-group>
                  <b-list-group-item v-for="(artwork,i) in cart.artwork" v-bind:key="`artwork-${i}`" height="75px">
                    <b-row>
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
                          <h4 class="text-secondary font-medium mt-3 mb-0">{{ artwork.title }}</h4>
                          <!-- <p class="mb-3 text-lg font-bold">{{ artistNameList }}</p> -->
                        </div>
                      </b-col>
                      <b-col>
                        <div class="px-3">
                          <h4 class="mb-3 text-lg font-bold mt-3 mb-0">$ {{ artwork.worth.toFixed(2) }}</h4>
                        </div>
                      </b-col>
                    </b-row>
                  </b-list-group-item>
                </b-list-group>
                <h3 style="margin-top: 2em;"><strong>Subtotal: ${{ cart.totalCost.toFixed(2) }}</strong></h3>
              </b-card>
            </b-col>

            <b-col cols="7">
              <div class="container">
                <form>

                  <div class="row">
                    <div class="col-50" >
                      <form method="post">
                        <h3>Please select method of delivery:</h3>
                        <input v-model="deliveryMethod" type="radio" id="pickup" name="method" value="pickup" style="margin-top: 4em;">
                        <label for="pickup">Pick up</label><br>
                        <input v-model="deliveryMethod" type="radio" id="delivery" name="method" value="delivery">
                        <label for="delivery">Delivery</label><br>
                      </form>
                    </div>
                    <div v-if="displayRadioValue() === true" class="col-50">  <!-- v-if="hasAddresses() === false" -->
                      <h3>Shipping Address</h3>
                      <label for="fname" style="margin-top: 4.7em;"><i class="fa fa-user"></i> Full Name</label>
                      <input type="text" id="fname" name="firstname" placeholder="John M. Doe">
                      <label for="adr"><i class="fa fa-address-card-o"></i> Address</label>
                      <input type="text" id="adr" v-model="addressForm.streetAddress1" name="address" placeholder="542 W. 15th Street">
                      <label for="city"><i class="fa fa-institution"></i> City</label>
                      <input type="text" id="city" v-model="addressForm.city" name="city" placeholder="Montreal">

                      <div class="row">
                        <div class="col-50">
                          <label for="state">Province</label>
                          <input type="text" id="state" v-model="addressForm.province" name="state" placeholder="QC">
                        </div>
                        <div class="col-50">
                          <label for="zip">Postal Code</label>
                          <input type="text" id="zip" v-model="addressForm.postalCode"  name="zip" placeholder="10001">
                        </div>
                        <div class="col-50">
                          <label>
                            <input type="checkbox" checked="checked" name="sameadr" style="margin-bottom: 3em;"> Billing address same as shipping
                          </label>
                        </div>
                      </div>
                    </div>

                    <!-- <div v-else>

                    </div> -->

                    <div class="col-50">
                      <h3>Payment</h3>
                      <label for="fname">Accepted Cards</label>
                      <div class="icon-container">
                        <i class="fa fa-cc-visa" style="color:navy;"></i>
                        <i class="fa fa-cc-amex" style="color:blue;"></i>
                        <i class="fa fa-cc-mastercard" style="color:red;"></i>
                        <i class="fa fa-cc-discover" style="color:orange;"></i>
                      </div>
                      <b-form @reset="onReset" v-if="show">
                        <b-form-group
                            id="input-group-1"
                            label="Card number:"
                            label-for="input-1"
                        >
                            <b-form-input
                            id="input-1"
                            v-model="paymentForm.cardNumber"
                            type="number"
                            min="0"
                            required
                            placeholder="Enter 16-digit Card Number"
                            ></b-form-input>
                        </b-form-group> 

                        <b-form-group id="input-group-2" label="Expiry Date:" label-for="input-2">
                            <b-form-input
                            id="input-2"
                            v-model="paymentForm.expirationDate"
                            type="date"
                            required
                            placeholder="Enter Date"
                            ></b-form-input>
                        </b-form-group>

                        <b-form-group id="input-group-3" label="CVV:" label-for="input-3">
                            <b-form-input
                            id="input-3"
                            v-model="paymentForm.cvv"
                            type="number"
                            min="100"
                            max="999"
                            required
                            placeholder="Enter 3-digit CVV"
                            ></b-form-input>
                        </b-form-group>

                        <b-form-group id="input-group-4" label="Name on Card:" label-for="input-4">
                            <b-form-input
                            id="input-4"
                            v-model="paymentForm.nameOnCard"
                            required
                            placeholder="Enter name as it appears on card"
                            ></b-form-input>
                        </b-form-group>

                        <b-button type="reset" variant="danger">Reset</b-button>
                      </b-form>
                    </div>

                  </div>
                  <span v-if="error" style="color:red">{{error}} </span>
                  <b-button type="submit" v-on:click = "onSubmit" style="margin-top: 2em;" variant="primary">Submit Order</b-button>
                  <p>We'll never share your information with anyone else.</p>
                </form>
              </div>

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
  function getUsernameCookie(){
  if(document.cookie.length<6) return null
  else return document.cookie.substr(6).split(" ")[0]
}
  export default {
    data () {
      return {
        paymentForm: {
          cardNumber: '',
          expirationDate: '',
          cvv: '',
          nameOnCard: ''
        },
        addressForm: {
          username: getUsernameCookie(),
          streetAddress1: '',
          city: '',
          province: '',
          country: 'Canada',
          postalCode: ''
        },
        show: true,
        cart: null,
        deliveryMethod: '',
        deliveryMethodChosen: false,
        error:''
      }
    },
    created: function () {
      // getting username from cookie
      const username = getUsernameCookie()
      
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
        if(this.paymentForm.cardNumber.toString().length !==16){
          this.error = 'Card number must be 16 digits'
          return
        }
        if(this.paymentForm.cvv.toString().length !==3){
          this.error = 'Card CVV must be 3 digits'
          return
        }
        if(!this.paymentForm.nameOnCard){
          this.error = 'Name on card not filled out'
          return
        }
        console.log(new Date())
          console.log(this.paymentForm.expirationDate)
        if(new Date(this.paymentForm.expirationDate)<new Date()){
          
          this.error = 'Card expiry date invalid'
          return
        }
        // alert("Just testing")
        // alert(JSON.stringify(this.addressForm))
        this.error='Loading...'
        const username = getUsernameCookie()
        if (this.deliveryMethodChosen == true) {
          AXIOS.post('/user/' + username + '/new/address', this.addressForm)
          .then(response => {
          // JSON responses are automatically parsed.
            //alert("Address created")
          }).catch(e => {
            this.error = e;
            console.log(error);
            return
          });
        }

        // creating order
        AXIOS.post('/user/' + username + '/new/order', this.addressForm)
        .then(response => {
        // JSON responses are automatically parsed.
          // paying for order
          if (this.deliveryMethod === "delivery") {
            AXIOS.put('/order/' + this.response.orderID + '/delivery?delivery=true')
            .then(response => {
              console.log('testing')
            }).catch(e => {
              console.log(e + 'fail')
            })
          }
          AXIOS.post('/order/' + response.data.orderID + '/pay', this.paymentForm)
          .then(response => {
          // JSON responses are automatically parsed.
            alert("Your order has been placed!")
            Router.push({path: '/' })
          }).catch(e => {
            this.error = e;
            console.log(error);
            return
          });
        }).catch(e => {
          this.error = e;
          console.log(error);
          return
        });

        
      },
      onReset (evt) {
        evt.preventDefault()
        // Reset our form values
        this.paymentForm.cardNumber = ''
        this.paymentForm.expirationDate = ''
        this.paymentForm.cvv = ''
        // Trick to reset/clear native browser form validation state
        this.show = false
        this.$nextTick(() => {
          this.show = true
        })
      },
      hasAddresses: function () {
        const username = getUsernameCookie()
        AXIOS.get('/user/' + username + '/addresses')
        .then(response => {
        // JSON responses are automatically parsed.
          if (response.data == null) {
            return false
          } else {
            this.addresses = response.data
          }
        })
      },
      displayRadioValue: function () { 
        // console.log('test')
        // console.log(this.deliveryMethod)
        this.deliveryMethodChosen = true
        if (this.deliveryMethod === "delivery") {
          return true
        } else {
          return false
        }
      },
    }
  }
</script>

<style scoped>
  img {
  max-width: 60px;
  height: auto;
  margin-top: 0.3em;
  }
  .flex-1 {
    flex: 1 1 0%;
  }
  /* Chrome, Safari, Edge, Opera */
  input::-webkit-outer-spin-button,
  input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
  /* Firefox */
  input[type=number] {
    -moz-appearance: textfield;
  }
  .row {
    display: -ms-flexbox; /* IE10 */
    display: flex;
    -ms-flex-wrap: wrap; /* IE10 */
    flex-wrap: wrap;
    margin: 0 -16px;
  }

  .col-25 {
    -ms-flex: 25%; /* IE10 */
    flex: 25%;
  }

  .col-50 {
    -ms-flex: 50%; /* IE10 */
    flex: 50%;
  }

  .col-75 {
    -ms-flex: 75%; /* IE10 */
    flex: 75%;
  }

  .col-25,
  .col-50,
  .col-75 {
    padding: 0 16px;
  }

  .container {
    background-color: #f2f2f2;
    padding: 5px 20px 15px 20px;
    border: 1px solid lightgrey;
    border-radius: 3px;
  }

  input[type=text] {
    width: 100%;
    margin-bottom: 20px;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 3px;
  }

  label {
    margin-bottom: 10px;
    display: block;
  }

  .icon-container {
    margin-bottom: 20px;
    padding: 7px 0;
    font-size: 24px;
  }
  span.price {
    float: right;
    color: grey;
  }

  /* Responsive layout - when the screen is less than 800px wide, make the two columns stack on top of each other instead of next to each other (and change the direction - make the "cart" column go on top) */
  @media (max-width: 800px) {
    .row {
      flex-direction: column-reverse;
    }
    .col-25 {
      margin-bottom: 20px;
    }
  }
</style>