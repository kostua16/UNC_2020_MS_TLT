<template>
  <div>
    <v-card-title>Прейскурант для рассчёта налога на недвижимость</v-card-title>
    <h3>{{ message }}</h3>
    <v-text-field
        type="text"
        label="Введите название региона"
        v-model="propertyTaxValue.region"
        hide-details="auto"
    />
    <v-text-field
        type="number"
        label="Введите стоимость 1 кв. м."
        v-model="propertyTaxValue.pricePerSquareMeter"
        hide-details="auto"
    />
    <v-text-field
        type="number"
        label="Введите кадастровое значение"
        v-model="propertyTaxValue.cadastralValue"
        hide-details="auto"
    />
    <v-btn @click="save" class="mt-5 mb-8" block color="red" dark>Добавить прейскурант</v-btn>
  </div>
</template>

<script>
import PropertyTaxValue from "@/models/communal/property-tax-value";
import {mapActions} from "vuex";

export default {
  name: "PropertyTaxValueForm",
  props: ['priceListAttr'],
  data() {
    return {
      propertyTaxValue: new PropertyTaxValue(),
      message: '',
    }
  },
  watch: {
    priceListAttr(newPriceList) {
      console.log("Property Tax Value from FORM COMPONENT: ", newPriceList)
      this.propertyTaxValue.region = newPriceList.region;
      this.propertyTaxValue.pricePerSquareMeter = newPriceList.pricePerSquareMeter;
      this.propertyTaxValue.cadastralValue = newPriceList.cadastralValue;
      this.propertyTaxValue.propertyTaxValueId = newPriceList.propertyTaxValueId;
    }
  },
  methods: {
    ...mapActions(['ADD_PROPERTY_TAX_VALUE']),
    save() {
      this.ADD_PROPERTY_TAX_VALUE(this.propertyTaxValue)
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
      this.propertyTaxValue.region = ''
      this.propertyTaxValue.coldWaterPrice = ''
      this.propertyTaxValue.hotWaterPrice = ''
      this.propertyTaxValue.electricityPrice = ''
      this.propertyTaxValue.utilitiesPriceListId = ''
    }
  }
}
</script>

<style scoped>
h3 {
  color: red;
}
</style>