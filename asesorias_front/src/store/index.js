import { createStore } from 'vuex'
import axios from 'axios'

let user = null;
try {
    const storedUser = localStorage.getItem('user');
    user = storedUser && storedUser !== 'undefined' ? JSON.parse(storedUser) : null;
} catch (error) {
    console.error('Error parsing user from localStorage:', error);
}

export default createStore({
    // Estado global de la aplicación
    state: {
        token: localStorage.getItem('token') || '', // Token de autenticación almacenado en localStorage
        user: user, // Información del usuario autenticado
        profesor: null, // Información del profesor autenticado
        status: '' // Estado de la autenticación (e.g., 'loading', 'success', 'error')
    },
    
    // Getters para acceder al estado global
    getters: {
        isLoggedIn: state => !!state.token, // Verifica si el usuario está autenticado
        authStatus: state => state.status, // Devuelve el estado de la autenticación
        currentUser: state => state.user, // Devuelve la información del usuario autenticado
        currentProfesor: state => state.profesor // Devuelve la información del profesor autenticado
    },

    // Mutations para modificar el estado global
    mutations: {
        auth_request(state) {
            state.status = 'loading'; // Cambia el estado a 'loading' durante la autenticación
        },
        auth_success(state, { token, user }) {
            state.status = 'success'; // Cambia el estado a 'success' después de la autenticación exitosa
            state.token = token; // Almacena el token de autenticación
            state.user = user; // Almacena la información del usuario
        },
        auth_error(state) {
            state.status = 'error'; // Cambia el estado a 'error' si la autenticación falla
        },
        logout(state) {
            state.status = ''; // Restablece el estado de autenticación
            state.token = ''; // Elimina el token de autenticación
            state.user = null; // Elimina la información del usuario
        },
        SET_TOKEN(state, token) {
            state.token = token; // Actualiza el token de autenticación
        },
        SET_USER(state, user) {
            state.user = user; // Actualiza la información del usuario
        },
        SET_PROFESOR(state, profesor) {
            state.profesor = profesor; // Actualiza la información del profesor
        }
    },

    // Acciones para realizar operaciones asíncronas
    actions: {
        login({ commit }, user) {
            return new Promise((resolve, reject) => {
                // Realiza una solicitud de inicio de sesión al backend
                axios.post('/api/auth/login', user)
                    .then(response => {
                        // Maneja la respuesta exitosa
                        const token = response.data.token;
                        const user = response.data.user;
                        localStorage.setItem('token', token); // Almacena el token en localStorage
                        localStorage.setItem('user', JSON.stringify(user)); // Almacena la información del usuario
                        commit('auth_success', { token, user }); // Actualiza el estado global
                        resolve(response);
                    })
                    .catch(error => {
                        // Maneja errores de autenticación
                        commit('auth_error');
                        localStorage.removeItem('token'); // Elimina el token en caso de error
                        localStorage.removeItem('user'); // Elimina la información del usuario
                        reject(error);
                    });
            });
        },
        register({ commit }, user) {
            return new Promise((resolve, reject) => {
                commit('auth_request'); // Cambia el estado a 'loading'
                // Realiza una solicitud de registro al backend
                axios.post('/api/auth/registro', user)
                    .then(response => {
                        resolve(response);
                    })
                    .catch(error => {
                        commit('auth_error'); // Cambia el estado a 'error' en caso de fallo
                        reject(error);
                    });
            });
        },
        logout({ commit }) {
            return new Promise(resolve => {
                commit('logout'); // Restablece el estado global
                localStorage.removeItem('token'); // Elimina el token de localStorage                localStorage.removeItem('user'); // Elimina la información del usuario
                resolve();
            });
        },
        forceLogout({ commit }) {
            // Acción para limpiar la sesión forzadamente cuando el backend no responde
            return new Promise(resolve => {
                commit('logout'); // Restablece el estado global
                localStorage.removeItem('token'); // Elimina el token de localStorage
                localStorage.removeItem('user'); // Elimina la información del usuario
                localStorage.removeItem('profesor'); // Elimina la información del profesor
                resolve();
            });
        },
        dataProfesor({ commit}) {
            return new Promise((resolve, reject) => {
                // Realiza una solicitud para obtener los datos del profesor
                axios.get('/profesores')
                    .then(response => {
                        const profesor = response.data; // Obtiene la información del usuario
                        commit('SET_PROFESOR', profesor); // Actualiza el estado global con la información del usuario
                        localStorage.setItem('profesor', JSON.stringify(profesor)); // Almacena la información del usuario en localStorage
                        resolve(response);
                    })
                    .catch(error => {
                        reject(error); // Maneja errores de la solicitud
                    });
            });

        }            
    }
});