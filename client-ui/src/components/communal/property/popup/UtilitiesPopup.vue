<!-- Компонент ввода показаний счётчиков для определённой недвижимости -->
<!-- Принимает метод отправления показаний на сервер (Всплывающая форма) -->

<template>
  <v-row justify="center">
    <v-dialog
        v-model="dialog"
        persistent
        max-width="450"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            color="primary"
            dark
            v-bind="attrs"
            v-on="on"
            icon
        >
          <v-icon>sticky_note_2</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="headline text-center">
          Введите показания со счётчиков
        </v-card-title>
        <v-text-field
            class="ml-5 mr-5"
            type="number"
            label="Холодная вода"
            v-model="coldWater"
            :error-messages="coldWaterErrors"
            hide-details="auto"
            @input="$v.coldWater.$touch()"
            @blur="$v.coldWater.$touch()"
        />
        <v-text-field
            class="ml-5 mr-5"
            type="number"
            label="Горячая вода"
            v-model="hotWater"
            :error-messages="hotWaterErrors"
            hide-details="auto"
            @input="$v.hotWater.$touch()"
            @blur="$v.hotWater.$touch()"
        />
        <v-text-field
            class="ml-5 mr-5"
            type="number"
            label="Электричество"
            v-model="electricity"
            :error-messages="electricityErrors"
            hide-details="auto"
            @input="$v.electricity.$touch()"
            @blur="$v.electricity.$touch()"
        />
        <v-card-actions class="mt-4">
          <v-spacer></v-spacer>
          <v-btn
              color="green darken-1"
              text
              @click="sendUtilities"
          >
            Отправить
          </v-btn>
          <v-btn
              color="green darken-1"
              text
              @click="closePopUp"
          >
            Отменить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar
        :color="notificationColor"
        v-model="snackbar"
        :timeout="timeout"
        top
        width="auto"
    >
      {{ notification }}
    </v-snackbar>
  </v-row>
</template>

<script>
import UtilitiesPayload from '@/models/communal/requests/utilities-payload'
import UtilityBillService from "@/services/communal/utility-bill-service";
import {between, integer, required} from "vuelidate/lib/validators";

export default {
  name: "UtilitiesPopup",
  props: ['propertyId'],
  data() {
    return {
      dialog: false,
      utilitiesPayload: new UtilitiesPayload,
      coldWater: '',
      hotWater: '',
      electricity: '',
      snackbar: false,
      notification: '',
      timeout: 2500,
      notificationColor: '',
    }
  },
  validations: {
    coldWater: {required, integer, between: between(1, 999999)},
    hotWater: {required, integer, between: between(1, 999999)},
    electricity: {required, integer, between: between(1, 999999)},
  },
  computed: {
    coldWaterErrors() {
      const errors = []
      if (!this.$v.coldWater.$dirty) return errors
      !this.$v.coldWater.between && errors.push('Диапозон значение 1-999999')
      !this.$v.coldWater.integer && errors.push('Введите число!')
      !this.$v.coldWater.required && errors.push('Это обязательное поле!')
      return errors
    },
    hotWaterErrors() {
      const errors = []
      if (!this.$v.hotWater.$dirty) return errors
      !this.$v.hotWater.between && errors.push('Диапозон значение 1-999999')
      !this.$v.hotWater.integer && errors.push('Введите число!')
      !this.$v.hotWater.required && errors.push('Это обязательное поле!')
      return errors
    },
    electricityErrors() {
      const errors = []
      if (!this.$v.electricity.$dirty) return errors
      !this.$v.electricity.between && errors.push('Диапозон значение 1-999999')
      !this.$v.electricity.integer && errors.push('Введите число!')
      !this.$v.electricity.required && errors.push('Это обязательное поле!')
      return errors
    },
  },
  methods: {
    sendUtilities() {
      if (this.$v.$invalid) {
        this.$v.$touch();
        return;
      }
      this.utilitiesPayload.propertyId = this.propertyId;
      this.utilitiesPayload.coldWater = this.coldWater;
      this.utilitiesPayload.hotWater = this.hotWater;
      this.utilitiesPayload.electricity = this.electricity;
      UtilityBillService.createPropertyTax(this.utilitiesPayload)
          .then(status => {
            if (status === 200) {
              this.notificationColor = 'green'
              this.notification = 'Показания отправлены!'
              this.snackbar = true
              setTimeout(() => this.dialog = false, this.timeout)
            } else {
              this.errorSnackbar()
            }
          })
          .catch(() => {
            this.errorSnackbar()
          })
      this.clear()
    },
    errorSnackbar() {
      this.notification = 'Произошла ошибка! Попробуйте позже.'
      this.notificationColor = 'red'
      this.snackbar = true
      setTimeout(() => this.dialog = false, this.timeout)
    },
    closePopUp() {
      this.clear()
      this.dialog = false
    },
    clear() {
      this.$v.$reset()
      this.utilitiesPayload = null
      this.coldWater = ''
      this.hotWater = ''
      this.electricity = ''
    }
  }
}
</script>

<style scoped>

</style>