<template>
  <v-row justify="center">
    <v-dialog
        v-model="dialog"
        persistent
        max-width="450"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            fab
            large
            depressed
            color="green"
            icon
            v-bind="attrs"
            v-on="on"
        >
          <v-icon>payment</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="headline text-center">
          Информация о платеже
        </v-card-title>
        <v-card-text>Номер счёта: {{paymentRequest.paymentRequestId}}</v-card-text>
        <v-card-text>Сумма к оплате: {{paymentRequest.amount}} руб.</v-card-text>
        <v-card-text>Уникальный налоговый номер: {{paymentRequest.taxId}}</v-card-text>
        <v-card-actions class="mt-4">
          <v-spacer></v-spacer>
          <v-btn
              color="green darken-1"
              text
              @click="pay"
          >
            Оплатить
          </v-btn>
          <v-btn
              color="green darken-1"
              text
              @click="dialog = false"
          >
            Отменить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>

import {mapActions} from "vuex";

export default {
  name: "PaymentPopup",
  props: ['paymentRequest'],
  data() {
    return {
      dialog: false,
    }
  },
  methods: {
    ...mapActions(['PAY_PAYMENT_REQUEST_ACTION']),
    pay() {
      this.PAY_PAYMENT_REQUEST_ACTION(this.paymentRequest.paymentRequestId);
      this.dialog = false;
    }
  }
}
</script>

<style scoped>

</style>