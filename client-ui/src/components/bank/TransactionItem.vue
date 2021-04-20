<template>
  <v-col sm="4">
    <v-card>
      <v-card-text>
        {{ transaction.transactionId}}
      </v-card-text>
      <v-card-text>
        {{ transaction.creationDate}}
      </v-card-text>
      <v-card-text>
        {{ transaction.amount}}
      </v-card-text>
      <v-card-text>
        {{ transaction.paymentRequestId}}
      </v-card-text>
    </v-card>
  </v-col>
</template>

<script>
export default {
  name: "TransactionItem",
  props: ['transaction'],
}
</script>

<style scoped>

</style>