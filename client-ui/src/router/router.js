import Vue from 'vue'
import Router from 'vue-router'
import Registration from '@/components/communal/Registration'
import Tax from "@/components/tax/Tax";
import Property from "@/components/communal/property/Property";
import CitizenProperty from "@/components/communal/property/CitizenProperty";
import UsersProperties from "@/components/communal/property/admin/UsersProperties";
import PersonalArea from "@/components/main/PersonalArea";
// import MainPage from "@/components/main/MainPage";

Vue.use(Router);

export const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/communal/add-registration',
            name: 'add-registration',
            component: Registration
        },
        {
            path: '/tax/get-all',
            name: 'tax-all',
            component: Tax
        },
        {
            path: '/communal/property/add-property',
            name: 'add-property',
            component: Property
        },
        {
            path: '/communal/property/list',
            name: 'property-list',
            component: CitizenProperty
        },
        {
            path: '/communal/admin/properties',
            name: 'users-properties',
            component: UsersProperties
        },
        {
            path: '/person-area',
            name: 'person-area',
            component: PersonalArea
        },
        // {
        //     path: '/main',
        //     name: 'main',
        //     component: MainPage
        // }
    ],

});