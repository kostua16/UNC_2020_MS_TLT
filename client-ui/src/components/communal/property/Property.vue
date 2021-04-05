<template>
  <div class="mt-10">
    <v-text-field type="text" label="Область" v-model="creationProperty.region" hide-details="auto"/>
    <v-text-field type="text" label="Населённый пункт" v-model="creationProperty.city" hide-details="auto"/>
    <v-text-field type="text" label="Улица" v-model="creationProperty.street" hide-details="auto"/>
    <v-text-field type="text" label="Дом" v-model="creationProperty.house" hide-details="auto"/>
    <v-text-field type="text" label="Офис/Квартира" v-model="creationProperty.apartment" hide-details="auto"/>
    <v-text-field type="number" label="Площадь квартиры" v-model="creationProperty.apartmentSize" hide-details="auto"/>
    <v-btn @click="addProperty" class="mt-5 mb-8" block color="red" dark>Добавить недвижимость</v-btn>
  </div>
</template>

<script>
import PropertyService from '@/services/communal/property-service'
import CreationProperty from '@/models/communal/requests/creation-property'

export default {
  name: "Property",
  data() {
    return {
      creationProperty: new CreationProperty()
    }
  },
  methods: {
    addProperty() {
      const creationProperty = {
        region: this.creationProperty.region,
        city: this.creationProperty.city,
        street: this.creationProperty.street,
        house: this.creationProperty.house,
        apartment: this.creationProperty.apartment,
        apartmentSize: this.creationProperty.apartmentSize,
      }
      console.log(creationProperty);
      PropertyService.addProperty(creationProperty);

      this.creationProperty.region = ''
      this.creationProperty.city = ''
      this.creationProperty.street = ''
      this.creationProperty.house = ''
      this.creationProperty.apartment = ''
      this.creationProperty.apartmentSize = 0
    }
  }
}
</script>

<style scoped>

</style>