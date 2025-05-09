import { createStore } from 'vuex'
import axios from 'axios'

export default createStore({
    state: {
        token: localStorage.getItem('token') || '',
        user: JSON.parse(localStorage.getItem('user')) || null,
        status: ''
    },
    getters: {
        isLoggedIn: state => !!state.token,
        authStatus: state => state.status,
        currentUser: state => state.user
    },
    mutations: {
        auth_request(state) {
            state.status = 'loading'
        },
        auth_success(state, { token, user }) {
            state.status = 'success'
            state.token = token
            state.user = user
        },
        auth_error(state) {
            state.status = 'error'
        },
        logout(state) {
            state.status = ''
            state.token = ''
            state.user = null
        },
        SET_TOKEN(state, token) {
            state.token = token;
        },
        SET_USER(state, user) {
            state.user = user;
        }
    },
    actions: {
        login({ commit }, user) {
            return new Promise((resolve, reject) => {
                axios.post('/api/auth/login', user)
                    .then(response => {
                        const token = response.data.token;
                        console.log('Token recibido:', token);
                        localStorage.setItem('token', token);
                        commit('SET_TOKEN', token);
                        commit('SET_USER', response.data.user || {});
                        resolve(response);
                    })
                    .catch(error => {
                        console.error('Error en login:', error);
                        reject(error);
                    });
            });
        },
        register({ commit }, user) {
            return new Promise((resolve, reject) => {
                commit('auth_request')
                axios.post('/api/auth/registro', user)
                    .then(response => {
                        resolve(response)  // No se actualiza el estado después del registro
                    })
                    .catch(err => {
                        commit('auth_error')
                        reject(err)
                    })
            })
        },
        logout({ commit }) {
            return new Promise(resolve => {
                commit('logout')
                localStorage.removeItem('token')
                localStorage.removeItem('user')
                delete axios.defaults.headers.common['Authorization']
                resolve()
            })
        }
    }
})