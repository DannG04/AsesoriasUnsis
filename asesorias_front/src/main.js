import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import vuetify from './plugins/vuetify'
import 'vuetify/styles'

// Configurar Axios
axios.defaults.baseURL = 'http://localhost:8080'

// Configurar interceptor para enviar el token en todas las solicitudes
const token = localStorage.getItem('token')
if (token) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
}

const app = createApp(App)
app.use(vuetify)
app.use(router)
app.use(store)
app.mount('#app')
