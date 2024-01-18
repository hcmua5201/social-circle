import {createRouter, createWebHistory} from 'vue-router'
import index from "../views/index.vue";
import login from "../views/login.vue";
import register from "../views/register.vue"
import publish from "../views/publish.vue"
import admin from "../views/admin/admin.vue";

const router=createRouter({
    history:createWebHistory(),
    routes:[
        {
            path:'/',
            component:login
        },
        {
            path:'/register',
            component:register
        },
        {
            path:'/index',
            component:index
        },
        {
            path:'/publish',
            component:publish
        },
        {
            path:'/admin',
            component:admin
        }
    ]
})


export default router;