import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import Home from '../views/Home.vue';
import EstadisticasView from '@/views/Estadisticas.vue';
import RegistroAsesorias from '../views/RegistroAsesorias.vue';
import PerfilView from '../views/Perfil.vue';
import store from '../store';
import HistorialAsesorias from "@/views/HistorialAsesorias.vue";
import CambioContraseña  from '@/views/CambioContraseña.vue';
import DatosUsuario from '@/views/DatosUsuario.vue';

// Definir constantes para las rutas principales
const LOGIN_ROUTE = '/login';
const REGISTER_ROUTE = '/register';
const HOME_ROUTE = '/home';
const REGISTRO_ASESORIAS_ROUTE = '/registro-asesorias';
const ESTADISTICAS_ROUTE = '/estadisticas';
const PERFIL_ROUTE = '/perfil';
const HISTORIAL_ASESORIAS_ROUTE = '/historial-asesorias';
const CAMBIO_CONTRASENA_ROUTE = '/cambio-contrasena';
const MODIFICAR_PERFIL_ROUTE = '/Datos-Usuario';

// Definir las rutas de la aplicación
const routes = [
  {
    path: '/',
    redirect: LOGIN_ROUTE // Redirige a la página de inicio de sesión
  },
  {
    path: LOGIN_ROUTE,
    name: 'Login',
    component: Login,
    meta: { guest: true } // Solo accesible para usuarios no autenticados
  },
  {
    path: REGISTER_ROUTE,
    name: 'Register',
    component: Register,
    meta: { requiresAuth: false } // Requiere autenticación
  },
  {
    path: HOME_ROUTE,
    name: 'Home',
    component: Home,
    meta: { requiresAuth: true } // Requiere autenticación
  },
  {
    path: REGISTRO_ASESORIAS_ROUTE,
    name: 'RegistroAsesorias',
    component: RegistroAsesorias,
    meta: { requiresAuth: true } // Requiere autenticación
  }, {
    path: HISTORIAL_ASESORIAS_ROUTE,
    name: 'HistorialAsesorias',
    component: HistorialAsesorias,
    meta: { requiresAuth: false }
  },
  {
    path: ESTADISTICAS_ROUTE,
    name: 'EstadisticasView',
    component: EstadisticasView,
    meta: { requiresAuth: false } // Requiere autenticación
},{
    path: PERFIL_ROUTE,
    name: 'PerfilView',
    component: PerfilView,
    meta: { requiresAuth: false } // Requiere autenticación
  },
  {
    path: CAMBIO_CONTRASENA_ROUTE,
    name: 'CambioContraseña',
    component: CambioContraseña,
    meta: { requiresAuth: false } // Requiere autenticación
  },
  {
    path: MODIFICAR_PERFIL_ROUTE,
    name: 'DatosUsuario',
    component: DatosUsuario,
    meta: { requiresAuth: false } // Requiere autenticación
  },
  
];

// Crear la instancia del router
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

// Protección de rutas
// Verifica si el usuario está autenticado antes de acceder a ciertas rutas
router.beforeEach((to, from, next) => {
  const isLoggedIn = store.getters.isLoggedIn;
  const token = localStorage.getItem('token');
  
  // Verificar si hay token pero el store no tiene información de login
  if (token && !isLoggedIn) {
    // Limpiar datos inconsistentes
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('profesor');
    store.commit('logout');
  }
  
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // Si la ruta requiere autenticación y el usuario no está autenticado
    if (!store.getters.isLoggedIn || !localStorage.getItem('token')) {
      // Limpiar datos por seguridad
      store.dispatch('forceLogout');
      next(LOGIN_ROUTE); // Redirige al inicio de sesión
      return;
    }
    next();
  } else if (to.matched.some(record => record.meta.guest)) {
    // Si la ruta es solo para invitados y el usuario ya está autenticado
    if (store.getters.isLoggedIn && localStorage.getItem('token')) {
      next(HOME_ROUTE); // Redirige a la página principal
      return;
    }
    next();
  } else {
    next(); // Permite el acceso a rutas sin restricciones
  }
});

export default router;