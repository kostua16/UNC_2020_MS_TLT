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
                  v-model="user.username"
                  type="text"
                  label="Login"
                  counter
                  outlined
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row justify="center">
            <v-col sm="9">
              <v-text-field
                  v-model="user.password"
                  :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="show ? 'text' : 'password'"
                  name="password"
                  label="Password"
                  counter
                  outlined
                  @click:append="show = !show"
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
    </v-card>
    </v-layout>
  </v-main>
</template>

<script>
import User from "@/models/auth/user";

export default {
  name: "Login",
  data() {
    return {
      user: new User('', ''),
      show: false,
      message: ''
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  created() {
    if (this.loggedIn) {
      this.$router.push('/profile');
    }
  },
  methods: {
    handleLogin() {
      if (this.user.username && this.user.password) {
        this.$store.dispatch('auth/LOGIN', this.user)
            .then(() => {
              this.$router.push('/profile');
            })
            .catch(() => {
              this.message = 'Неверный логин или пароль!'
            })
      }
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