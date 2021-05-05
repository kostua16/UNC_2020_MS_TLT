<template>
  <v-card class="mx-5 my-5 orange darken-1" width="490px" height="220px">
    <v-row>
      <v-col sm="9">
        <v-card-title primary-title>
          Регион: {{ propertyTaxValue.region }}
        </v-card-title>
      </v-col>
      <v-col>
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
      </v-col>
    </v-row>
    <v-card-title class="pt-6">
      <v-row justify="center">
        <v-col sm="7">Цена за 1 кв. метр:</v-col>
        <v-col>{{ propertyTaxValue.pricePerSquareMeter }} руб.</v-col>
      </v-row>
      <v-row justify="center">
        <v-col sm="7">Кадастровое значение:</v-col>
        <v-col>{{ propertyTaxValue.cadastralValue }} %</v-col>
      </v-row>
    </v-card-title>
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