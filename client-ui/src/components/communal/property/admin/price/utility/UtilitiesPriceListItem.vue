<template>
  <v-card class="my-2 text-settings" width="850px">
    <v-main class="yellow darken-3 black--text pt-8">
      <div class="main-block">
        <v-card-title primary-title>
          <h2>{{ utilitiesPriceList.region }}</h2>
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
<!--          <v-btn-->
<!--              v-if="IS_ADMIN_ROLE"-->
<!--              @click="del"-->
<!--              small-->
<!--              fab-->
<!--              outlined-->
<!--              color="deep-purple accent-4"-->
<!--          >-->
<!--            <v-icon color="deep-purple accent-4">delete</v-icon>-->
<!--          </v-btn>-->

        </v-card-actions>
      </div>
      <v-card-text>

        <v-simple-table height="120px" class="yellow darken-3 black--text title">
          <template v-slot:default>
            <thead>
            <tr>
              <th class="text-left">За куб. м. ХВ</th>
              <th class="text-left">За куб. м. ГВ</th>
              <th class="text-left">За КВТ</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>{{ utilitiesPriceList.coldWaterPrice }} руб.</td>
              <td>{{ utilitiesPriceList.hotWaterPrice }} руб.</td>
              <td>{{ utilitiesPriceList.electricityPrice }} руб.</td>
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