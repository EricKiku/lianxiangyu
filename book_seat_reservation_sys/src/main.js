import { createApp } from 'vue'
import './globalCss.css'
// 使用pinia
import { createPinia } from 'pinia'
const pinia = createPinia()
// 使用vuerouter
import router from './router/router.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 自适应
// import "amfe-flexible/index.js";
// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import '@mdi/font/css/materialdesignicons.css';
import { aliases, mdi } from 'vuetify/lib/iconsets/mdi';
const vuetify = createVuetify({
    icons: {
        defaultSet: 'mdi',
        aliases,
        sets: {
            mdi,
        },
    },
    components,
    directives,
    date: {

    }
})


import App from './App.vue'

createApp(App)
    .use(pinia)
    .use(router)
    .use(ElementPlus)
    .use(vuetify)
    .mount('#app')