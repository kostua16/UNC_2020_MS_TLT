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

        <router-link to="/main">
          <v-btn
              v-if="GET_USER_IS_ACTIVE && $route.path !== '/main'"
              icon
          >
            <v-icon>home</v-icon>
          </v-btn>
        </router-link>
        <v-toolbar-title>
          ГосПортал
        </v-toolbar-title>

        <v-spacer></v-spacer>

        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <router-link to="/sign-up">

              <v-btn
                  v-if="!GET_USER_IS_ACTIVE"
                  icon
                  :disabled="$route.path === '/sign-up'"
                  v-bind="attrs"
                  v-on="on"
              >
                <v-icon>person_add</v-icon>
              </v-btn>
            </router-link>
          </template>
          <span>Регистрация</span>
        </v-tooltip>

        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <router-link to="/login">
              <v-btn
                  v-if="!GET_USER_IS_ACTIVE"
                  icon
                  :disabled="$route.path === '/login'"
                  v-bind="attrs"
                  v-on="on"
              >
                <v-icon>login</v-icon>
              </v-btn>
            </router-link>
          </template>
          <span>Вход в систему</span>
        </v-tooltip>

        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <router-link to="/profile">
              <v-btn
                  v-if="GET_USER_IS_ACTIVE"
                  icon
                  :disabled="$route.path === '/profile'"
                  v-bind="attrs"
                  v-on="on"
              >
                <v-icon>account_circle</v-icon>
              </v-btn>
            </router-link>
          </template>
          <span>Личный кабинет</span>
        </v-tooltip>

        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <router-link to="/login">
              <v-btn
                  v-if="GET_USER_IS_ACTIVE"
                  icon
                  @click="logout"
                  v-bind="attrs"
                  v-on="on"
              >
                <v-icon>exit_to_app</v-icon>
              </v-btn>
            </router-link>
          </template>
          <span>Выход из учётной записи</span>
        </v-tooltip>

      </v-app-bar>

      <v-navigation-drawer
          v-model="drawer"
          absolute
          bottom
          temporary
      >
        <!--        ЗАКОММЕНТИРОВАНО СПЕЦИАЛЬНО, ТАК КАК БУДУ ПЕРЕДЕЛЫВАТЬ-->
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
                @click="$router.push('/communal/utility-bills')"
                :disabled="$route.name === 'utility-bills'"
            >
              <v-list-item-title>
                Просмотреть квитанции ЖКХ
              </v-list-item-title>
            </v-list-item>
          </v-list-item-group>

          <v-list-item-group
              v-model="group"
              active-class="deep-purple--text text--accent-4"
          >
            <v-list-item
                @click="$router.push('/communal/property/list')"
                :disabled="$route.name === 'property-list'"
            >
              <v-list-item-title>
                Добавить недвижимость
              </v-list-item-title>
            </v-list-item>
          </v-list-item-group>

          <v-list-item-group
              v-model="group"
              active-class="deep-purple--text text--accent-4"
          >
            <v-list-item
                @click="$router.push('/bank/transactions')"
                :disabled="$route.name === 'transactions'"
            >
              <v-list-item-title>
                История платежей
              </v-list-item-title>
            </v-list-item>
          </v-list-item-group>

          <v-list-item-group
              v-if="GET_USER_IS_ACTIVE && IS_ADMIN_ROLE"
              v-model="group"
              active-class="deep-purple--text text--accent-4"
          >
            <v-list-item
                @click="$router.push('/communal/admin/utilities/price-list')"
                :disabled="$route.name === 'utilities-price-list'"
            >
              <v-list-item-title>
                Коммунальные прейскуранты
              </v-list-item-title>
            </v-list-item>
          </v-list-item-group>

          <v-list-item-group
              v-if="GET_USER_IS_ACTIVE && IS_ADMIN_ROLE"
              v-model="group"
              active-class="deep-purple--text text--accent-4"
          >
            <v-list-item
                @click="$router.push('/communal/admin/tax/price-list')"
                :disabled="$route.name === 'tax-price-list'"
            >
              <v-list-item-title>
                Налоговые прейскуранты
              </v-list-item-title>
            </v-list-item>
          </v-list-item-group>

          <v-list-item-group
              v-if="GET_USER_IS_ACTIVE && IS_ADMIN_ROLE"
              v-model="group"
              active-class="deep-purple--text text--accent-4"
          >
            <v-list-item
                @click="$router.push('/communal/admin/properties')"
                :disabled="$route.name === 'users-properties'"
            >
              <v-list-item-title>
                Недвижимость граждан
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
      'GET_USER_ROLE',
      'IS_ADMIN_ROLE'
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
