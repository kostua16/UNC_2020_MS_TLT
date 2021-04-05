<template>
  <v-container>
    <v-layout align-content-space-around justify-start column>

      <v-col cols="12" sm="4">
        <v-text-field
            class="pa-3"
            append-icon="mdi-magnify"
            label="Введите адрес"
            single-line
            hide-details
        ></v-text-field>
      </v-col>

      <user-property-item
          v-for="(property, index) in getAllProperties"
          :key="`property.propertyId - ${index}`"
          :property="property"
      />
    </v-layout>
  </v-container>
</template>

<script>
import UserPropertyItem from '@/components/communal/property/admin/UserPropertyItem'
import Property from "@/models/communal/property";
import {mapActions, mapGetters} from "vuex";

export default {
  name: "UsersProperties",
  components: {UserPropertyItem},
  data() {
    return {
      property: new Property,
      properties: [],
    }
  },
  computed: {
    ...mapGetters(['GET_ALL_PROPERTIES']),
    getAllProperties() {
      return this.GET_ALL_PROPERTIES;
    }
  },
  methods: {
    ...mapActions([
      'GET_ALL_PROPERTIES_FROM_API',
    ]),
  },
  created() {
    this.GET_ALL_PROPERTIES_FROM_API()
        .then(response => {
          if (response.data) {
            console.log('Users properties arrived!')
          }
        })
  },
}
</script>

<style scoped>

</style>