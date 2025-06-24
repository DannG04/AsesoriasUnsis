import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import axios from 'axios';
import vuetify from './plugins/vuetify';
import sessionPlugin from './plugins/sessionPlugin';
import 'vuetify/styles';
import { Icon } from '@iconify/vue'; // Importar Iconify
import '@mdi/font/css/materialdesignicons.css';
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';

// Configuración global de Axios
// Establece la URL base para todas las solicitudes HTTP
axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.headers.common['Content-Type'] = 'application/json'


// Configurar interceptor para enviar el token en todas las solicitudes
const token = localStorage.getItem('token');
if (token) {
    // Agrega el token de autenticación al encabezado si está disponible
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
}

// Crear la instancia principal de la aplicación Vue
const app = createApp(App);

// Registrar Iconify como un componente global
app.component('IconifyIcon', Icon);

// Usar Vuetify para el diseño de la interfaz
app.use(vuetify);

// Usar Vue Router para la navegación
app.use(router);

// Usar Vuex para el manejo del estado global
app.use(store);

// Configurar y usar Toast para notificaciones
app.use(Toast, {
    position: 'bottom-right', // Posición de las notificaciones
    timeout: 3000, // Duración de las notificaciones
    closeOnClick: true, // Cerrar al hacer clic
    pauseOnFocusLoss: true, // Pausar al perder el foco
    pauseOnHover: true, // Pausar al pasar el mouse
    draggable: true, // Permitir arrastrar la notificación
    transition: 'Vue-Toastification__bounce' // Efecto de transición
});

// Usar el plugin de sesión DESPUÉS de store y router
app.use(sessionPlugin);

// Montar la aplicación en el elemento con ID 'app'
app.mount('#app');
