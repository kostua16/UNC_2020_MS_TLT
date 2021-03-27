<template>
  <div>
    <p>{{paymentRequest.paymentRequestId}}</p>
    <p>{{paymentRequest.amount}}</p>
  </div>
</template>

<script>

export default {
  name: "PaymentRequestItem",
  props: ['paymentRequest'],

  created() {
    console.log(this.paymentRequest)
  }
}
</script>

<style scoped>

</style>