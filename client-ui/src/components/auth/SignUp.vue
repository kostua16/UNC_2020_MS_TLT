<template>
  <div>
    <v-form name="form" @submit.prevent="registration">

      <v-container>

        <v-row justify="center">
          <v-col cols="12" sm="8" md="4">
            <h3>
              <div class="error-message">{{ message }}</div>
            </h3>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="12" sm="8" md="4">
            <v-text-field
                v-model="user.phoneNumber"
                type="text"
                label="Phone Number"
                counter
                outlined
                :rules="[rules.required, rules.min]"
                hint="At least 6 characters"
                class="input-group--focused"
            ></v-text-field>
          </v-col>
        </v-row>

        <v-row justify="center">
          <v-col cols="12" sm="8" md="4">
            <v-text-field
                v-model="user.username"
                type="text"
                label="Логин"
                counter
                outlined

                :rules="[rules.required, rules.minData]"
                hint="At least 4 characters"
            ></v-text-field>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="12" sm="8" md="4"></v-col>
          <v-col cols="12" sm="8" md="4">
            <v-text-field
                v-model="user.password"
                :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                :type="show ? 'text' : 'password'"
                name="password"
                label="Password"
                counter
                outlined
                :rules="[rules.required, rules.minData]"
                hint="At least 4 characters"
                @click:append="show = !show"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-btn
            width="100px"
            rounded
            color="primary"
            dark
            @click="registration"
        >
          <span>Login</span>
        </v-btn>
        <v-btn @click="clear">clear</v-btn>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import User from '@/models/auth/user'
import AuthService from '@/services/auth/auth-service'

export default {
  name: "SignUp",
  data() {
    return {
      user: new User('', '', ''),
      successful: false,
      show: false,
      message: '', // Сообщение в которое возвращается тело ошибки при регистрации
      issFilled: false,
      rules: {
        required: value => !!value || 'Required.',
        min: v => v.length >= 6 || 'Min 6 characters',
        minData: v => v.length >= 4 || 'Min 4 characters'
      },
      number: 0
    };
  },
  methods: {
    clear() {
      this.user.phoneNumber = ''
      this.user.username = ''
      this.user.password = ''
      this.$refs.observer.reset()
    },
    input() {
    },
    registration() {
      AuthService.register(this.user)
          .then(response => {
            if (response.status === 200) {
              this.$router.push('/login');
            } else {
              // Убрать ошибку из консоли
              this.message = response.data
              console.log(this.message)
            }
          })
    }
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