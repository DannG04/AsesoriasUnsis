<template>
    <!-- Contenedor principal para el formulario de registro -->
    <div class="auth-container">
        <div class="auth-form">
            <h2>Registro de Usuario</h2>

            <!-- Formulario de registro -->
            <form @submit.prevent="register">
                <div class="form-group">
                    <label for="nombre">Nombre de usuario</label>
                    <input type="text" id="nombre" v-model="user.nombre" placeholder="Ingrese su nombre de usuario" />
                </div>

                <div class="form-group">
                    <label for="contrasenia">Contraseña</label>
                    <input type="password" id="contrasenia" v-model="user.contrasenia"
                        placeholder="Ingrese su contraseña" />
                </div>

                <div class="form-group">
                    <label for="rol">Rol</label>
                    <select id="rol" v-model="user.rol">
                        <option value="">Seleccione un rol</option>
                        <option value="admin">Administrador</option>
                        <option value="user">Usuario</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="idProfesor">ID del Profesor (opcional)</label>
                    <input type="number" id="idProfesor" v-model="user.idProfesor"
                        placeholder="Ingrese el ID del profesor" />
                </div>

                <button type="submit" class="btn-primary" :disabled="loading">
                    {{ loading ? 'Registrando...' : 'Registrar' }}
                </button>

                <div class="auth-links">
                    <p>¿Ya tienes una cuenta? <a href="/login">Inicia sesión</a></p>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
export default {
    name: 'RegisterUser',
    data() {
        return {
            user: {
                nombre: '', // Campo para el nombre de usuario
                contrasenia: '', // Campo para la contraseña
                rol: '', // Campo para el rol del usuario
                idProfesor: null // Campo opcional para el ID del profesor
            },
            loading: false // Indicador de carga
        };
    },
    methods: {
        /**
         * Método para manejar el registro de un nuevo usuario.
         * Realiza una solicitud al backend y maneja los errores.
         */
        register() {
            this.loading = true;

            this.$store.dispatch('register', this.user)
                .then(() => {
                    this.$router.push('/login'); // Redirige al inicio de sesión tras el registro exitoso
                })
                .catch(err => {
                    console.error('Error al registrar usuario:', err);
                    alert('Error al registrar usuario. Por favor, intente nuevamente.');
                })
                .finally(() => {
                    this.loading = false;
                });
        }
    }
};
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

input,
select {
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
</style>