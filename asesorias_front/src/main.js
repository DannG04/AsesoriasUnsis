import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import vuetify from './plugins/vuetify'
import 'vuetify/styles'
import { Icon } from '@iconify/vue'  // Importar Iconify
import '@mdi/font/css/materialdesignicons.css'
import Toast from "vue-toastification"
import "vue-toastification/dist/index.css"

// Configurar Axios
axios.defaults.baseURL = 'http://localhost:8080'

// Configurar interceptor para enviar el token en todas las solicitudes
const token = localStorage.getItem('token')
if (token) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
}

const app = createApp(App)
app.component('IconifyIcon', Icon)  // Registrar componente globalmente
app.use(vuetify)
app.use(router)
app.use(store)
app.use(Toast, {
    position: "bottom-right",
    timeout: 3000,
    closeOnClick: true,
    pauseOnFocusLoss: true,
    pauseOnHover: true,
    draggable: true,
    transition: "Vue-Toastification__bounce"
})
app.mount('#app')
