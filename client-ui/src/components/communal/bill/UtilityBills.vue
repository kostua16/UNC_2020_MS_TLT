<template>
  <v-main>
    <v-data-table
        :headers="headers"
        :items="getUtilityBills"
        item-key="utilityBillId"
        :items-per-page="10"
        class="elevation-0"
        no-data-text="Квитанций не найдено!"
    >
      <template v-slot:top>
        <v-toolbar
            flat
        >
          <v-toolbar-title>Мои коммунальные квитанции</v-toolbar-title>
          <v-divider
              class="mx-4"
              inset
              vertical
          ></v-divider>
          <v-spacer></v-spacer>
        </v-toolbar>
      </template>
      <template v-slot:item.isPaid="{ item }">
        <v-simple-checkbox
            v-model="item.isPaid"
            disabled
        ></v-simple-checkbox>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-btn
            v-if="!item.isPaid"
            icon
            color="green"
            @click="payUtilityBill(item)"
        >
          <v-icon>payment</v-icon>
        </v-btn>
      </template>
    </v-data-table>
    <v-snackbar
        v-model="snackbar"
        :timeout="timeout"
        :color="notificationColor"
        top
    >
      {{ notificationText }}

      <template v-slot:action="{ attrs }">
        <v-btn
            color="blue"
            text
            v-bind="attrs"
            @click="snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-main>
</template>

<script>
import UtilityBill from "@/models/communal/utility-bill";
import {mapActions, mapGetters} from "vuex";
import PaymentRequest from "@/models/bank/payment-request"

export default {
  name: "UtilityBills",
  data() {
    return {
      snackbar: false,
      timeout: 3000,
      notificationColor: '',
      notificationText: '',
      utilityBill: new UtilityBill(),
      utilityBills: [],
      paymentRequest: new PaymentRequest(),
      headers: [
        {text: 'УН Квитанции', align: 'start', value: 'utilityBillId'},
        {text: 'Дата создания', align: 'center', value: 'date'},
        {text: 'Статус оплаты', value: 'isPaid'},
        {text: 'Кол-во Х. воды, куб. м. ', value: 'coldWater'},
        {text: 'Кол-во Г. воды, куб. м.', value: 'hotWater'},
        {text: 'Электропотребление, кВт', value: 'electricity'},
        {text: 'За Х. воду', value: 'coldWaterAmount'},
        {text: 'За Г. воду', value: 'hotWaterAmount'},
        {text: 'За электричество', value: 'electricityAmount'},
        {text: 'Итого', value: 'utilityAmount'},
        {
          text: 'Оплатить',
          sortable: false,
          value: 'actions'
        },
      ],
    }
  },
  computed: {
    ...mapGetters(['GET_UTILITY_BILLS']),
    getUtilityBills() {
      // filter
      return this.GET_UTILITY_BILLS
    }
  },
  methods: {
    ...mapActions([
      'GET_UTILITY_BILLS_FROM_API',
      'CHANGE_UTILITY_BILL_PAYMENT_STATUS',
      'PAY_PAYMENT_REQUEST_ACTION'
    ]),
    payUtilityBill(item) {
      this.PAY_PAYMENT_REQUEST_ACTION(item.paymentRequestId)
          .then(status => {
            if (status === 200) {
              this.CHANGE_UTILITY_BILL_PAYMENT_STATUS(item)
                  .then(utilityStatus => {
                    if (utilityStatus === 200) {
                      this.notificationColor = 'green'
                      this.notificationText = 'Квитанция успешно оплачена!'
                      this.timeout = 2000
                      this.snackbar = true
                    } else {
                      this.notificationError()
                    }
                  })
            } else {
              this.notificationError()
            }
          })
    },
    notificationError() {
      this.notificationColor = 'red'
      this.notificationText = 'Не удалось оплатить квитанцию. Попробуйте позже'
      this.timeout = 3000
      this.snackbar = true
    }
  },
  created() {
    this.GET_UTILITY_BILLS_FROM_API()
        .then(status => {
          if (status !== 200) {
            this.notificationColor = 'red'
            this.notificationText = 'Сервис врменно не доступен. Попробуйте позже.'
            this.timeout = 4000
            this.snackbar = true
          }
        })
  }
}
</script>

<style scoped>

</style>