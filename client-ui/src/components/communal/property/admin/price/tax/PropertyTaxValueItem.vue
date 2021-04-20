<template>
  <v-card class="my-2 text-settings" width="850px">
    <v-main class="yellow darken-3 black--text pt-8">
      <div class="main-block">
        <v-card-title primary-title>
          <h2>{{ propertyTaxValue.region }}</h2>
        </v-card-title>
        <v-card-actions>
          <v-btn
              @click="edit"
              class="ma-2"
              outlined
              small
              fab
              color="deep-purple accent-4"
          >
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
        </v-card-actions>
      </div>
      <v-card-text>

        <v-simple-table height="120px" class="yellow darken-3 black--text title">
          <template v-slot:default>
            <thead>
            <tr>
              <th class="text-left">Цена за кв. м.</th>
              <th class="text-left">Кадастровое значение</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>{{ propertyTaxValue.pricePerSquareMeter }} руб.</td>
              <td>{{ propertyTaxValue.cadastralValue }} руб.</td>
            </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-card-text>
    </v-main>
  </v-card>
</template>

<script>
export default {
  name: "PropertyTaxValueItem",
  props: ['propertyTaxValue', 'editPriceLIst'],
  data() {
    return {
      message: ''
    }
  },
  methods: {
    edit() {
      this.editPriceLIst(this.propertyTaxValue);
    },
  }
}
</script>

<style scoped>

</style>