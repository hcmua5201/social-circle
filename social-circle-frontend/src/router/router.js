import {createRouter, createWebHistory} from 'vue-router'
import index from "../views/index.vue";

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