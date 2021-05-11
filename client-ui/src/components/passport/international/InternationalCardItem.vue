<template>
  <v-col sm="6">
    <v-card class="mt-5">
      <v-card-title>{{ international.name }} {{ international.surname }}</v-card-title>
      <v-list>
        <v-list-item>
          Уникальный номер: {{ international.internationalId }}
        </v-list-item>
        <v-list-item>
          Дата Рождения: {{ international.dateOfBirth | moment }}
        </v-list-item>
      </v-list>
      <v-card-actions>
        <v-tooltip bottom>

          <template v-slot:activator="{ on, attrs }">
            <v-icon
                v-if="!international.isActive"
                color="primary"
                dark
                v-bind="attrs"
                v-on="on"
            >
              lock
            </v-icon>
            <v-icon
                v-else
                color="primary"
                dark
                v-bind="attrs"
                v-on="on"
            >
              lock_open
            </v-icon>
          </template>
          <span v-if="!international.isActive">
            Заграничный пасспорт не активирован. Для активации оплатите все выставленные счета.
          </span>
          <span v-else>
            Заграничный пасспорт активирован.
          </span>
        </v-tooltip>
      </v-card-actions>
    </v-card>
  </v-col>
</template>

<script>
import Moment from "moment"

export default {
  name: "InternationalCardItem",
  props: ['international'],
  data() {
    return {}
  },
  computed: {},
  methods: {},
  filters: {
    moment(date) {
      Moment.locale('ru')
      return Moment(date).format('DD MMMM YYYY');
    }
  },
}
</script>

<style scoped>

</style>