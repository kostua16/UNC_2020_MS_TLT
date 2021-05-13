<template>
  <v-main class="justify-center align-center text-center">
    <v-card-title
        class="text-break text-center"
        v-model="message"
    ></v-card-title>
    <v-date-picker
        full-width
        range
        v-model="range"
        @click="getPeriodTransactions"
        min="2020-12-01"
    ></v-date-picker>
    <v-btn
        block
        color="primary"
        @click="getPeriodTransactions"
        :disabled="isDisabled"
    >
      Запросить чеки
    </v-btn>
    <v-row justify="center" class="mt-10">
      <transaction-item
          v-for="(transaction, index) in getTransactions"
          :key="`transaction.transactionId - ${index}`"
          :transaction="transaction"
      />
    </v-row>
  </v-main>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import TransactionItem from "@/components/bank/TransactionItem";
import Transaction from "@/models/bank/transaction";

export default {
  name: "Transactions",
  components: {TransactionItem},
  data() {
    return {
      message: '',
      transaction: new Transaction(),
      range: ['', ''],
      period: {
        startDate: '',
        endDate: ''
      },
    }
  },
  computed: {
    ...mapGetters(['GET_TRANSACTIONS']),
    getTransactions() {
      return this.GET_TRANSACTIONS;
    },
    isDisabled() {
      return !(this.range[1]);
    },
    // getDate() {
    //   let date = new Date()
    //   return date.setDate(date.ge)
    // }
  },
  methods: {
    ...mapActions(['GET_PERIOD_TRANSACTION_FROM_API']),
    getPeriodTransactions() {
      if (this.range[0] <= this.range[1]) {
        this.period.startDate = this.range[0];
        this.period.endDate = this.range[1];
      } else {
        this.period.startDate = this.range[1];
        this.period.endDate = this.range[0];
      }
      this.GET_PERIOD_TRANSACTION_FROM_API(this.period)
          .then(status => {
            if (status !== 200) {
              this.message = 'Не удалось загрузить чеки. Попробуйте позже.';
            }
          })
      this.startDate = '';
      this.endDate = '';
      this.range = [];
    }
  },
}
</script>

<style scoped>

</style>