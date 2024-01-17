import { createApp } from 'vue'
import element from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router/router.js'
import 'font-awesome/css/font-awesome.min.css'
import VueClickOutside from 'v-click-outside';


const app=createApp(App)
app.use(element)
app.use(router)
app.use(VueClickOutside);
app.mount('#app')
