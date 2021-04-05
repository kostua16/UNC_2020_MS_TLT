<template>
  <div>
    <v-card-title>Прейскурант для рассчёта стоимости коммунальных услуг</v-card-title>
    <h3>{{ message }}</h3>
    <v-text-field
        type="text"
        label="Введите название региона"
        v-model="utilitiesPriceList.region"
        hide-details="auto"
    />
    <v-text-field
        type="number"
        label="Введите стоимость 1 куб. м. холодной воды"
        v-model="utilitiesPriceList.coldWaterPrice"
        hide-details="auto"
    />
    <v-text-field
        type="number"
        label="Введите стоимость 1 куб. м. горячей воды"
        v-model="utilitiesPriceList.hotWaterPrice"
        hide-details="auto"
    />
    <v-text-field
        type="number"
        label="Введите стоимость 1 КВТ холодной воды"
        v-model="utilitiesPriceList.electricityPrice"
        hide-details="auto"
    />
    <v-btn @click="save" class="mt-5 mb-8" block color="red" dark>Добавить прейскурант</v-btn>
  </div>
</template>

<script>
import UtilitiesPriceList from "@/models/communal/utilities-price-list";
import {mapActions} from 'vuex';

export default {
  name: "UtilitiesPriceListForm",
  props: ['priceListAttr'],
  data() {
    return {
      utilitiesPriceList: new UtilitiesPriceList(),
      message: '',
    }
  },
  watch: {
    priceListAttr(newPriceList) {
      console.log("Price list from FORM COMPONENT: ", newPriceList)
      this.utilitiesPriceList.region = newPriceList.region;
      this.utilitiesPriceList.coldWaterPrice = newPriceList.coldWaterPrice;
      this.utilitiesPriceList.hotWaterPrice = newPriceList.hotWaterPrice;
      this.utilitiesPriceList.electricityPrice = newPriceList.electricityPrice;
      this.utilitiesPriceList.utilitiesPriceListId = newPriceList.utilitiesPriceListId;
    }
  },
  methods: {
    ...mapActions(['ADD_UTILITIES_PRICE_LIST']),
    save() {
      // const priceList = {
      //   utilitiesPriceListId: this.utilityBillPriceList.utilitiesPriceListId,
      //   region: this.utilityBillPriceList.region,
      //   coldWaterPrice: this.utilityBillPriceList.coldWaterPrice,
      //   hotWaterPrice: this.utilityBillPriceList.hotWaterPrice,
      //   electricityPrice: this.utilityBillPriceList.electricityPrice
      // }
      this.ADD_UTILITIES_PRICE_LIST(this.utilitiesPriceList)
          .then(status => {
            if (status === 200) {
              this.message = 'Прескурант успешно добавлен.';
            } else {
              this.message = 'Не удалось добавить прейскурант! Попробуйте позже.'
            }
          })
      this.cleanForm();
    },
    cleanForm() {
      this.utilitiesPriceList.region = ''
      this.utilitiesPriceList.coldWaterPrice = ''
      this.utilitiesPriceList.hotWaterPrice = ''
      this.utilitiesPriceList.electricityPrice = ''
      this.utilitiesPriceList.utilitiesPriceListId = ''
    }
  }
}
</script>

<style scoped>
h3 {
  color: red;
}

</style>