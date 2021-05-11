<template>
  <v-main>
    <v-container>
      <v-layout align-content-space-around justify-start column>
<!--        <creation-property :propertyAttr="property"/>-->
        <v-col cols="12" sm="4">
          <v-text-field
              v-model.trim="search"
              class="pa-3"
              append-icon="mdi-magnify"
              label="Введите регион, город или улицу"
              single-line
              hide-details
          ></v-text-field>
        </v-col>

        <v-data-table
            :headers="headers"
            :items="filteredProperties"
            item-key="propertyId"
            :items-per-page="10"
            class="elevation-0"
        >
          <template v-slot:top>
            <v-toolbar
                flat
            >
              <v-toolbar-title>Моя недвижимость</v-toolbar-title>
              <v-divider
                  class="mx-4"
                  inset
                  vertical
              ></v-divider>
              <v-spacer></v-spacer>
              <creation-property />
            </v-toolbar>
          </template>
          <template v-slot:item.actions="{ item }">
            <utilities-popup
                :propertyId="item.propertyId"
            />
          </template>
        </v-data-table>

        <!--        <citizen-property-item-->
        <!--            v-for="(property, index) in filteredProperties"-->
        <!--            :key="`property.propertyId - ${index}`"-->
        <!--            :property="property"-->
        <!--        />-->
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
// import CitizenPropertyItem from '@/components/communal/property/CitizenPropertyItem'
import Property from "@/models/communal/property";
import CreationProperty from "@/components/communal/property/CreationProperty";
import UtilitiesPopup from "@/components/communal/property/popup/UtilitiesPopup";

export default {
  name: "CitizenProperty",
  components: {CreationProperty, UtilitiesPopup},
  data() {
    return {
      search: '',
      property: new Property,
      properties: [],
      headers: [
        {
          text: 'регион',
          align: 'start',
          value: 'region',
        },
        {text: 'Город', value: 'city'},
        {text: 'Улица ', value: 'street'},
        {text: 'Дом', value: 'house'},
        {text: 'Квартира', value: 'apartment'},
        {text: 'Площадь', value: 'apartmentSize'},
        {
          text: 'Ввести показания',
          sortable: false,
          value: 'actions'
        },
      ],
    }
  },
  computed: {
    ...mapGetters(['GET_PROPERTIES']),
    filteredProperties() {
      return this.GET_PROPERTIES.filter(p => {
        return p.region.toLowerCase().includes(this.search.toLowerCase())
            || p.city.toLowerCase().includes(this.search.toLowerCase())
            || p.street.toLowerCase().includes(this.search.toLowerCase())
      })
    }
  },
  methods: {
    ...mapActions([
      'GET_PROPERTIES_FROM_API'
    ]),
  },
  created() {
    this.GET_PROPERTIES_FROM_API()
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