<template>
  <!--  Сделать выбор периода дат -->
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
    ></v-date-picker>
    <v-btn
        block
        color="primary"
        @click="getPeriodTransactions"
    >
      Запросить чеки
    </v-btn>
    <transaction-item
        v-for="(transaction, index) in getTransactions"
        :key="`transaction.transactionId - ${index}`"
        :transaction="transaction"
    />
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
  },
  methods: {
    ...mapActions(['GET_PERIOD_TRANSACTION_FROM_API']),
    getPeriodTransactions() {
      console.log(this.range)
      this.period.startDate = this.range[0];
      this.period.endDate = this.range[1];
      console.log("Period: ", this.period.startDate, " -- ", this.period.endDate)
      // this.GET_PERIOD_TRANSACTION_FROM_API(this.period)
      //     .then(status => {
      //       if (status !== 200) {
      //         this.message = 'Не удалось загрузить чеки. Попробуйте позже.';
      //       }
      //     })
      this.startDate = '';
      this.endDate = '';
    }
  },
}
</script>

<style scoped>

</style>