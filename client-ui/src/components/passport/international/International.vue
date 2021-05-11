<template>
  <v-main>
    <v-layout row wrap align-center justify-center>
      <v-card
          flat
          width="650px"
      >
        <v-card-title>
          <span class="headline">Оформление заграничного паспорта</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col
                  cols="12"
                  sm="6"
                  md="5"
              >
                <v-text-field
                    v-model.trim="name"
                    :error-messages="nameErrors"
                    label="Имя"
                    counter
                    required
                    @input="$v.name.$touch()"
                    @blur="$v.name.$touch()"
                ></v-text-field>
              </v-col>
              <v-col
                  cols="12"
                  sm="6"
                  md="2"
              ></v-col>
              <v-col
                  cols="12"
                  sm="6"
                  md="5"
              >
                <v-text-field
                    v-model.trim="surname"
                    :error-messages="surnameErrors"
                    label="Фамилия"
                    counter
                    required
                    @input="$v.surname.$touch()"
                    @blur="$v.surname.$touch()"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col sm="6">
                <v-menu
                    ref="menu"
                    v-model="menu"
                    :close-on-content-click="false"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                        v-model="dateOfBirth"
                        :error-messages="dateOfBirthErrors"
                        label="Дата Рождения"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                        @input="$v.dateOfBirth.$touch()"
                        @blur="$v.dateOfBirth.$touch()"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                      ref="picker"
                      v-model="dateOfBirth"
                      :max="new Date().toISOString().substr(0, 10)"
                      min="1900-01-01"
                      @change="save"
                  ></v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="blue darken-1"
              text
              @click="clear"
          >
            Очистить
          </v-btn>
          <v-btn
              color="blue darken-1"
              text
              @click="registerDomestic"
          >
            Оформить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-layout>
    <v-snackbar
        :color="notificationColor"
        v-model="snackbar"
        :timeout="timeout"
        top
    >
      {{ notification }}
    </v-snackbar>
    <v-row>
      <international-card-item
          v-for="(international, index) in getInternationalPassports"
          :key="`international.internationalId - ${index}`"
          :international="international"
      />
    </v-row>
  </v-main>
</template>

<script>
import Citizen from "@/models/passport/citizen";
import {maxLength, minLength, required} from "vuelidate/lib/validators";
import {mapActions, mapGetters} from "vuex";
import International from "@/models/passport/international";
import InternationalCardItem from "@/components/passport/international/InternationalCardItem";

export default {
  name: "International",
  components: {InternationalCardItem},
  data() {
    return {
      citizen: new Citizen(),
      international: new International(),
      number: 0,
      menu: false,
      name: '',
      surname: '',
      dateOfBirth: null,
      snackbar: false,
      notification: '',
      timeout: 5000,
      notificationColor: '',
    }
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.$refs.picker.activePicker = 'YEAR'))
    },
  },
  validations: {
    name: {required, minLength: minLength(2), maxLength: maxLength(40), alpha: val => /^[а-яё]*$/i.test(val)},
    surname: {required, minLength: minLength(2), maxLength: maxLength(40), alpha: val => /^[а-яё]*$/i.test(val)},
    dateOfBirth: {required},
  },
  computed: {
    ...mapGetters(['GET_INTERNATIONAL_PASSPORTS']),
    getInternationalPassports() {
      return this.GET_INTERNATIONAL_PASSPORTS
    },
    nameErrors() {
      const errors = []
      if (!this.$v.name.$dirty) return errors
      !this.$v.name.minLength && errors.push('Имя должно состоять из не менее, чем из 2 символов!')
      !this.$v.name.maxLength && errors.push('Имя должно состоять из не более, чем из 20 символов!')
      !this.$v.name.alpha && errors.push('Имя должно состоять из букв русского алфавита!')
      !this.$v.name.required && errors.push('Это обязательное поле!')
      return errors
    },
    surnameErrors() {
      const errors = []
      if (!this.$v.surname.$dirty) return errors
      !this.$v.surname.minLength && errors.push('Фамилия должно состоять из не менее, чем из 2 символов!')
      !this.$v.surname.maxLength && errors.push('Фамилия должно состоять из не более, чем из 20 символов!')
      !this.$v.surname.alpha && errors.push('Фамилия должно состоять из букв русского алфавита!')
      !this.$v.surname.required && errors.push('Это обязательное поле!')
      return errors
    },
    dateOfBirthErrors() {
      const errors = []
      if (!this.$v.dateOfBirth.$dirty) return errors
      !this.$v.dateOfBirth.required && errors.push('Это обязательное поле!')
      return errors
    },
  },
  methods: {
    ...mapActions([
      'REGISTER_INTERNATIONAL_PASSPORT_ACTION',
      'GET_INTERNATIONAL_FROM_API'
    ]),
    registerDomestic() {
      if (this.$v.$invalid) {
        this.$v.$touch();
        return;
      }
      this.citizen.name = this.name
      this.citizen.surname = this.surname
      this.citizen.dateOfBirth = this.dateOfBirth
      this.REGISTER_INTERNATIONAL_PASSPORT_ACTION(this.citizen)
          .then(status => {
            if (status === 200) {
              this.notificationColor = 'green'
              this.notification = 'Заграничный паспорт оформлен! Вы будете перенаправлены в личный кабинет через '
              this.redirectTime = 3
              this.timeout = 3000
              this.snackbar = true
              this.clear()
            } else {
              this.notification = 'Произошла ошибка! Попробуйте позже.'
              this.notificationColor = 'red'
              this.snackbar = true
            }
          })
    },
    clear() {
      this.$v.$reset()
      this.name = ''
      this.surname = ''
      this.dateOfBirth = null
    },
    save(date) {
      this.$refs.menu.save(date)
    },
  },
  created() {
    this.GET_INTERNATIONAL_FROM_API()
        .then(response => {
          if (response.status !== 200) {
            // notification and redirect
          }
        })
  }
}
</script>

<style scoped>

</style>