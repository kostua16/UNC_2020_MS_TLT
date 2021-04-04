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
                    v-model="regData.username"
                    type="text"
                    label="Логин"
                    counter
                    outlined
                    :rules="[rules.required, rules.minAuth]"
                    required
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col sm="9">
                <v-text-field
                    v-model="regData.password"
                    :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show ? 'text' : 'password'"
                    name="password"
                    label="Пароль"
                    counter
                    outlined
                    :rules="[rules.required, rules.minAuth]"
                    @click:append="show = !show"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col sm="9">
                <v-text-field
                    v-model="regData.name"
                    type="text"
                    label="Имя"
                    counter
                    outlined

                    :rules="[rules.required, rules.minData]"
                    required
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col sm="9">
                <v-text-field
                    v-model="regData.surname"
                    type="text"
                    label="Фамилия"
                    counter
                    outlined

                    :rules="[rules.required, rules.minData]"
                    required
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col sm="9">
                <v-text-field
                    v-model="regData.dateOfBirth"
                    type="text"
                    label="Дата рождения"
                    outlined
                    :rules="[rules.required]"
                    required
                    readonly
                >
                  <template v-slot:append>
                    <v-menu
                        style="top: -12px"
                        offset-y
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            color="white"
                            v-bind="attrs"
                            v-on="on"
                            :ripple="false"
                            class="v-btn--icon v-btn--flat mb-4"
                        >
                          <v-icon>event</v-icon>
                        </v-btn>
                      </template>
                      <v-date-picker
                          v-model="regData.dateOfBirth"
                      ></v-date-picker>
                    </v-menu>
                  </template>
                </v-text-field>


              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col sm="9">
                <v-text-field
                    v-model="regData.registration"
                    type="text"
                    label="Прописка"
                    counter
                    outlined
                    :rules="[rules.required, rules.minData]"
                    required
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
  </v-main>
</template>

<script>
import AuthService from '@/services/auth/auth-service'
import RegistrationData from "@/models/auth/registration-data";

export default {
  name: "SignUp",
  data() {
    return {
      regData: new RegistrationData,
      successful: false,
      show: false,
      message: '', // Сообщение в которое возвращается тело ошибки при регистрации
      issFilled: false,
      rules: { // правлиа для поллей ввода информации (+ юзается в html тегах, см выше) ПОЧИНИТЬ!!!
        required: value => !!value || 'Required!',
        minAuth: value => value.length > 4 || 'Number of characters 5-25',
        minData: value => value.length >= 2 || 'Min 2 characters',
      },
      number: 0
    };
  },
  methods: {
    clear() {
      this.regData.username = ''
      this.regData.password = ''
      this.regData.name = ''
      this.regData.surname = ''
      this.regData.dateOfBirth = ''
      this.regData.registration = ''
      // this.$refs.observer.reset()
      // this.$refs.form.reset;
    },
    registration() {
      // this.$refs.form.validate();
      AuthService.register(this.regData)
          .then(response => {
            if (response.status === 200) {
              this.$router.push('/login');
            } else {
              this.message = 'Ошибка регистрации!'
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