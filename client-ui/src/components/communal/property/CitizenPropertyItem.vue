<template>
  <v-card height="auto" class="blue-grey black--text title ma-4">
    <v-form>
      <v-container>
        <v-simple-table class="blue-grey black--text title">
          <template v-slot:default>
            <thead>
            <tr>
              <td>{{ property.propertyId }}</td>
              <td>{{ property.region }}</td>
              <td>{{ property.city }}</td>
              <td>{{ property.street }}</td>
              <td>{{ property.house }}</td>
              <td>{{ property.apartment }}</td>
              <td>{{ property.apartmentSize }}</td>
              <td>{{ property.citizenId }}</td>
            </tr>
            </thead>
          </template>
        </v-simple-table>

      </v-container>
    </v-form>
  </v-card>
</template>

<script>
export default {
  name: "CitizenPropertyItem",
  props: [
    'property'
  ],

}
</script>

<style scoped>

</style>