<template>
  <v-container class="mt-10 mb-10">
    <v-row no-gutters>
      <v-col
          cols="12"
          sm="6"
      >
        <v-card-title>Name title</v-card-title>
        <v-card
            height="400px"
            class="pa-3"
            outlined
            tile
        >
          <v-list>
            <v-list-item>Что-то</v-list-item>
            <v-list-item>Что-то</v-list-item>
            <v-list-item>Что-то</v-list-item>
            <v-list-item>Что-то</v-list-item>
          </v-list>
        </v-card>
      </v-col>
      <v-col
      >
        <v-spacer></v-spacer>
      </v-col>
      <v-col
          cols="8"
          sm="4"
      >
        <v-card-title>Выставленные счета</v-card-title>
        <v-card
            height="400px"
            class="pa-2"
            outlined
            tile
        >
<!--          <v-virtual-scroll-->
<!--              height="300"-->
<!--              item-height="64"-->
<!--          >-->
            <payment-request-item
                v-for="(paymentRequest, index) in getMyRequestPayments"
                :key="`paymentRequest.paymentRequestId - ${index}`"
                :paymentRequest="paymentRequest"
            />
<!--          </v-virtual-scroll>-->
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col
          cols="4"
      >
        <router-link to="tax-all">Просмотреть налоги</router-link>
      </v-col>
      <v-col
          cols="4"
      >
        <v-spacer></v-spacer>
      </v-col>
      <v-col
          cols="4"
      >
        <router-link to="tax-all">Просмотреть налоги</router-link>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import PaymentRequest from '@/models/bank/payment-request'
import PaymentRequestItem from "@/components/main/PaymentRequestItem";

export default {
  name: "PersonalArea",
  components: {PaymentRequestItem},

  data() {
    return {
      paymentRequest: new PaymentRequest
    }
  },
  methods: {
    ...mapActions([
      'GET_MY_PAYMENT_REQUESTS_FROM_API'
    ]),
  },
  computed: {
    ...mapGetters(['GET_MY_REQUEST_PAYMENTS']),
    getMyRequestPayments() {
      return this.GET_MY_REQUEST_PAYMENTS;
    }
  },
  created() {
    this.GET_MY_PAYMENT_REQUESTS_FROM_API(111)
        .then(response => {
          if (response.data) {
            console.log('Request payments arrived!')
          }
        })
  },
}
</script>

<style scoped>

</style>