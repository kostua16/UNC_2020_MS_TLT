<template>
  <v-main>
    <v-layout>
      <v-container>
        <v-row no-gutters>
          <v-col
              cols="12"
              sm="7"
          >
            <domestic-card/>
          </v-col>
          <v-col
              cols="12"
              sm="5"
          >
            <!--      elevation - делает обводку вокруг карточки  -->
            <v-card
                flat
                max-width="450"
                class="mx-auto"
            >
              <v-card-title>Выставленные счета</v-card-title>
              <v-card>
                <v-virtual-scroll
                    :bench="benched"
                    :items="getMyRequestPayments"
                    height="400px"
                    item-height="80px"
                >
                  <template v-slot:default="{ item }">
                    <v-list-item :key="item.paymentRequestId">
                      <v-list-item-action class="mr-md-2">
                        <!--                  <v-btn-->
                        <!--                      fab-->
                        <!--                      large-->
                        <!--                      depressed-->
                        <!--                      color="green"-->
                        <!--                      class="text-center"-->
                        <!--                      icon-->
                        <!--                  >-->
                        <!--                    <v-icon>payment</v-icon>-->
                        <!--                  </v-btn>-->
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
            </v-card>
          </v-col>
        </v-row>
        <v-row>
          <v-col
              cols="4"
          >
            <router-link to="/tax/get-all">Просмотреть налоги</router-link>
          </v-col>
          <v-col
              cols="4"
          >
            <v-spacer></v-spacer>
          </v-col>
          <v-col
              cols="4"
          >
            <router-link to="/tax/get-all">Просмотреть налоги</router-link>
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

export default {
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