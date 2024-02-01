import {createRouter, createWebHistory} from 'vue-router'
import index from "../views/index.vue";
import login from "../views/login.vue";
import register from "../views/register.vue"
import publish from "../views/publish.vue"
import admin from "../views/admin/view/admin.vue";
import main_success from "../views/admin/components/main_success.vue";
import shouye from "../views/shouye.vue";


const router=createRouter({
    history:createWebHistory(),
    routes:[
        {
            path:'/',
            component:shouye
        },
        {
            path:'/login',
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
            component:admin,
            children:[
                {
                    path:'main_success',
                    component:main_success
                }
            ]
        }
    ]
})


export default router;
