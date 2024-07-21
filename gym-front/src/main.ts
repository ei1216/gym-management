import './assets/main.scss'     //这个样式很关键，决定每一个vue页面的基本样式！！

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import {createPinia} from "pinia";
import {createPersistedState} from "pinia-plugin-persistedstate";
import 'element-plus/dist/index.css'        //导入el-plus的样式----有的组件不引入这个的话会没有样式的！！！


const app = createApp(App)
const pinia = createPinia()
const persist = createPersistedState()
//使用持久化插件
pinia.use(persist)

app.use(router).use(pinia).mount('#app')

