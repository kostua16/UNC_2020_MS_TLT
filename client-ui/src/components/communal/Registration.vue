<template>
  <v-main>
    <div class="mt-5">
      <v-autocomplete
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
          label="офис/квартира"
          v-model.trim="apartment"
          :error-messages="apartmentErrors"
          hide-details="auto"
          counter
          required
          @input="$v.apartment.$touch()"
          @blur="$v.apartment.$touch()"
      />
      <v-btn
          @click="addRegistration"
          class="mt-5 mb-8"
          block
          color="red"
          dark
      >
        Добавить прописку
      </v-btn>
      <v-snackbar
          :color="notificationColor"
          v-model="snackbar"
          :timeout="timeout"
          top
          width="auto"
      >
        {{ notification }}
      </v-snackbar>
    </div>
  </v-main>
</template>

<script>
import CommunalService from '@/services/communal/communal-service'
import Registration from "@/models/communal/registration";
import {maxLength, minLength, required} from "vuelidate/lib/validators";
import {mapActions} from "vuex";
import regions from "@/models/list/region-list";

export default {
  name: "Registration",
  data() {
    return {
      region: '',
      city: '',
      street: '',
      house: '',
      apartment: '',
      registration: new Registration(),
      snackbar: false,
      notification: '',
      timeout: 2500,
      notificationColor: '',
      regions: regions
    }
  },
  validations: {
    region: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(40),
      alpha: val => /^[а-яё][а-яё-]+[ ]+[а-яё]+$|^(Севастополь)$/i.test(val)
    },
    city: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(40),
      alpha: val => /^[а-яё][а-яё-]*$/i.test(val)
    },
    street: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(40),
      alphaNum: val => /^[а-яё0-9 ]*$/i.test(val)
    },
    house: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(10),
      alphaNum: val => /^[1-9][0-9]*[а-яё]$|^[1-9][0-9]*$/i.test(val)
    },
    apartment: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(10),
      alphaNum: val => /^[1-9][0-9]*[а-яё]$|^[1-9][0-9]*$/i.test(val)
    },
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
    cityErrors() {
      const errors = []
      if (!this.$v.city.$dirty) return errors
      !this.$v.city.minLength && errors.push('Название города должно состоять из не менее, чем из 2 букв!')
      !this.$v.city.maxLength && errors.push('Название города должно состоять из не более, чем из 40 букв!')
      !this.$v.city.alpha && errors.push('Неверный формат города! Пример: "Санкт-Петербург", "Самара')
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
  },
  methods: {
    ...mapActions(['GET_REGISTRATIONS_FROM_API']),
    addRegistration() {
      if (this.$v.$invalid) {
        this.$v.$touch();
        return;
      }

      this.registration.region = this.region
      this.registration.city = this.city
      this.registration.street = this.street
      this.registration.house = this.house
      this.registration.apartment = this.apartment

      CommunalService.addRegistration(this.registration)
          .then(status => {
            if (status === 200) {
              this.notificationColor = 'green'
              this.notification = 'Прописка добавлена'
              this.snackbar = true
              setTimeout(() => this.$router.push('/profile'), 2500)
            } else if (status === 503) {
              this.serviceUnavailable()
            } else {
              this.notificationColor = 'red'
              this.notification = 'Не удалось добавить прописку'
              this.snackbar = true
              this.clear()
            }
          })
    },
    close() {
      this.dialog = false
      this.clear()
    },
    clear() {
      this.$v.$reset()
      this.region = ''
      this.city = ''
      this.street = ''
      this.house = ''
      this.apartment = ''
      this.registration.region = ''
      this.registration.city = ''
      this.registration.street = ''
      this.registration.house = ''
      this.registration.apartment = ''
    },
    serviceUnavailable() {
      this.notificationColor = 'red'
      this.notification = 'Сервис временно не доступен!'
      this.snackbar = true
      setTimeout(() => this.$router.push('/profile'), 2500)
    }
  },
  created() {
    this.GET_REGISTRATIONS_FROM_API()
        .then(status => {
          if (status === 503) {
            this.serviceUnavailable()
          }
        })
  }
}
</script>

<style scoped>

</style>