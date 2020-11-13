<template>
    <div class="root">
        <b-container class="artwork info shadow-lg p-4">
          <b-row no-gutters align-h="center">
            <b-col>
              <b-card header="Items in cart:">
                <b-list-group>
                <b-list-group-item href="#">Veronica</b-list-group-item>
                <b-list-group-item href="#">Cesar</b-list-group-item>
                </b-list-group>
                <p style="margin-top: 2em;"><strong>Total:   187$</strong></p>
              </b-card>
            </b-col>
          </b-row>
          <b-row no-gutters align-h="center" style="margin-top: 7em;">
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
          </b-row>
        </b-container>
    </div>
</template>

<script>
  export default {
    data () {
      return {
        form: {
          cardNumber: '',
          expiryDate: '',
          cvv: '',
          name: ''
        },
        show: true
      }
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

</style>