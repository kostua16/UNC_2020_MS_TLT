<template>
  <v-main>
    <v-container>
      <v-layout align-content-space-around justify-start column>
        <property-tax-value-form :priceListAttr="propertyTaxValue"/>

        <v-col cols="12" sm="4">
          <v-text-field
              class="pa-3"
              v-model="search"
              append-icon="mdi-magnify"
              label="Введите название региона"
              single-line
              hide-details
          ></v-text-field>
        </v-col>

        <v-row justify="center">
          <property-tax-value-item
              v-for="(propertyTaxValue, index) in filteredPriceList"
              :key="`propertyTaxValue.id - ${index}`"
              :propertyTaxValue="propertyTaxValue"
              :editPriceLIst="editPriceLIst"
          />
        </v-row>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import PropertyTaxValueForm from "@/components/communal/property/admin/price/tax/PropertyTaxValueForm";
import PropertyTaxValueItem from "@/components/communal/property/admin/price/tax/PropertyTaxValueItem";
import PropertyTaxValue from "@/models/communal/property-tax-value";
import {mapActions, mapGetters} from "vuex";

export default {
  name: "PropertyTaxValue",
  components: {PropertyTaxValueForm, PropertyTaxValueItem},
  data() {
    return {
      search: '',
      propertyTaxValue: new PropertyTaxValue(),
    }
  },
  computed: {
    ...mapGetters(['SORTED_PROPERTY_TAX_VALUE']),
    filteredPriceList() {
      return this.SORTED_PROPERTY_TAX_VALUE.filter(pl => {
        return pl.region.toLowerCase().includes(this.search.toLowerCase())
      })
    }
  },
  methods: {
    ...mapActions(['GET_PROPERTY_TAX_VALUE_FROM_API']),
    editPriceLIst(priceLIst) {
      this.propertyTaxValue = priceLIst;
    },
  },
  created() {
    this.GET_PROPERTY_TAX_VALUE_FROM_API()
        .then(response => {
          if (response.data) {
            console.log('Property Tax Value arrived!', response.data)
          }
        })
  }

}
</script>

<style scoped>

</style>