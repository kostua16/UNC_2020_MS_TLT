<template>
  <v-main>
    <v-container>
      <v-layout align-content-space-around justify-start column>
        <utilities-price-list-form :priceListAttr="utilitiesPriceList"/>

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
            <utilities-price-list-item
                v-for="(utilitiesPriceList, index) in filteredPriceList"
                :key="`utilitiesPriceList.id - ${index}`"
                :utilitiesPriceList="utilitiesPriceList"
                :editPriceLIst="editPriceLIst"
            />
        </v-row>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import UtilitiesPriceListForm from "@/components/communal/property/admin/price/utility/UtilitiesPriceListForm";
import UtilitiesPriceListItem from "@/components/communal/property/admin/price/utility/UtilitiesPriceListItem";
import UtilitiesPriceList from "@/models/communal/utilities-price-list";
import {mapActions, mapGetters} from 'vuex'

export default {
  name: "UtilitiesPriceList",
  components: {UtilitiesPriceListItem, UtilitiesPriceListForm},
  data() {
    return {
      search: '',
      utilitiesPriceList: new UtilitiesPriceList(),
    }
  },
  computed: {
    ...mapGetters(['SORTED_UTILITIES_PRICE_LIST']),
    filteredPriceList() {
      return this.SORTED_UTILITIES_PRICE_LIST.filter(pl => {
        return pl.region.toLowerCase().includes(this.search.toLowerCase())
      })
    }
  },
  methods: {
    ...mapActions(['GET_UTILITIES_PRICE_LIST_FROM_API']),
    editPriceLIst(priceLIst) {
      this.utilitiesPriceList = priceLIst;
    },
  },
  created() {
    this.GET_UTILITIES_PRICE_LIST_FROM_API()
        .then(response => {
          if (response.data) {
            console.log('Utilities Price List arrived!', response.data)
          }
        })
  }
}
</script>

<style scoped>

</style>