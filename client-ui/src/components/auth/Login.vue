<template>
  <v-main>
    <v-layout row wrap align-center justify-center>
      <v-card
          flat
          class="mt-10 text-center text-xs-center"
          width="500"
          max-height="400"
      >
        <h3>{{ message }}</h3>
        <v-form name="form" @submit.prevent="handleLogin">
          <v-container>
            <v-row justify="center" class="mt-5">

              <v-col sm="9">
                <v-text-field
                    v-model.trim="username"
                    :error-messages="usernameErrors"
                    type="text"
                    label="Login"
                    counter
                    outlined
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
                    label="Password"
                    counter
                    outlined
                    @click:append="show = !show"
                    @input="$v.password.$touch()"
                    @blur="$v.password.$touch()"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-btn
                width="100px"
                rounded
                color="primary"
                dark
                @click="handleLogin"
            >
              <span>Login</span>
            </v-btn>
          </v-container>
        </v-form>
        <div class="mt-5">
          <router-link to="sign-up">Нету аккаута? Зарегестрируйтесь!</router-link>
        </div>
      </v-card>
    </v-layout>
  </v-main>
</template>

<script>
import {maxLength, minLength, required} from 'vuelidate/lib/validators'
import User from "@/models/auth/user";
import {mapGetters} from 'vuex'

export default {
  name: "Login",
  data() {
    return {
      username: '',
      password: '',
      user: new User('', ''),
      show: false,
      message: ''
    };
  },
  validations: {
    username: {required, minLength: minLength(5), maxLength: maxLength(20)},
    password: {required, minLength: minLength(5), maxLength: maxLength(20)},
  },
  computed: {
    ...mapGetters(['GET_USER_IS_ACTIVE']),
    loggedIn() {
      return this.GET_USER_IS_ACTIVE;
    },
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
  },
  created() {
    if (this.loggedIn) {
      this.$router.push('/profile');
    }
  },
  methods: {
    handleLogin() {
      if (this.$v.$invalid) {
        this.$v.$touch();
        return;
      }
      this.user.username = this.username
      this.user.password = this.password
      this.$store.dispatch('auth/LOGIN', this.user)
          .then(() => {
            this.$router.push('/profile');
          })
          .catch(() => {
            this.message = 'Неверный логин или пароль!'
          })
    }
  }
};
</script>

<style scoped>

label {
  display: block;
  margin-top: 10px;
}

h3 {
  color: red;
  font-family: "Arial Narrow";
  font-style: italic;
}
</style>