<template>
  <!-- Contenedor principal con diseño responsivo y fondo personalizado -->
  <div class="vh-100 d-flex align-items-center login-background">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-10">
          <div class="card border-0 shadow-lg rounded-3">
            <div class="card-body p-0">
              <div class="row g-0">
                <!-- Lado izquierdo - Formulario de inicio de sesión -->
                <div class="col-md-6 p-4 p-md-5">
                  <div class="text-center mb-4">
                    <!-- Logo de la institución -->
                    <img src="@/assets/logo-unsis.png" alt="Logo UNSIS" class="img-fluid mb-4 logo" />
                  </div>

                  <h2 class="text-center fw-bold mb-4">INICIAR SESIÓN</h2>

                  <!-- Formulario de inicio de sesión -->
                  <form @submit.prevent="login">
                    <div class="mb-4">
                      <label for="nombre" class="form-label">Nombre de usuario</label>
                      <input type="text" class="form-control border-0 border-bottom rounded-0" id="nombre"
                        v-model="user.nombre" aria-label="Nombre de usuario" />
                    </div>

                    <div class="mb-3">
                      <label for="contrasenia" class="form-label">Contraseña</label>
                      <input type="password" class="form-control border-0 border-bottom rounded-0" id="contrasenia"
                        v-model="user.contrasenia" aria-label="Contraseña" />
                    </div>

                    <div class="text-end mb-4">
                      <a href="#" class="text-muted small text-decoration-none">¿Olvidaste la contraseña?</a>
                    </div>

                    <div class="d-grid">
                      <button type="submit" class="btn btn-success botonPrimario">
                        {{ loading ? 'Iniciando...' : 'Iniciar Sesión' }}
                      </button>
                    </div>
                  </form>
                </div>

                <!-- Lado derecho - Imagen decorativa -->
                <div class="col-md-6 d-none d-md-flex align-items-center justify-content-center p-4">
                  <img src="@/assets/pc.png" alt="Laptop con gráficos" class="img-fluid" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useToast } from "vue-toastification";

export default {
  name: 'LoginComponent',
  data() {
    return {
      user: {
        nombre: '', // Campo para el nombre de usuario
        contrasenia: '' // Campo para la contraseña
      },
      error: '', // Mensaje de error
      loading: false, // Indicador de carga
      toast: null // Instancia de toast para notificaciones
    };
  },
  created() {
    // Inicializa la instancia de toast
    this.toast = useToast();
  },
  methods: {
    /**
     * Método para manejar el inicio de sesión.
     * Realiza una solicitud al backend y maneja los errores.
     */
    login() {
      this.loading = true;
      this.error = '';

      console.log('Intentando iniciar sesión con:', this.user);

      this.$store.dispatch('login', this.user)
        .then(response => {
          console.log('Login exitoso:', response);
          console.log('Token guardado:', localStorage.getItem('token'));
          this.$router.push('/Home');
        })
        .catch(err => {
          console.error('Error completo:', err);
          if (err.response && err.response.status === 401) {
            // Usamos toast.error en lugar del mensaje en el div
            this.toast.error('Credenciales incorrectas. Por favor, verifique su usuario y contraseña.');
          } else {
            this.toast.error('Error al iniciar sesión. Por favor, intente nuevamente más tarde.');
          }
        })
        .finally(() => {
          this.loading = false;
        });
    }
  }
};
</script>

<style scoped>
/* Fondo personalizado para la página de inicio de sesión */
.login-background {
  background-color: #9AD7A7;
}

/* Estilo para el botón principal */
.botonPrimario {
  background-color: #9AD7A7;
}

/* Estilo para el logo */
.logo {
  max-width: 150px;
}
</style>