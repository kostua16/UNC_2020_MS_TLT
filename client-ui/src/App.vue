<template>
  <v-app>
    <div>
      <v-app-bar
          app
          color="deep-purple accent-4"
          dense
          dark
      >

        <v-app-bar-nav-icon
            @click.stop="drawer = !drawer"
        ></v-app-bar-nav-icon>

        <v-toolbar-title>ГосПортал</v-toolbar-title>

        <v-spacer></v-spacer>

        <router-link to="registration">
          <v-btn
              icon
              :disabled="$route.path === '/registration'"
          >
            <v-icon>person_add</v-icon>
          </v-btn>
        </router-link>

        <router-link to="login">
          <v-btn
              icon
              :disabled="$route.path === '/login'"
          >
            <v-icon>login</v-icon>
          </v-btn>

        </router-link>
        <v-btn icon @click="logOut">
          <v-icon>exit_to_app</v-icon>
        </v-btn>
      </v-app-bar>

      <v-navigation-drawer
          v-model="drawer"
          absolute
          bottom
          temporary
      >
        <v-list
            nav
        >
          <v-list-item-group
              v-model="group"
              active-class="deep-purple--text text--accent-4"
          >
            <v-list-item
                @click="$router.push('/communal/add-registration')"
                :disabled="$route.name === 'add-registration'"
            >
              <v-list-item-title>
                Profile
              </v-list-item-title>
            </v-list-item>

          </v-list-item-group>

          <v-list-item-group
              v-model="group"
              active-class="deep-purple--text text--accent-4"
          >
            <v-list-item
                @click="$router.push('/tax/get-all')"
                :disabled="$route.name === 'tax-all'"
            >
              <v-list-item-title>
                TAX
              </v-list-item-title>
            </v-list-item>

          </v-list-item-group>
        </v-list>
      </v-navigation-drawer>


      <div class="container">
        <router-view/>
      </div>
    </div>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      drawer: false,
      group: null
    }
  },
  watch: {
    group () {
      this.drawer = false
    },
  },
  methods: {
    logOut() {
      console.log("Test exit")
      // this.$store.dispatch('auth/LOGOUT');
      // this.$router.push('/login');
    },
  }
}
</script>

<style>
.font-size {
  font-size: 110%;
}
.navigate-btn {
  color: #6200ea;
}
</style>
