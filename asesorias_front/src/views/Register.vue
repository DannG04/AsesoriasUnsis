<template>
    <div class="auth-container">
        <div class="auth-form">
            <h2>Registro de Alumno</h2>
            <div v-if="error" class="error-message">{{ error }}</div>
            <div v-if="success" class="success-message">{{ success }}</div>
            <form @submit.prevent="register">
                <div class="form-group">
                    <label for="nombre">Nombre de Usuario</label>
                    <input type="text" id="nombre" v-model="user.nombre" required
                        placeholder="Ingrese su nombre de usuario">
                </div>
                <div class="form-group">
                    <label for="contrasenia">Contraseña</label>
                    <input type="password" id="contrasenia" v-model="user.contrasenia" required
                        placeholder="Ingrese su contraseña">
                </div>
                <div class="form-group">
                    <label for="confirmarContrasenia">Confirmar Contraseña</label>
                    <input type="password" id="confirmarContrasenia" v-model="confirmarContrasenia" required
                        placeholder="Confirme su contraseña">
                </div>
                <button type="submit" class="btn-primary" :disabled="loading">
                    {{ loading ? 'Registrando...' : 'Registrarse' }}
                </button>
            </form>
            <div class="auth-links">
                <p>¿Ya tienes una cuenta? <router-link to="/login">Inicia Sesión</router-link></p>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'RegisterUser',
    data() {
        return {
            user: {
                nombre: '',
                contrasenia: ''
            },
            confirmarContrasenia: '',
            error: '',
            success: '',
            loading: false
        }
    },
    methods: {
        register() {
            this.error = ''
            this.success = ''

            // Validar que las contraseñas coincidan
            if (this.user.contrasenia !== this.confirmarContrasenia) {
                this.error = 'Las contraseñas no coinciden'
                return
            }

            this.loading = true

            this.$store.dispatch('register', this.user)
                .then(() => {
                    this.success = 'Registro exitoso. Redirigiendo al login...'
                    setTimeout(() => {
                        this.$router.push('/login')
                    }, 2000)
                })
                .catch(err => {
                    this.error = err.response?.data || 'Error en el registro'
                })
                .finally(() => {
                    this.loading = false
                })
        }
    }
}
</script>

<style scoped>
.auth-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-color: #f5f5f5;
}

.auth-form {
    width: 400px;
    padding: 2rem;
    background-color: white;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h2 {
    text-align: center;
    margin-bottom: 1.5rem;
    color: #333;
}

.form-group {
    margin-bottom: 1.5rem;
}

label {
    display: block;
    margin-bottom: 0.5rem;
    font-weight: 500;
}

input {
    width: 100%;
    padding: 0.75rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1rem;
}

.btn-primary {
    width: 100%;
    padding: 0.75rem;
    background-color: #3498db;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.3s;
}

.btn-primary:hover {
    background-color: #2980b9;
}

.btn-primary:disabled {
    background-color: #95a5a6;
    cursor: not-allowed;
}

.auth-links {
    margin-top: 1.5rem;
    text-align: center;
}

.auth-links a {
    color: #3498db;
    text-decoration: none;
}

.auth-links a:hover {
    text-decoration: underline;
}

.error-message {
    background-color: #f8d7da;
    color: #721c24;
    padding: 0.75rem;
    margin-bottom: 1rem;
    border-radius: 4px;
    text-align: center;
}

.success-message {
    background-color: #d4edda;
    color: #155724;
    padding: 0.75rem;
    margin-bottom: 1rem;
    border-radius: 4px;
    text-align: center;
}
</style>