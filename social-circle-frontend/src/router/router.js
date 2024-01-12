import {createRouter, createWebHistory} from 'vue-router'
import index from "../views/index.vue";
import login from "../views/login.vue";

const router=createRouter({
    history:createWebHistory(),
    routes:[
        {
            path:'/',
            component:login
        },
        {
            path:'/index',
            component:index
        }
    ]
})


export default router;