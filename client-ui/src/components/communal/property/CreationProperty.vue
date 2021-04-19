<template>
  <v-container>
    <v-text-field
        type="text"
        label="Область"
        v-model.trim="region"
        :error-messages="regionErrors"
        hide-details="auto"
        counter
        required
        @input="$v.region.$touch()"
        @blur="$v.region.$touch()"
    />
    <v-text-field
        type="text"
        label="Населённый пункт"
        v-model.trim="city"
        :error-messages="cityErrors"
        hide-details="auto"
        counter
        required
        @input="$v.city.$touch()"
        @blur="$v.city.$touch()"
    />
    <v-text-field
        type="text"
        label="Улица"
        v-model.trim="street"
        :error-messages="streetErrors"
        hide-details="auto"
        counter
        required
        @input="$v.street.$touch()"
        @blur="$v.street.$touch()"
    />
    <v-text-field
        type="text"
        label="Дом"
        v-model.trim="house"
        :error-messages="houseErrors"
        hide-details="auto"
        counter
        required
        @input="$v.house.$touch()"
        @blur="$v.house.$touch()"
    />
    <v-text-field
        type="text"
        label="Офис/Квартира"
        v-model.trim="apartment"
        :error-messages="apartmentErrors"
        hide-details="auto"
        counter
        required
        @input="$v.apartment.$touch()"
        @blur="$v.apartment.$touch()"
    />
    <v-text-field
        type="number"
        label="Площадь квартиры"
        v-model="apartmentSize"
        :error-messages="apartmentSizeErrors"
        hide-details="auto"
        required
        @input="$v.apartmentSize.$touch()"
        @blur="$v.apartmentSize.$touch()"
    />
    <v-btn
        @click="addProperty"
        class="mt-5 mb-8"
        block color="red"
        dark
    >
      Создать недвижимость
    </v-btn>
  </v-container>
</template>

<script>
import CreationProperty from '@/models/communal/requests/creation-property'
import {maxLength, minLength, minValue, numeric, required} from "vuelidate/lib/validators";
import {mapActions} from "vuex";

export default {
  name: "CreationProperty",
  props: ['propertyAttr'],
  data() {
    return {
      creationProperty: new CreationProperty(0),
      region: '',
      city: '',
      street: '',
      house: '',
      apartment: '',
      apartmentSize: '',
    }
  },
  validations: {
    region: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(40),
      alpha: val => /^[а-яё]*$/i.test(val)
    },
    city: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(40),
      alpha: val => /^[а-яё]*$/i.test(val)
    },
    street: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(40),
      alphaNum: val => /^[а-яё0-9 ]*$/i.test(val) // пробел после '9' нужен!
    },
    house: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(10),
      alphaNum: val => /^[1-9]*[а-яё]$|^[1-9]*$/i.test(val)
    },
    apartment: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(10),
      alphaNum: val => /^[1-9]*[а-яё]$|^[1-9]*$/i.test(val)
    },
    apartmentSize: {required, numeric, minValue: minValue(10)},
  },
  computed: {
    regionErrors() {
      const errors = []
      if (!this.$v.region.$dirty) return errors
      !this.$v.region.minLength && errors.push('Название региона должно состоять из не менее, чем из 2 букв!')
      !this.$v.region.maxLength && errors.push('Название региона должно состоять из не более, чем из 40 букв!')
      !this.$v.region.alpha && errors.push('Название региона должно состоять из букв')
      !this.$v.region.required && errors.push('Это обязательное поле!')
      return errors
    },
    cityErrors() {
      const errors = []
      if (!this.$v.city.$dirty) return errors
      !this.$v.city.minLength && errors.push('Название города должно состоять из не менее, чем из 2 букв!')
      !this.$v.city.maxLength && errors.push('Название города должно состоять из не более, чем из 40 букв!')
      !this.$v.city.alpha && errors.push('Название города должно состоять из букв.')
      !this.$v.city.required && errors.push('Это обязательное поле!')
      return errors
    },
    streetErrors() {
      const errors = []
      if (!this.$v.street.$dirty) return errors
      !this.$v.street.minLength && errors.push('Название города должно состоять из не менее, чем из 2 символов!')
      !this.$v.street.maxLength && errors.push('Название города должно состоять из не более, чем из 40 символов!')
      !this.$v.street.alphaNum && errors.push('Название города должно состоять из букв и цифр. Пример: "40 лет Победы"')
      !this.$v.street.required && errors.push('Это обязательное поле!')
      return errors
    },
    houseErrors() {
      const errors = []
      if (!this.$v.house.$dirty) return errors
      !this.$v.house.minLength && errors.push('Минимум 1 символ!')
      !this.$v.house.maxLength && errors.push('Максимум 10 символов!')
      !this.$v.house.alphaNum && errors.push('Неверный формат данных. Пример: "125", "23Г"')
      !this.$v.house.required && errors.push('Это обязательное поле!')
      return errors
    },
    apartmentErrors() {
      const errors = []
      if (!this.$v.apartment.$dirty) return errors
      !this.$v.apartment.minLength && errors.push('Минимум 1 символ!')
      !this.$v.apartment.maxLength && errors.push('Максимум 10 символов!')
      !this.$v.apartment.alphaNum && errors.push('Неверный формат данных. Пример: "125", "23Г"')
      !this.$v.apartment.required && errors.push('Это обязательное поле!')
      return errors
    },
    apartmentSizeErrors() {
      const errors = []
      if (!this.$v.apartmentSize.$dirty) return errors
      !this.$v.apartmentSize.minValue && errors.push('Минимальная площадь квартиры 10 кв.м.')
      !this.$v.apartmentSize.numeric && errors.push('Это целочисленное поле!')
      !this.$v.apartmentSize.required && errors.push('Это обязательное поле!')
      return errors
    },
  },
  methods: {
    ...mapActions(['ADD_PROPERTY_ACTION']),
    addProperty() {
      if (this.$v.$invalid) {
        this.$v.$touch();
        return;
      }
      this.creationProperty.region = this.region
      this.creationProperty.city = this.city
      this.creationProperty.street = this.street
      this.creationProperty.house = this.house
      this.creationProperty.apartment = this.apartment
      this.creationProperty.apartmentSize = this.apartmentSize

      this.ADD_PROPERTY_ACTION(this.creationProperty)
          .then(status => {
            if (status === 200) {
              this.clear()
              // всплывающее уведомление
            } else {

            }
          })
    },
    clear() {
      this.$v.$reset()
      this.region = ''
      this.city = ''
      this.street = ''
      this.house = ''
      this.apartment = ''
      this.apartmentSize = ''
      this.creationProperty.region = ''
      this.creationProperty.city = ''
      this.creationProperty.street = ''
      this.creationProperty.house = ''
      this.creationProperty.apartment = ''
      this.creationProperty.apartmentSize = 0
    }
  }
}
</script>

<style scoped>

</style>