<template>
  <v-main>
    <v-layout row wrap align-center justify-center>
      <v-card
          flat
          class="mt-10 text-center text-xs-center"
          width="600"
          max-height="400"
      >
        <v-form
            justify="center"
            name="form"
            class="text-center form_width"
            @submit.prevent="registration"
        >
          <v-container>
            <v-row justify="center">
              <v-col sm="9">
                <h3>
                  <div class="error-message">{{ message }}</div>
                </h3>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col sm="9">
                <v-text-field
                    v-model.trim="username"
                    :error-messages="usernameErrors"
                    type="text"
                    label="Логин"
                    counter
                    outlined
                    required
                    @input="$v.username.$touch()"
                    @blur="$v.username.$touch()"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col sm="9">
                <v-text-field
                    v-model.trim="password"
                    :error-messages="passwordErrors"
                    :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show ? 'text' : 'password'"
                    name="password"
                    label="Пароль"
                    counter
                    outlined
                    required
                    @click:append="show = !show"
                    @input="$v.password.$touch()"
                    @blur="$v.password.$touch()"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col sm="9">
                <v-text-field
                    v-model.trim="email"
                    :error-messages="emailErrors"
                    type="text"
                    label="Электронная почта"
                    counter
                    outlined
                    required
                    @input="$v.email.$touch()"
                    @blur="$v.email.$touch()"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-btn
                rounded
                color="primary"
                dark
                @click="registration"
            >
              <span>Зарегестрироваться</span>
            </v-btn>
            <v-btn @click="clear">clear</v-btn>
          </v-container>
        </v-form>
      </v-card>
    </v-layout>
    <v-snackbar
        :color="notificationColor"
        v-model="snackbar"
        :timeout="timeout"
        top
    >
      {{ notification }} {{ redirectTime }}

      <template v-slot:action="{ attrs }">
        <v-btn
            color="white"
            text
            v-bind="attrs"
            @click="snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-main>
</template>

<script>
import {email, maxLength, minLength, required} from 'vuelidate/lib/validators'
import {validationMixin} from "vuelidate";
import RegistrationData from "@/models/auth/registration-data";
import AuthService from '@/services/auth/auth-service'


export default {
  name: "SignUp",
  mixins: [validationMixin],
  data() {
    return {
      username: '',
      password: '',
      email: '',
      regData: new RegistrationData,
      successful: false,
      show: false,
      message: '', // Сообщение в которое возвращается тело ошибки при регистрации
      snackbar: false,
      notification: '',
      timeout: 5000,
      notificationColor: '',
      redirectTime: '',
    };
  },
  validations: {
    username: {required, minLength: minLength(5), maxLength: maxLength(20)},
    password: {required, minLength: minLength(5), maxLength: maxLength(20)},
    email: {required, email, minLength: minLength(10)},
  },
  computed: {
    usernameErrors() {
      const errors = []
      if (!this.$v.username.$dirty) return errors
      !this.$v.username.minLength && errors.push('Имя пользователь должно состоять из не менее, чем из 5 символов!')
      !this.$v.username.maxLength && errors.push('Имя пользователь должно состоять из не более, чем из 20 символов!')
      !this.$v.username.required && errors.push('Это обязательное поле!')
      return errors
    },
    passwordErrors() {
      const errors = []
      if (!this.$v.password.$dirty) return errors
      !this.$v.password.minLength && errors.push('Пароль должно состоять из не менее, чем из 5 символов!')
      !this.$v.password.maxLength && errors.push('Пароль должно состоять из не более, чем из 20 символов!')
      !this.$v.password.required && errors.push('Это обязательное поле!')
      return errors
    },
    emailErrors() {
      const errors = []
      if (!this.$v.email.$dirty) return errors
      !this.$v.email.email && errors.push('Должен быть корректный адрес электронной почты!')
      !this.$v.email.required && errors.push('Это обязательное поле!')
      return errors
    },
  },
  methods: {
    registration() {
      if (this.$v.$invalid) {
        this.$v.$touch();
        return;
      }
      this.regData.username = this.username
      this.regData.password = this.password
      this.regData.email = this.email
      AuthService.register(this.regData)
          .then(status => {
            if (status === 200) {
              this.notificationColor = 'green'
              this.notification = 'Вы успешно зарегистрировались. Перенапрявляем вас на страницу авторизации...'
              this.redirectTime = 3
              this.timeout = 3000
              this.snackbar = true
              setTimeout(() => this.$router.push('/login'), 3000, this.interval())
            } else {
              this.notification = 'Ошибка регистрации!'
              this.notificationColor = 'red'
              this.snackbar = true
            }
          })
    },
    interval() {
      setInterval(() => {
        if (this.redirectTime === 0) {
          (this.redirectTime = 0)
        }
        this.redirectTime -= 1
      }, 950);
    },
    clear() {
      this.$v.$reset()
      this.username = ''
      this.password = ''
      this.email = ''
      this.regData.username = ''
      this.regData.password = ''
      this.regData.email = ''
    },
  }
}
</script>

<style scoped>
h3 {
  color: orangered;
  font-family: "Arial Narrow";
  font-style: italic;
  text-align: center;
  font-size: 20px;
}
</style>