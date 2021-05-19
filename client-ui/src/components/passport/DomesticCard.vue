<template>
  <v-card
      v-if="domestic.domesticId"
      height="450px"
      max-width="600px"
      flat
  >
    <v-card-title>{{ domestic.surname }} {{ domestic.name }}</v-card-title>
    <v-list>
      <v-list-item>
        <v-row>
          <v-col sm="4">
            Дата Рождения:
          </v-col>
          <v-col class="font-weight-bold">
            {{ domestic.dateOfBirth | moment }}
          </v-col>
        </v-row>
      </v-list-item>
      <v-list-item>
        <v-row>
          <v-col sm="4">
            Серия/Номер:
          </v-col>
          <v-col class="font-weight-bold">
            {{ domestic.series }} / {{ domestic.number }}
          </v-col>
        </v-row>
      </v-list-item>
      <v-list-item v-if="registration.registrationId">
        <v-row>
          <v-col sm="4">
            Прописка:
          </v-col>
          <v-col class="font-weight-bold">
            {{ registration.region }},
            г. {{ registration.city }},
            ул. {{ registration.street }},
            д. {{ registration.house }},
            офис {{ registration.apartment }}
          </v-col>
        </v-row>
      </v-list-item>
      <v-list-item v-else>
        У вас ещё нет прописки
      </v-list-item>
    </v-list>
    <v-card-actions class="card_action">
      <v-btn
          v-if="domestic.registrationId"
          @click="$router.push('/communal/add-registration')"
      >
        Изменить прописку
      </v-btn>
      <v-btn
          v-else
          @click="$router.push('/communal/add-registration')"
      >
        Добавить прописку
      </v-btn>
    </v-card-actions>
  </v-card>
  <v-hover
      v-else
      v-slot="{ hover }"
      close-delay="200"
  >
    <v-card
        flat
        :elevation="hover ? 8 : 2"
        :class="{ 'on-hover': hover }"
        class="mx-auto text-center"
        height="400px"
        max-width="600px"
        color="#f4f9fa"
        @click="$router.push('/passport/domestic')"
    >
      <v-card-text
          class="mt-12 text-xs-center black--text text-h5"
      >
        У вас ещё нет паспорта гражданина РФ. Оформите его сейчас!
      </v-card-text>
    </v-card>
  </v-hover>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import Domestic from "@/models/passport/domestic";
import Moment from "moment";
import Registration from "@/models/communal/registration";

export default {
  name: "DomesticCard",
  data() {
    return {
      message: '',
      domestic: new Domestic(),
      registration: new Registration(),
    }
  },
  computed: {
    ...mapGetters(['GET_DOMESTIC', 'GET_REGISTRATION']),
  },
  methods: {
    ...mapActions(['GET_DOMESTIC_FROM_API', 'GET_REGISTRATION_FROM_API'])
  },
  created() {
    this.GET_DOMESTIC_FROM_API()
        .then(response => {
          if (response.status !== 200) {
            // красное сообщение
            this.message = 'Не удалось получить данные о паспотре гражданина РФ!';
          } else {
            this.domestic = response.data;
            this.GET_REGISTRATION_FROM_API(this.domestic.registrationId)
                .then(resp => {
                  console.log(resp.data)
                  if (resp.status !== 200) {
                    // error snackbar
                  } else {
                    this.registration = resp.data
                  }
                })
          }
        })
  },
  filters: {
    moment(date) {
      Moment.locale('ru')
      return Moment(date).format('DD MMMM YYYY');
    }
  }
}
</script>

<style scoped>
.card_action {
  position: absolute;
  bottom: 0;
}

</style>