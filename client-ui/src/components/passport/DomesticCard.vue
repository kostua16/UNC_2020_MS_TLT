<template>
  <v-card
      height="450px"
      max-width="600px"
      flat
  >
    <v-card-title>{{ domestic.surname }} {{ domestic.name }}</v-card-title>
    <v-list>
      <v-list-item>Дата Рождения {{ domestic.dateOfBirth }}</v-list-item>
      <v-list-item>Серия: {{ domestic.series }}</v-list-item>
      <v-list-item>Номер:{{ domestic.number }}</v-list-item>
      <v-list-item>Что-то</v-list-item>
    </v-list>
    <v-card-actions class="card_action">
      <v-btn>
        Изменить паспортные данные
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import Domestic from "@/models/passport/domestic";

export default {
  name: "DomesticCard",
  data() {
    return {
      message: '',
      domestic: new Domestic(),
    }
  },
  computed: {
    ...mapGetters(['GET_DOMESTIC'])
  },
  methods: {
    ...mapActions(['GET_DOMESTIC_FROM_API'])
  },
  created() {
    this.GET_DOMESTIC_FROM_API()
        .then(response => {
          if (response.status !== 200) {
            // красное сообщение
            this.message = 'Не удалось получить данные о паспотре гражданина РФ!';
          } else {
            this.domestic = response.data;
          }
        })
  }
}
</script>

<style scoped>
.card_action {
  position: absolute;

  bottom: 0;
}

</style>