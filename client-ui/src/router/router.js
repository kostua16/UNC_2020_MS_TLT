import Vue from 'vue'
import Router from 'vue-router'
import Tax from "@/components/tax/Tax";
import CitizenProperty from "@/components/communal/property/CitizenProperty";
import UsersProperties from "@/components/communal/property/admin/UsersProperties";
import Login from "@/components/auth/Login";
import store from '@/store/index'
import SignUp from "@/components/auth/SignUp";
import Profile from "@/components/main/Profile";
import UtilitiesPriceList from "@/components/communal/property/admin/price/utility/UtilitiesPriceList";
import PropertyTaxValue from "@/components/communal/property/admin/price/tax/PropertyTaxValue";
import Property from '@/components/communal/property/Property'
import MainPage from "@/components/main/MainPage";
import Transactions from "@/components/bank/Transactions";

Vue.use(Router);

// проверка авторизации пользователя
function checkAuth(to, from, next) {
    if (store.getters.GET_USER_IS_ACTIVE) {
        next()
    } else {
        console.warn('Вам необходимо авторизоваться!')
        next('login')
    }
}

// проверка прав
function checkPrivilege(to, from, next) {
    if (store.getters.GET_USER_IS_ACTIVE) {
        if (store.getters.IS_ADMIN_ROLE) {
            next()
        } else {
            // если недостаточно прав
            next('/profile')
        }
    } else {
        console.warn('Вам необходимо авторизоваться!')
        next('login')
    }
}

// запрещает посещать страницу, если пользователь авторизован
function checkNoAuth(to, from, next) {
    if (store.getters.GET_USER_IS_ACTIVE) {
        console.warn('Вам необходимо выйти из своего аккаунта!')
        next('profile')
    } else {
        next()
    }
}


export const router = new Router({
    mode: 'history',
    routes: [
        {
            // 404
            path: '*',
            name: 'NotFound',
            redirect: 'profile',

            beforeEnter(to, from, next) {
                if (store.getters.GET_USER_IS_ACTIVE) {
                    next('profile')
                } else {
                    console.warn('Вам необходимо авторизоваться!')
                    next('login')
                }
            },
        },
        {
            path: '/login',
            name: 'login',
            component: Login,
            beforeEnter(to, from, next) {
                checkNoAuth(to, from, next);
            }
        },
        {
            path: '/sign-up',
            name: 'sign-up',
            component: SignUp,
            beforeEnter(to, from, next) {
                checkNoAuth(to, from, next);
            }
        },
        {
            path: '/profile',
            name: 'profile',
            component: Profile,
            beforeEnter(to, from, next) {
                checkAuth(to, from, next);
            }
        },
        {
            path: '/communal/add-registration',
            name: 'add-registration',
            component: () => '@/components/communal/RegistrationData',
            beforeEnter(to, from, next) {
                checkAuth(to, from, next);
            },
        },
        {
            path: '/tax/get-all',
            name: 'tax-all',
            component: Tax,
            beforeEnter(to, from, next) {
                checkAuth(to, from, next);
            },
        },
        {
            path: '/communal/property/add-property',
            name: 'add-property',
            component: Property,
            beforeEnter(to, from, next) {
                checkAuth(to, from, next);
            },

        },
        {
            path: '/communal/property/list',
            name: 'property-list',
            component: CitizenProperty,
            beforeEnter(to, from, next) {
                checkAuth(to, from, next);
            },
        },
        {
            path: '/communal/admin/properties',
            name: 'users-properties',
            component: UsersProperties,
            beforeEnter(to, from, next) {
                checkPrivilege(to, from, next);
            },
        },
        {
            path: '/communal/admin/utilities/price-list',
            name: 'utilities-price-list',
            component: UtilitiesPriceList,
            beforeEnter(to, from, next) {
                checkPrivilege(to, from, next);
            },
        },
        {
            path: '/communal/admin/tax/price-list',
            name: 'tax-price-list',
            component: PropertyTaxValue,
            beforeEnter(to, from, next) {
                checkPrivilege(to, from, next);
            },
        },
        {
            path: '/main',
            name: 'main',
            component: MainPage,
            beforeEnter(to, from, next) {
                checkAuth(to, from, next);
            },
        },
        {
            path: '/bank/transactions',
            name: 'transactions',
            component: Transactions,
            beforeEnter(to, from, next) {
                checkAuth(to, from, next);
            }
        },
    ],
});