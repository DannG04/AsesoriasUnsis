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
                      <input 
                        type="text" 
                        class="form-control border-bottom rounded-0 input-field" 
                        id="nombre"
                        v-model="user.nombre" 
                        placeholder="Ingresa tu usuario"
                        aria-label="Nombre de usuario" />
                    </div>

                    <div class="mb-3">
                      <label for="contrasenia" class="form-label">Contraseña</label>
                      <input 
                        type="password" 
                        class="form-control border-bottom rounded-0 input-field" 
                        id="contrasenia"
                        v-model="user.contrasenia" 
                        placeholder="Ingresa tu contraseña"
                        aria-label="Contraseña" />
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
                <div class="col-md-6 d-none d-md-flex align-items-center p-0 overflow-hidden">
                  <div class="image-container w-100 h-100 d-flex align-items-center justify-content-center p-4">
                    <img 
                      src="@/assets/pc.png" 
                      alt="Laptop con gráficos" 
                      class="img-fluid login-image" 
                    />
                  </div>
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
        nombre:'', // Campo para el nombre de usuario
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
      this.user.nombre = this.user.nombre.trim(); 
      console.log('Intentando iniciar sesión con:', this.user);

      this.$store.dispatch('login', this.user)
        .then(response => {
          console.log('Login exitoso:', response);
          console.log('Token guardado:', localStorage.getItem('token'));
          this.$router.push('/Home');
          this.toast.success('Inicio de sesión exitoso. Bienvenido!');
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
  transition: all 0.3s ease;
}

.botonPrimario:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* Estilo para el logo */
.logo {
  max-width: 150px;
  transition: transform 0.3s ease;
}

.logo:hover {
  transform: scale(1.05);
}

/* Estilos para la sección de imagen */
.image-container {
  background-color: #ffffff;
  min-height: 100%;
  transition: all 0.3s ease;
}

.login-image {
  max-width: 100%;
  height: auto;
  max-height: 80vh;
  object-fit: contain;
  transition: transform 0.5s ease;
}

.login-image:hover {
  transform: scale(1.03);
}

/* Estilos para los campos de entrada */
.input-field {
  border: 1px solid #ced4da !important;
  border-radius: 4px !important;
  padding: 10px 15px !important;
  transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1) !important;
  box-sizing: border-box;
  width: 100%;
  height: calc(1.5em + 1rem + 2px);
  position: relative;
  background-color: transparent;
  z-index: 1;
}

.input-field:focus {
  border-color: #9AD7A7 !important;
  box-shadow: 0 0 0 0.15rem rgba(154, 215, 167, 0.25) !important;
  outline: none !important;
}

/* Efecto de borde inferior al enfocar */
.input-field::after {
  content: '';
  position: absolute;
  bottom: -1px;
  left: 0;
  right: 0;
  height: 2px;
  background-color: #9AD7A7;
  transform: scaleX(0);
  transform-origin: bottom center;
  transition: transform 0.3s ease;
  z-index: 2;
}

.input-field:focus::after {
  transform: scaleX(1);
}

.input-field::placeholder {
  color: #6c757d;
  opacity: 0.7;
}

/* Ajustes responsivos */
@media (max-width: 991.98px) {
  .login-image {
    max-height: 50vh;
  }
}
</style>