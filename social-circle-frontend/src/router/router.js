import {createRouter, createWebHistory} from 'vue-router'
import index from "../views/index.vue";
import login from "../views/login.vue";
import register from "../views/register.vue"
import publish from "../views/publish.vue"
import admin from "../views/admin/admin.vue";
import shouye from "../views/shouye.vue";
import login1 from "../views/login1.vue";
import register1 from "../views/register1.vue";

const router=createRouter({
    history:createWebHistory(),
    routes:[
        {
            path:'/',
            component:shouye
        },
        {
            path:'/login',
            component:login1
        },
        {
            path:'/register',
            component:register1
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