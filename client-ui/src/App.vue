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
            v-if="GET_USER_IS_ACTIVE"
            @click.stop="drawer = !drawer"
        ></v-app-bar-nav-icon>

        <v-toolbar-title>ГосПортал</v-toolbar-title>

        <v-spacer></v-spacer>

        <router-link to="sign-up">
          <v-btn
              v-if="!GET_USER_IS_ACTIVE"
              icon
              :disabled="$route.path === '/sign-up'"
          >
            <v-icon>person_add</v-icon>
          </v-btn>
        </router-link>

        <router-link to="login">
          <v-btn
              v-if="!GET_USER_IS_ACTIVE"
              icon
              :disabled="$route.path === '/login'"
          >
            <v-icon>login</v-icon>
          </v-btn>
        </router-link>

        <router-link to="profile">
          <v-btn
              v-if="GET_USER_IS_ACTIVE"
              icon
              :disabled="$route.path === '/profile'"
          >
            <v-icon>account_circle</v-icon>
          </v-btn>
        </router-link>


        <v-btn
            v-if="GET_USER_IS_ACTIVE"
            icon
            @click="logout"
        >
          <v-icon>exit_to_app</v-icon>
        </v-btn>

      </v-app-bar>

      <v-navigation-drawer
          v-model="drawer"
          absolute
          bottom
          temporary
      >
<!--        <v-list-->
<!--            nav-->
<!--            v-for="(link) in links"-->
<!--        >-->
<!--            <v-list-item-group-->
<!--                v-if="!(link.role == 'ROLE_ADMIN' && GET_USER_ROLE != link.role)"-->
<!--                v-model="group"-->
<!--                active-class="deep-purple&#45;&#45;text text&#45;&#45;accent-4"-->
<!--            >-->
<!--              <v-list-item-->
<!--                  @click="$router.push(link.url)"-->
<!--                  :disabled="$route.name === link.name"-->
<!--              >-->
<!--                <v-list-item-title>-->
<!--                  {{ link.title }}-->
<!--                </v-list-item-title>-->
<!--              </v-list-item>-->
<!--            </v-list-item-group>-->

        <v-list nav>
                    <v-list-item-group
                        v-model="group"
                        active-class="deep-purple--text text--accent-4"
                    >
                      <v-list-item
                          @click="$router.push('/profile')"
                          :disabled="$route.name === 'profile'"
                      >
                        <v-list-item-title>
                          Личный кабинет
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
                          Просмотреть налоги
                        </v-list-item-title>
                      </v-list-item>
                    </v-list-item-group>

                    <v-list-item-group
                        v-model="group"
                        active-class="deep-purple--text text--accent-4"
                    >
                      <v-list-item
                          @click="$router.push('/communal/property/add-property')"
                          :disabled="$route.name === 'add-property'"
                      >
                        <v-list-item-title>
                          Добавить недвижимость
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
import {mapGetters} from 'vuex'

export default {
  name: 'App',
  data() {
    return {
      drawer: false,
      group: null,
      links: [
        {url: '/profile', name: 'profile', title: 'Личный кабинет', role: 'ROLE_USER'},
        {url: '/tax/get-all', name: 'tax-all', title: 'Посмотреть налоги', role: 'ROLE_USER'},
        {
          url: '/communal/property/add-property',
          name: 'add-property',
          title: 'Добавить недвижимость',
          role: 'ROLE_USER'
        },
        {url: '/communal/property/list', name: 'property-list', title: 'Список моей недвижимости', role: 'ROLE_USER'},
        {url: '/communal/add-registration', name: 'add-registration', title: 'Сменить регистрацию', role: 'ROLE_USER'},
        {
          url: '/communal/admin/properties',
          name: 'users-properties',
          title: 'Список недвижимости пользователей',
          role: 'ROLE_ADMIN'
        },
        // {url: '', name: '',  title: '', role: 'ROLE_USER'},
      ]
    }
  },
  watch: {
    group() {
      this.drawer = false
    },
  },
  computed: {
    ...mapGetters([
      'GET_USER_IS_ACTIVE',
      'GET_USER_ROLE'
    ]),
  },
  methods: {
    logout() {
      this.$store.dispatch('auth/LOGOUT');
      this.$router.push('/login');
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
