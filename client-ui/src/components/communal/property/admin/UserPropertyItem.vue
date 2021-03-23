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
                <v-btn @click="createPropertyTax">Создать налог</v-btn>
              </td>
              <td>
<!--                <v-btn @click="createUtilityBill">Создать квитанцию</v-btn>-->
                <utilities-popup :sendUtilities="sendUtilities"/>
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
import UtilitiesPayload from "@/models/communal/requests/utilities-payload";
import PropertyTaxService from "@/services/communal/property-tax-service";
import UtilityBillService from "@/services/communal/utility-bill-service";
import UtilitiesPopup from "@/components/communal/popup/UtilitiesPopup";

const ERROR_MESSAGE = 'Error! Try later.';
const PROPERTY_TAX_SUC_MESSAGE = 'Property Tax has been created.';
const UTILITY_BILL_SUC_MESSAGE = 'Utility bill has been created.';

export default {
  name: "UserPropertyItem",
  components: {UtilitiesPopup},
  props: [
    'property'
  ],
  data() {
    return {
      message: '',
      status: 0,
    }
  },

  methods: {
    createPropertyTax() {
      PropertyTaxService.createPropertyTax(this.property.propertyId)
          .then(response => {
            if (response.status === 200) {
              this.message = PROPERTY_TAX_SUC_MESSAGE;
            } else {
              this.message = ERROR_MESSAGE;
            }
          })
    },
    sendUtilities(utilitiesPayload) {
      utilitiesPayload.propertyId = this.property.propertyId;
      console.log(utilitiesPayload);
      UtilityBillService.createPropertyTax(utilitiesPayload)
          .then(response => {
            if (response.status === 200) {
              this.message = UTILITY_BILL_SUC_MESSAGE;
            } else {
              this.message = ERROR_MESSAGE;
            }
          })
    },
  },
  watch() { // отслеживать message, в случае изменения выводить небольшое цветное (злёное/красное) уведомление
  }
}
</script>

<style scoped>

</style>