import Vue from 'vue'
import Router from 'vue-router'
import Registration from '@/components/communal/Registration'
import Tax from "@/components/tax/Tax";
import Property from "@/components/communal/property/Property";
import CitizenProperty from "@/components/communal/property/CitizenProperty";
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
            path: '/property/add-property',
            name: 'add-property',
            component: Property
        },
        {
            path: '/property/list',
            name: 'property-list',
            component: CitizenProperty
        }
        // {
        //     path: '/main',
        //     name: 'main',
        //     component: MainPage
        // }
    ],

});