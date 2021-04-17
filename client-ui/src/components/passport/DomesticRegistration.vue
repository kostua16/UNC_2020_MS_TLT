<template>
  <v-main>
    <v-layout row wrap align-center justify-center>
      <v-card flat>
        <v-card-title>
          <span class="headline">Оформление паспорта гражданина РФ</span>
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
                    label="Имя"
                    required
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
                    label="Фамилия"
                    required
                ></v-text-field>
              </v-col>
              <v-col
                  v-if="domestic.series"
                  cols="12"
                  sm="6"
                  md="5"
              >
                <v-text-field
                    label="Серия"
                    readonly
                    required
                ></v-text-field>
              </v-col>
              <v-col
                  v-if="domestic.series"
                  cols="12"
                  sm="6"
                  md="2"
              ></v-col>
              <v-col
                  v-if="domestic.number"
                  cols="12"
                  sm="6"
                  md="5"
              >
                <v-text-field
                    label="Номер"
                    readonly
                    required
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
                        label="Дата Рождения"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
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
              v-if="!domestic.series"
              color="blue darken-1"
              text
              @click="registerDomestic"
          >
            Оформить
          </v-btn>
          <v-btn
              v-else-if="domestic.series"
              color="blue darken-1"
              text
              @click="updateDomestic"
          >
            Обновить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-layout>
  </v-main>
</template>

<script>
import {mapGetters} from "vuex";
import Domestic from "@/models/passport/domestic";

export default {
  name: "DomesticRegistration",
  data() {
    return {
      domestic: new Domestic(),
      number: 0,
      menu: false,
      name: '',
      surname: '',
      dateOfBirth: '',
    }
  },
  created() {
    this.domestic = this.GET_DOMESTIC
    this.name = this.domestic.name
    this.surname = this.domestic.surname
    this.dateOfBirth = this.domestic.dateOfBirth
  },
  computed: {
    ...mapGetters(['GET_DOMESTIC'])
  },
  methods: {
    registerDomestic() {
      // todo: validate and request to API
      this.domestic.name = this.name
      this.domestic.surname = this.surname
      this.domestic.dateOfBirth = this.dateOfBirth

    },
    updateDomestic() {
      // todo: validate
      this.domestic.name = this.name
      this.domestic.surname = this.surname
      this.domestic.dateOfBirth = this.dateOfBirth
    },
    clear() {
      this.domestic.name = ''
      this.domestic.surname = ''
      this.domestic.dateOfBirth = ''
    },
    save(date) {
      this.$refs.menu.save(date)
    },
  }
}
</script>

<style scoped>

</style>