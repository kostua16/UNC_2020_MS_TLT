<template>
  <v-card height="auto" class="blue-grey black--text title ma-4">
    <v-form>
      <v-container>
        <v-simple-table class="blue-grey black--text title">
          <template v-slot:default>
            <thead>
            <tr>
              <td>{{ property.propertyId }}</td>
              <td>{{ property.region }}</td>
              <td>{{ property.city }}</td>
              <td>{{ property.street }}</td>
              <td>{{ property.house }}</td>
              <td>{{ property.apartment }}</td>
              <td>{{ property.apartmentSize }}</td>
              <td>{{ property.citizenId }}</td>
              <td>
                <utilities-popup
                    :sendUtilitiesStatus="sendUtilitiesStatus"
                    :propertyId="property.propertyId"
                />
              </td>
            </tr>
            </thead>
          </template>
        </v-simple-table>
      </v-container>
    </v-form>
  </v-card>
</template>

<script>
import UtilitiesPopup from "@/components/communal/property/popup/UtilitiesPopup";

const ERROR_MESSAGE = 'Error! Try later.';
const UTILITY_BILL_SUC_MESSAGE = 'Utility bill has been created.';

export default {
  name: "CitizenPropertyItem",
  props: [
    'property'
  ],
  components: {UtilitiesPopup},
  methods: {
    /**
     * Вывод сообщени/статуса о проведённой операции по созданию квитанции о затраченных коммунальных услугах.
     *
     * @param status статус ответа от бэкенда
     */
    sendUtilitiesStatus(status) {
      if (status === 200) {
        this.message = UTILITY_BILL_SUC_MESSAGE;
      } else {
        this.message = ERROR_MESSAGE;
      }
    },
  }
}
</script>

<style scoped>

</style>