<template>
  <div class="mt-10">
    <!--    добавить правило ввода (не меньше 3 символов :rule) https://vuetifyjs.com/en/components/inputs/#inputs  -->
    <v-text-field type="text" label="Область" v-model="registration.region" hide-details="auto"/>
    <v-text-field type="text" label="Населённый пункт" v-model="registration.city" hide-details="auto"/>
    <v-text-field type="text" label="Улица" v-model="registration.street" hide-details="auto"/>
    <v-text-field type="text" label="Дом" v-model="registration.house" hide-details="auto"/>
    <v-text-field type="text" label="офис/квартира" v-model="registration.apartment" hide-details="auto"/>
    <v-text-field type="number" label="citizenId - ЭТОГО ПОЛЯ НЕ БУДЕТ" v-model="registration.citizenId" hide-details="auto"/>
    <v-btn @click="addRegistration" class="mt-5 mb-8" block color="red" dark>Добавить прописку</v-btn>
  </div>
</template>

<script>

import Registration from '@/models/communal/registration'
import CommunalService from '@/services/communal/communal-service'

export default {
  name: "Registration",
  data() {
    return {
      registration: new Registration()
    }
  },
  methods: {
    addRegistration() {
      const registration = {
        region: this.registration.region,
        city: this.registration.city,
        street: this.registration.street,
        house: this.registration.house,
        apartment: this.registration.apartment,
        citizenId: this.registration.citizenId
      }
      console.log(registration);
      CommunalService.addRegistration(registration);

      this.registration.region = ''
      this.registration.city = ''
      this.registration.street = ''
      this.registration.house = ''
      this.registration.internet = ''
      this.registration.apartment = ''
      this.registration.citizenId = ''
    }
  }
}
</script>

<style scoped>

</style>