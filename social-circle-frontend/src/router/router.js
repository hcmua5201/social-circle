import {createRouter, createWebHistory} from 'vue-router'
import index from "../views/index2.vue";

const router=createRouter({
    history:createWebHistory(),
    routes:[
        {
            path:'/',
            component:index
        }
    ]
})


export default router;