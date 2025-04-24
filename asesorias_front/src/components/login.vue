<template>
  <div class="vh-100 d-flex align-items-center" style="background-color: #9AD7A7;">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-10">
          <div class="card border-0 shadow-lg rounded-3">
            <div class="card-body p-0">
              <div class="row g-0">
                <!-- Lado izquierdo - Formulario de inicio de sesión -->
                <div class="col-md-6 p-4 p-md-5">
                  <div class="text-center mb-4">
                    <img 
                      src="@/assets/logo-unsis.png" 
                      alt="Logo UNSIS" 
                      class="img-fluid mb-4" 
                      style="max-width: 150px;"
                    />
                  </div>
                  
                  <h2 class="text-center fw-bold mb-4">INICIAR SESIÓN</h2>
                  
                  <form @submit.prevent="iniciarSesion">
                    <div class="mb-4">
                      <label for="username" class="form-label">Nombre de usuario</label>
                      <input 
                        type="text" 
                        class="form-control border-0 border-bottom rounded-0" 
                        id="username"
                        v-model="nombreUsuario"
                      />
                    </div>
                    
                    <div class="mb-3">
                      <label for="password" class="form-label">Contraseña</label>
                      <input 
                        type="password" 
                        class="form-control border-0 border-bottom rounded-0" 
                        id="password"
                        v-model="contraseña"
                      />
                    </div>
                    
                    <div class="text-end mb-4">
                      <a href="#" class="text-muted small text-decoration-none">¿Olvidaste la contraseña?</a>
                    </div>
                    
                    <div class="d-grid">
                      <button 
                        type="submit" 
                        class="btn btn-success botonPrimario"
                      >
                        Ingresar
                      </button>
                    </div>
                  </form>
                </div>
                
                <!-- Lado derecho - Usando la imagen de pc.png -->
                <div class="col-md-6 d-none d-md-flex align-items-center justify-content-center p-4">
                  <img 
                    src="@/assets/pc.png" 
                    alt="Laptop con gráficos" 
                    class="img-fluid"
                  />
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
export default {
  name: 'InicioSesionUNSIS',
  data() {
    return {
      nombreUsuario: '',
      contraseña: ''
    }
  },
  methods: {
    iniciarSesion() {
      console.log('Intento de inicio de sesión con:', { 
        usuario: this.nombreUsuario, 
        clave: this.contraseña 
      });
      // Aquí iría la lógica real de autenticación
    }
  }
}
</script>
<style scoped>
  .botonPrimario {
    background-color: #9AD7A7;
  }
</style>