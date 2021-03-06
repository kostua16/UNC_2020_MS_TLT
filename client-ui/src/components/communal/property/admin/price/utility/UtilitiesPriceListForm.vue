<template>
  <div>
    <h2 class="text-center">Прейскурант для рассчёта стоимости коммунальных услуг</h2>
    <v-autocomplete
        class="pt-5"
        type="text"
        label="Область"
        v-model.trim="region"
        :error-messages="regionErrors"
        hide-details="auto"
        counter
        required
        @input="$v.region.$touch()"
        @blur="$v.region.$touch()"
        :items="regions"
        dense
        flat
        no-data-text="Такого региона не найдено"
    ></v-autocomplete>
    <v-text-field
        type="number"
        label="Введите стоимость 1 куб. м. холодной воды"
        v-model="coldWaterPrice"
        hide-details="auto"
        :error-messages="coldWaterPriceErrors"
        required
        @input="$v.coldWaterPrice.$touch()"
        @blur="$v.coldWaterPrice.$touch()"
    />
    <v-text-field
        type="number"
        label="Введите стоимость 1 куб. м. горячей воды"
        v-model="hotWaterPrice"
        hide-details="auto"
        :error-messages="hotWaterPriceErrors"
        required
        @input="$v.hotWaterPrice.$touch()"
        @blur="$v.hotWaterPrice.$touch()"
    />
    <v-text-field
        type="number"
        label="Введите стоимость 1 КВТ холодной воды"
        v-model="electricityPrice"
        hide-details="auto"
        :error-messages="electricityPriceErrors"
        required
        @input="$v.electricityPrice.$touch()"
        @blur="$v.electricityPrice.$touch()"
    />
    <v-btn @click="save" class="mt-5 mb-8" block color="red" dark>Добавить прейскурант</v-btn>
    <v-snackbar
        v-model="snackbar"
        :timeout="timeout"
        :color="notificationColor"
        top
    >
      <h4 class="text-center">{{ notificationText }}</h4>
    </v-snackbar>
  </div>
</template>

<script>
import UtilitiesPriceList from "@/models/communal/utilities-price-list";
import {mapActions} from 'vuex';
import {maxLength, minLength, minValue, numeric, required} from "vuelidate/lib/validators";
import regions from "@/models/list/region-list";

export default {
  name: "UtilitiesPriceListForm",
  props: ['priceListAttr'],
  data() {
    return {
      snackbar: false,
      timeout: 3000,
      notificationColor: '',
      notificationText: '',
      utilitiesPriceList: new UtilitiesPriceList(),
      message: '',
      region: '',
      coldWaterPrice: '',
      hotWaterPrice: '',
      electricityPrice: '',
      regions: regions,
    }
  },
  validations: {
    region: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(40),
      alpha: val => /^[а-яё][а-яё-]+[ ]+[а-яё]+$|^(Севастополь)$/i.test(val)
    },
    coldWaterPrice: {
      required,
      numeric,
      minValue: minValue(1)
    },
    hotWaterPrice: {
      required,
      numeric,
      minValue: minValue(1)
    },
    electricityPrice: {
      required,
      numeric,
      minValue: minValue(1)
    },
  },
  watch: {
    priceListAttr(newPriceList) {
      this.region = newPriceList.region;
      this.coldWaterPrice = newPriceList.coldWaterPrice;
      this.hotWaterPrice = newPriceList.hotWaterPrice;
      this.electricityPrice = newPriceList.electricityPrice;
      this.utilitiesPriceListId = newPriceList.utilitiesPriceListId;
    }
  },
  computed: {
    regionErrors() {
      const errors = []
      if (!this.$v.region.$dirty) return errors
      !this.$v.region.minLength && errors.push('Название региона должно состоять из не менее, чем из 2 букв!')
      !this.$v.region.maxLength && errors.push('Название региона должно состоять из не более, чем из 40 букв!')
      !this.$v.region.alpha && errors.push('Неверный формат региона! Пример: "Московская область", "Ямало-Ненецкий округ"')
      !this.$v.region.required && errors.push('Это обязательное поле!')
      return errors
    },
    coldWaterPriceErrors() {
      const errors = []
      if (!this.$v.coldWaterPrice.$dirty) return errors
      !this.$v.coldWaterPrice.minValue && errors.push('Минимальное стоимость 1 руб.')
      !this.$v.coldWaterPrice.numeric && errors.push('Это числовое поле!')
      !this.$v.coldWaterPrice.required && errors.push('Это обязательное поле!')
      return errors
    },
    hotWaterPriceErrors() {
      const errors = []
      if (!this.$v.hotWaterPrice.$dirty) return errors
      !this.$v.hotWaterPrice.minValue && errors.push('Минимальное стоимость 1 руб.')
      !this.$v.hotWaterPrice.numeric && errors.push('Это числовое поле!')
      !this.$v.hotWaterPrice.required && errors.push('Это обязательное поле!')
      return errors
    },
    electricityPriceErrors() {
      const errors = []
      if (!this.$v.electricityPrice.$dirty) return errors
      !this.$v.electricityPrice.minValue && errors.push('Минимальное стоимость 1 руб.')
      !this.$v.electricityPrice.numeric && errors.push('Это числовое поле!')
      !this.$v.electricityPrice.required && errors.push('Это обязательное поле!')
      return errors
    },
  },
  methods: {
    ...mapActions(['ADD_UTILITIES_PRICE_LIST']),
    save() {
      if (this.$v.$invalid) {
        this.$v.$touch();
        return;
      }
      this.utilitiesPriceList.region = this.region
      this.utilitiesPriceList.coldWaterPrice = this.coldWaterPrice
      this.utilitiesPriceList.hotWaterPrice = this.hotWaterPrice
      this.utilitiesPriceList.electricityPrice = this.electricityPrice
      this.ADD_UTILITIES_PRICE_LIST(this.utilitiesPriceList)
          .then(status => {
            if (status === 200) {
              this.notificationColor = 'green'
              this.notificationText = 'Прескурант успешно добавлен';
              this.timeout = 2000
              this.snackbar = true
              this.cleanForm();
            } else {
              this.notificationColor = 'red'
              this.notificationText = 'Не удалось добавить прейскурант! Попробуйте позже'
              this.timeout = 3000
              this.snackbar = true
            }
          })
    },
    cleanForm() {
      this.$v.$reset()
      this.region = ''
      this.coldWaterPrice = ''
      this.hotWaterPrice = ''
      this.electricityPrice = ''
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