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
                        v-model="regData.dateOfBirth"
                        label="Дата Рождения"
                        prepend-icon="mdi-calendar"
                        readonly
                        outlined
                        v-bind="attrs"
                        v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                      ref="picker"
                      v-model="regData.dateOfBirth"
                      :max="new Date().toISOString().substr(0, 10)"
                      min="1950-01-01"
                      @change="save"
                  ></v-date-picker>
                </v-menu>
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

export default {
  name: "DomesticRegistration",
  data() { // todo: fix for passport registration
    return {
      regData: '', // new RegistrationData,
      successful: false,
      show: false,
      message: '', // Сообщение в которое возвращается тело ошибки при регистрации
      issFilled: false,
      rules: { // правлиа для поллей ввода информации (+ юзается в html тегах, см выше) ПОЧИНИТЬ!!!
        required: value => !!value || 'Required!',
        minAuth: value => value.length > 4 || 'Number of characters 5-25',
        minData: value => value.length >= 2 || 'Min 2 characters',
      },
      number: 0,
      menu: false,
    };
  },
}
</script>

<style scoped>

</style>