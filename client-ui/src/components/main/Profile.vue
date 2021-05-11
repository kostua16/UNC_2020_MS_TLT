<template>
  <v-main>
    <v-layout>
      <v-container>
        <v-row no-gutters>
          <v-col
              class="mt-4"
              cols="12"
              sm="7"
          >
            <domestic-card/>
          </v-col>
          <v-col
              cols="12"
              sm="5"
          >
            <v-card
                flat
                max-width="450"
                class="mx-auto"
            >
              <v-card-title>Выставленные счета</v-card-title>
              <v-card v-if="stateRequestPaymentsIsNotEmpty">
                <v-virtual-scroll
                    :bench="benched"
                    :items="getMyRequestPayments"
                    height="400px"
                    item-height="80px"
                >
                  <template v-slot:default="{ item }">
                    <v-list-item :key="item.paymentRequestId">
                      <v-list-item-action class="mr-md-2">
                        <payment-popup :paymentRequest="item"/>
                      </v-list-item-action>

                      <v-list-item-content>
                        <v-list-item-title class="mt-2">
                          Номер выставленного счёта: {{ item.paymentRequestId }}
                        </v-list-item-title>
                        <v-list-item-title>
                          Сумма к оплате: {{ item.amount }} руб.
                        </v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                    <v-divider></v-divider>
                  </template>
                </v-virtual-scroll>
              </v-card>
              <v-card
                  height="400px"
                  color="#f4f9fa"
                  flat
                  v-else
              >
                <v-img
                    height="400px"
                    width="450"
                    :src="require('@/assets/images/empty_card.png')"
                />
              </v-card>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-layout>
  </v-main>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import PaymentRequest from '@/models/bank/payment-request'
import PaymentPopup from "@/components/main/popup/PaymentPopup";
import DomesticCard from "@/components/passport/DomesticCard";

export default { // todo: skeleton loader
  name: "Profile",
  components: {PaymentPopup, DomesticCard},
  data() {
    return {
      paymentRequest: new PaymentRequest,
      benched: 0,
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
    },
    stateRequestPaymentsIsNotEmpty() {
      return this.GET_MY_REQUEST_PAYMENTS.length !== 0;
    }
  },
  created() {
    this.GET_MY_PAYMENT_REQUESTS_FROM_API()
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