<template>
  <v-container>
    <v-layout align-content-space-around justify-start column>

      <v-col cols="12" sm="4">
        <v-text-field
            class="pa-3"
            append-icon="mdi-magnify"
            label="Введите название тарифа"
            single-line
            hide-details
        ></v-text-field>
      </v-col>

      <citizen-property-item
          v-for="(property, index) in getProperties"
          :key="`property.propertyId - ${index}`"
          :property="property"
      />
    </v-layout>
  </v-container>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import CitizenPropertyItem from '@/components/communal/property/CitizenPropertyItem'
import Property from "@/models/communal/property";

export default {
  name: "CitizenProperty",
  components: {CitizenPropertyItem},
  data() {
    return {
      property: new Property,
      properties: []
    }
  },
  computed: {
    ...mapGetters(['GET_PROPERTIES']),
    getProperties() {
      return this.GET_PROPERTIES;
    }
  },
  methods: {
    ...mapActions([
      'GET_PROPERTIES_FROM_API'
    ]),
  },
  created() {
    this.GET_PROPERTIES_FROM_API(111)
        .then(response => {
          if (response.data) {
            console.log('Data arrived!')
          }
        })
  },
}
</script>

<style scoped>

</style>