<template>
  <v-main>
    <v-container class="pa-4 text-center">
      <v-row
          class="fill-height"
          align="center"
          justify="center"
      >
        <template v-for="(item, i) in items">
          <v-col
              :key="i"
              cols="12"
              md="4"
          >
            <v-card-title>{{item.title}}</v-card-title>
            <v-hover v-slot="{ hover }">
              <v-card
                  @click="$router.push(item.link)"
                  :elevation="hover ? 12 : 2"
                  :class="{ 'on-hover': hover }"
              >
                <v-img
                    :src="item.img"
                    height="350px"
                >
                  <v-card-title class="title white--text">

                  </v-card-title>
                </v-img>
              </v-card>
            </v-hover>
          </v-col>
        </template>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "MainPage",
  data() {
    return {
      icons: ['mdi-rewind', 'mdi-play', 'mdi-fast-forward'],
      items: [
        {
          title: 'Недвижимость',
          link: 'communal/property/list',
          img: require('@/assets/images/property.png'),
        },
        {
          title: 'История оплаты',
          link: '/bank/transactions',
          img: require('@/assets/images/payment_history.png'),
        },
        {
          title: 'Налоги',
          link: 'tax/get-all',
          img: require('@/assets/images/tax.png'),
        },
        {
          title: 'Налоги на недвижимость',
          link: '/communal/property-tax',
          img: require('@/assets/images/propert_tax.png'),
        },
        {
          title: 'Коммунальные квитанции',
          link: '/communal/utility-bills',
          img: require('@/assets/images/utility.png'),
        },
        {
          title: 'Оформить Заграничный паспорт',
          link: '/passport/international',
          img: require('@/assets/images/international_.png'),
        },
      ],
      transparent: 'rgba(255, 255, 255, 0)',
    }
  }

}
</script>

<style scoped>
.v-card {
  transition: opacity .4s ease-in-out;
}

.v-card:not(.on-hover) {
  opacity: 0.6;
}

.show-btns {
  color: rgba(255, 255, 255, 1) !important;
}
</style>