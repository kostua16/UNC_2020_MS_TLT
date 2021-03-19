<template>
  <div>
    <v-btn
        class="mt-10 mb-8"
        block
        color="red"
        @click="getTaxes"
    >
      GET TAXES
    </v-btn>
  </div>
</template>

<script>

import Tax from '@/models/tax/tax'
import TaxService from '@/services/tax/tax-service'

export default {
  name: "Tax",
  data() {
    return {
      taxes: '',
      tax: new Tax()
    }
  },
  methods: {
    getTaxes() {
      TaxService.getALlTaxes().then(
          response => {
            this.taxes = response
          }
      );
      console.log()
    }
  }
}
</script>

<style scoped>

</style>