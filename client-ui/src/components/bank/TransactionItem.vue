<template>
  <v-card class="mx-5 my-5 cyan lighten-2" width="300px" height="160px">
    <v-row>
      <v-col>Чек №{{ transaction.transactionId }}</v-col>
    </v-row>
    <v-row >
      <v-col sm="6">Дата создания:</v-col>
      <v-col>{{ transaction.creationDate | moment }}</v-col>
    </v-row>
    <v-row>
      <v-col sm="6">Сумма:</v-col>
      <v-col class="font-weight-bold">{{ transaction.amount }} руб.</v-col>
    </v-row>
  </v-card>
</template>

<script>
import Moment from "moment";

export default {
  name: "TransactionItem",
  props: ['transaction'],
  filters: {
    moment(date) {
      Moment.locale('ru')
      return Moment(date).format('DD MMMM YYYY');
    }
  }
}
</script>

<style scoped>

</style>