<template>
  <v-card class="mx-5 my-5 green lighten-1" width="495px" height="270px">
    <v-row>
      <v-col sm="9">
        <v-card-title primary-title>
          Регион: {{ utilitiesPriceList.region }}
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
        <v-col sm="8">Цена за 1 куб. м. Х. воды:</v-col>
        <v-col>{{ utilitiesPriceList.coldWaterPrice }} руб.</v-col>
      </v-row>
      <v-row justify="center">
        <v-col sm="8">Цена за 1 куб. м. Г. воды:</v-col>
        <v-col>{{ utilitiesPriceList.hotWaterPrice }} руб.</v-col>
      </v-row>
      <v-row justify="center">
        <v-col sm="8">Цена за 1 кВт электроэнергии:</v-col>
        <v-col>{{ utilitiesPriceList.electricityPrice }} руб.</v-col>
      </v-row>
    </v-card-title>
  </v-card>
</template>

<script>
export default {
  name: "UtilitiesPriceListItem",
  props: ['utilitiesPriceList', 'editPriceLIst'],
  data() {
    return {
      message: ''
    }
  },
  methods: {
    edit() {
      this.editPriceLIst(this.utilitiesPriceList);
    },
  }
}
</script>

<style scoped>
.main-block {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.text-settings {
  font-size: 26px;
  color: black;
}

.text-card-title {
  line-height: 30px;
  color: black;
}
</style>