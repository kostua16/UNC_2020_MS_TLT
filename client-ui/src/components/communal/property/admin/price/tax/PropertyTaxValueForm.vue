<template>
  <div>
    <h2 class="text-center">Прейскурант для рассчёта налога на недвижимость</h2>
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
        label="Введите стоимость 1 кв. м."
        v-model="pricePerSquareMeter"
        hide-details="auto"
        :error-messages="pricePerSquareMeterErrors"
        required
        @input="$v.pricePerSquareMeter.$touch()"
        @blur="$v.pricePerSquareMeter.$touch()"
    />
    <v-text-field
        type="number"
        label="Введите кадастровое значение, %"
        v-model="cadastralValue"
        hide-details="auto"
        :error-messages="cadastralValueErrors"
        required
        @input="$v.cadastralValue.$touch()"
        @blur="$v.cadastralValue.$touch()"
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
import PropertyTaxValue from "@/models/communal/property-tax-value";
import {mapActions} from "vuex";
import {maxLength, minLength, required, numeric, minValue, maxValue} from "vuelidate/lib/validators";
import regions from "@/models/list/region-list";

export default {
  name: "PropertyTaxValueForm",
  props: ['priceListAttr'],
  data() {
    return {
      snackbar: false,
      timeout: 3000,
      notificationColor: '',
      notificationText: '',
      propertyTaxValue: new PropertyTaxValue(),
      message: '',
      region: '',
      pricePerSquareMeter: '',
      cadastralValue: '',
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
    pricePerSquareMeter: {
      required,
      numeric,
      minValue: minValue(1)

    },
    cadastralValue: {
      required,
      numeric,
      minValue: minValue(1),
      maxValue: maxValue(100)
    },
  },
  watch: {
    priceListAttr(newPriceList) {
      this.region = newPriceList.region;
      this.pricePerSquareMeter = newPriceList.pricePerSquareMeter;
      this.cadastralValue = newPriceList.cadastralValue;
      this.propertyTaxValueId = newPriceList.propertyTaxValueId;
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
    pricePerSquareMeterErrors() {
      const errors = []
      if (!this.$v.pricePerSquareMeter.$dirty) return errors
      !this.$v.pricePerSquareMeter.minValue && errors.push('Минимальное стоимость 1 руб.')
      !this.$v.pricePerSquareMeter.numeric && errors.push('Это числовое поле!')
      !this.$v.pricePerSquareMeter.required && errors.push('Это обязательное поле!')
      return errors
    },
    cadastralValueErrors() {
      const errors = []
      if (!this.$v.cadastralValue.$dirty) return errors
      !this.$v.cadastralValue.minValue && errors.push('Минимальное значение 1%!')
      !this.$v.cadastralValue.maxValue && errors.push('Макимальное значение 100%!')
      !this.$v.cadastralValue.numeric && errors.push('Это числовое поле!')
      !this.$v.cadastralValue.required && errors.push('Это обязательное поле!')
      return errors
    },
  },
  methods: {
    ...mapActions(['ADD_PROPERTY_TAX_VALUE']),
    save() {
      if (this.$v.$invalid) {
        this.$v.$touch();
        return;
      }
      this.propertyTaxValue.region = this.region
      this.propertyTaxValue.pricePerSquareMeter = this.pricePerSquareMeter
      this.propertyTaxValue.cadastralValue = this.cadastralValue
      this.ADD_PROPERTY_TAX_VALUE(this.propertyTaxValue)
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
      this.cleanForm();
    },
    cleanForm() {
      this.$v.$reset()
      this.propertyTaxValue.region = ''
      this.propertyTaxValue.pricePerSquareMeter = ''
      this.propertyTaxValue.cadastralValue = ''
      this.propertyTaxValue.propertyTaxValueId = ''
      this.pricePerSquareMeter = ''
      this.cadastralValue = ''
      this.propertyTaxValueId = ''
    }
  }
}
</script>

<style scoped>
h3 {
  color: red;
}
</style>