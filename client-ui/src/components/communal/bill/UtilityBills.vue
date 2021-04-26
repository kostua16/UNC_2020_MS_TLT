<template>
  <v-main>
    <v-list>
      <utility-bill-item
          v-for="(utilityBill, index) in GET_UTILITY_BILLS"
          :key="`utilityBill.utilityBillId - ${index}`"
          :utilityBill="utilityBill"
      />
    </v-list>
  </v-main>
</template>

<script>
import UtilityBill from "@/models/communal/utility-bill";
import UtilityBillItem from "@/components/communal/bill/UtilityBillItem";
import {mapActions, mapGetters} from "vuex";

export default {
  name: "UtilityBills",
  components: {UtilityBillItem},
  data() {
    return {
      utilityBill: new UtilityBill(),
    }
  },
  computed: {
    ...mapGetters(['GET_UTILITY_BILLS']),
  },
  methods: {
    ...mapActions(['GET_UTILITY_BILLS_FROM_API']),
  },
  created() {
    this.GET_UTILITY_BILLS_FROM_API()
        .then(status => {
          if (status !== 200) {
            // notification or redirect ???
          }
        })
  }
}
</script>

<style scoped>

</style>