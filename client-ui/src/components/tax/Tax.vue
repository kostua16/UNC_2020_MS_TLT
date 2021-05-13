<template>
  <v-main>
    <h2 class="text-left ml-2">Налоги</h2>
    <div class="pt-5">
      <v-data-table
          :no-data-text="message"
          :headers="headers"
          :items="taxes"
          :page.sync="page"
          :items-per-page="itemsPerPage"
          hide-default-footer
          class="elevation-1"
          @page-count="pageCount = $event"
      >
        <template v-slot:item.status="{ item }">
          <v-simple-checkbox
              v-model="item.status"
              disabled
          ></v-simple-checkbox>
        </template>
      </v-data-table>
      <div class="text-center pt-2">
        <v-pagination
            v-model="page"
            :length="pageCount"
        ></v-pagination>
        <v-row justify="center">
          <v-col sm="4">
            <v-text-field
                :value="itemsPerPage"
                :error-messages="pageErrors"
                label="Введите количествово записей на одной странице"
                type="number"
                required
                min="1"
                max="20"
                @input="$v.itemsPerPage.$touch();  itemsPerPage = parseInt($event, 10)"
                @blur="$v.itemsPerPage.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
      </div>
    </div>
  </v-main>
</template>

<script>

import Tax from '@/models/tax/tax'
import TaxService from '@/services/tax/tax-service'
import {between, minLength, numeric, required} from "vuelidate/lib/validators";

export default {
  name: "Tax",
  data() {
    return {
      tax: new Tax(),
      taxes: [],
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      headers: [
        {
          text: 'Уникальный номер',
          align: 'start',
          value: 'taxId',
          sortable: false,
        },
        {text: 'Сумма', value: 'taxAmount'},
        {text: 'Дата создания', align: 'start', value: 'creationDate'},
        {text: 'Статус оплаты', value: 'status'},
        {text: 'Дата погашения', align: 'start', value: 'taxPaymentDate'},
        {text: 'Идентификатор услуги', sortable: false, value: 'serviceId'},
      ],
      message: 'Налогов не найдено.',
    }
  },
  validations: {
    itemsPerPage: {numeric, between: between(1, 20), minLength: minLength(1), required},
  },
  computed: {
    pageErrors() {
      const errors = []
      if (!this.$v.itemsPerPage.$dirty) return errors
      !this.$v.itemsPerPage.between && errors.push('Кол-во записей на одной странице 1-20')
      !this.$v.itemsPerPage.numeric && errors.push('Это числовое поле')
      !this.$v.itemsPerPage.required && errors.push('Это обязательное поле!')
      return errors
    },
  },
  created() {
    TaxService.getALlTaxes()
        .then(response => {
          if (response.status === 200) {
            this.taxes = response.data
          } else {
            this.message = 'Не удалось получить информацию о налогах. Попробуйте позже'
          }
        })
  }
}
</script>

<style scoped>

</style>