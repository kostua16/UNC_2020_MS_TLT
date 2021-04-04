<!-- Компонент ввода показаний счётчиков для определённой недвижимости -->
<!-- Принимает метод отправления показаний на сервер (Всплывающая форма) -->

<template>
  <v-row justify="center">
    <v-dialog
        v-model="dialog"
        persistent
        max-width="450"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            color="primary"
            dark
            v-bind="attrs"
            v-on="on"
        >
          Создать квитанцию
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="headline text-center">
          Введите показания со счётчиков
        </v-card-title>
        <v-text-field
            class="ml-5 mr-5"
            type="number"
            label="Холодная вода"
            v-model="utilitiesPayload.coldWater"
            hide-details="auto"
        />
        <v-text-field
            class="ml-5 mr-5"
            type="number"
            label="Горячая вода"
            v-model="utilitiesPayload.hotWater"
            hide-details="auto"
        />
        <v-text-field
            class="ml-5 mr-5"
            type="number"
            label="Электричество"
            v-model="utilitiesPayload.electricity"
            hide-details="auto"
        />
        <v-card-actions class="mt-4">
          <v-spacer></v-spacer>
          <v-btn
              color="green darken-1"
              text
              @click="sendUtilities"
          >
            Отправить
          </v-btn>
          <v-btn
              color="green darken-1"
              text
              @click="dialog = false"
          >
            Отменить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import UtilitiesPayload from '@/models/communal/requests/utilities-payload'
import UtilityBillService from "@/services/communal/utility-bill-service";

export default {
  name: "UtilitiesPopup",
  props: ['sendUtilitiesStatus', 'propertyId'],
  data() {
    return {
      dialog: false,
      utilitiesPayload: new UtilitiesPayload,
    }
  },
  methods: {
    sendUtilities() {
      this.dialog = false;
      this.utilitiesPayload.propertyId = this.propertyId;
      console.log(this.utilitiesPayload);
      UtilityBillService.createPropertyTax(this.utilitiesPayload)
          .then(response => {
            this.sendUtilitiesStatus(response.status);
          })
    },
  }
}
</script>

<style scoped>

</style>