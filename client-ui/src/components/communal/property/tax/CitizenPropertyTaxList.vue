<template>
  <v-main>
    <v-data-table
        :headers="headers"
        :items="getPropertyTax"
        item-key="utilityBillId"
        :items-per-page="10"
        class="elevation-0"
        no-data-text="Налоги не найдены!"
    >
      <template v-slot:top>
        <v-toolbar
            flat
        >
          <v-toolbar-title>Мои налоги на едвижимость</v-toolbar-title>
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
            icon color="green"
            @click="payPropertyTax(item)"
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
import PropertyTax from "@/models/communal/property-tax";
import {mapActions, mapGetters} from "vuex";
import Moment from "moment";

export default {
  name: "CitizenPropertyTaxList",
  data() {
    return {
      snackbar: false,
      timeout: 3000,
      notificationColor: '',
      notificationText: '',
      propertyTax: new PropertyTax(),
      headers: [
        {
          text: 'УН Налога',
          align: 'start',
          value: 'propertyTaxId',
        },
        {text: 'Сумма', value: 'taxAmount'},
        {text: 'Статус оплаты ', value: 'isPaid'},
        {text: 'Дата', value: 'date'}, //  | this.moment
        {text: 'УН недвижимости', value: 'paymentRequestId'},
        {
          text: 'Оплатить',
          sortable: false,
          value: 'actions'
        },
      ],
    }
  },
  computed: {
    ...mapGetters(['GET_CITIZEN_PROPERTY_TAXES']),
    getPropertyTax() {
      return this.GET_CITIZEN_PROPERTY_TAXES;
    },
  },
  methods: {
    ...mapActions([
      'GET_CITIZEN_PROPERTY_TAXES_FROM_API',
      'CHANGE_PROPERTY_TAX_PAYMENT_STATUS_ACTION',
      'PAY_PAYMENT_REQUEST_ACTION'
    ]),
    payPropertyTax(item) {
      this.PAY_PAYMENT_REQUEST_ACTION(item.paymentRequestId)
          .then(status => {
            if (status === 200) {
              this.CHANGE_PROPERTY_TAX_PAYMENT_STATUS_ACTION(item.propertyTaxId)
                  .then(utilityStatus => {
                    if (utilityStatus === 200) {
                      this.notificationColor = 'green'
                      this.notificationText = 'Налог на недвижимость успешно оплачен!'
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
    },
  },
  filters: {
    moment(date) {
      Moment.locale('ru')
      return Moment(date).format('DD MMMM YYYY');
    }
  },
  created() {
    this.GET_CITIZEN_PROPERTY_TAXES_FROM_API()
        .then(status => {
          if (status !== 200) {
            this.notificationColor = 'red'
            this.notificationText = 'Сервис врменно не доступен. Попробуйте позже.'
            this.timeout = 4000
            setTimeout(() => this.$router.push('/main'), 4000, this.snackbar = true)
          }
        })
  }
}
</script>

<style scoped>

</style>