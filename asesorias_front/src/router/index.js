import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import Home from '../views/Home.vue';
import RegistroAsesorias from '../views/RegistroAsesorias.vue';
import store from '../store';

// Definir constantes para las rutas principales
const LOGIN_ROUTE = '/login';
const REGISTER_ROUTE = '/register';
const HOME_ROUTE = '/home';
const REGISTRO_ASESORIAS_ROUTE = '/registro-asesorias';

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
    meta: { requiresAuth: true } // Requiere autenticación
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
  }
];

// Crear la instancia del router
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

// Protección de rutas
// Verifica si el usuario está autenticado antes de acceder a ciertas rutas
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // Si la ruta requiere autenticación y el usuario no está autenticado
    if (!store.getters.isLoggedIn) {
      next(LOGIN_ROUTE); // Redirige al inicio de sesión
      return;
    }
    next();
  } else if (to.matched.some(record => record.meta.guest)) {
    // Si la ruta es solo para invitados y el usuario ya está autenticado
    if (store.getters.isLoggedIn) {
      next(HOME_ROUTE); // Redirige a la página principal
      return;
    }
    next();
  } else {
    next(); // Permite el acceso a rutas sin restricciones
  }
});

export default router;