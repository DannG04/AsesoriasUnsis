<template>
  <div class="container">
    <div class="registro-header">
      <p class="subtitle">Panel de administración - Crear nuevos usuarios</p>
    </div>

    <div class="registro-content">
      <div class="registro-card">
        <div class="card-header">
          <h3>Nuevo Usuario</h3>
          <p>Complete los datos para registrar un nuevo usuario en el sistema</p>
        </div>

        <!-- Formulario de registro -->
        <form @submit.prevent="register" class="registro-form">
          <div class="form-row">
            <div class="form-group">
              <label for="nombre" class="form-label">
                <IconifyIcon icon="material-symbols:person-outline" width="20" height="20" />
                Nombre de usuario
              </label>
              <input 
                type="text" 
                class="form-control" 
                id="nombre"
                v-model="user.nombre" 
                placeholder="Ingrese el nombre de usuario"
                required />
            </div>

            <div class="form-group">
              <label for="contrasenia" class="form-label">
                <IconifyIcon icon="material-symbols:lock-outline" width="20" height="20" />
                Contraseña
              </label>
              <input 
                type="password" 
                class="form-control" 
                id="contrasenia"
                v-model="user.contrasenia" 
                placeholder="Ingrese la contraseña"
                required />
            </div>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label for="rol" class="form-label">
                <IconifyIcon icon="material-symbols:admin-panel-settings-outline" width="20" height="20" />
                Rol del usuario
              </label>
              <select 
                class="form-control" 
                id="rol" 
                v-model="user.rol"
                required>
                <option value="">Seleccione un rol</option>
                <option value="admin">Administrador</option>
                <option value="user">Usuario</option>
              </select>
            </div>

            <div class="form-group">
              <label for="idProfesor" class="form-label">
                <IconifyIcon icon="material-symbols:school-outline" width="20" height="20" />
                ID del Profesor (opcional)
              </label>
              <input 
                type="number" 
                class="form-control" 
                id="idProfesor"
                v-model="user.idProfesor" 
                placeholder="ID del profesor asociado" />
            </div>
          </div>

          <div class="form-actions">
            <button type="button" class="btn btn-secondary" @click="resetForm">
              <IconifyIcon icon="material-symbols:refresh" width="20" height="20" />
              Limpiar
            </button>
            <button type="submit" class="btn btn-primary" :disabled="loading">
              <IconifyIcon icon="material-symbols:person-add-outline" width="20" height="20" />
              {{ loading ? 'Registrando...' : 'Registrar Usuario' }}
            </button>
          </div>
        </form>
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
                nombre: '', // Campo para el nombre de usuario
                contrasenia: '', // Campo para la contraseña
                rol: '', // Campo para el rol del usuario
                idProfesor: null // Campo opcional para el ID del profesor
            },
            loading: false // Indicador de carga
        };
    },    methods: {
        /**
         * Método para manejar el registro de un nuevo usuario.
         * Realiza una solicitud al backend y maneja los errores.
         */
        register() {
            this.loading = true;

            this.$store.dispatch('register', this.user)
                .then(() => {
                    // Mostrar mensaje de éxito y limpiar formulario
                    alert('Usuario registrado exitosamente');
                    this.resetForm();
                })
                .catch(err => {
                    console.error('Error al registrar usuario:', err);
                    alert('Error al registrar usuario. Por favor, intente nuevamente.');
                })
                .finally(() => {
                    this.loading = false;
                });
        },

        /**
         * Método para limpiar el formulario
         */
        resetForm() {
            this.user = {
                nombre: '',
                contrasenia: '',
                rol: '',
                idProfesor: null
            };
        }
    }
};
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 3rem 2rem 2rem 2rem;
  min-height: auto;
}

.registro-header {
  text-align: center;
  margin-bottom: 2rem;
}

.registro-header h1 {
  font-size: 2.5rem;
  font-weight: 700;
  color: #333;
  margin-bottom: 0.5rem;
}

.subtitle {
  font-size: 1.1rem;
  color: #666;
  margin: 0;
}

.registro-content {
  display: flex;
  justify-content: center;
}

.registro-card {
  background-color: #DDE5FF;
  padding: 2rem;
  border-radius: 30px;
  width: 100%;
  max-width: 800px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.card-header {
  text-align: center;
  margin-bottom: 2rem;
}

.card-header h3 {
  font-size: 1.8rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 0.5rem;
}

.card-header p {
  color: #666;
  margin: 0;
}

.registro-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 1rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 0.5rem;
}

.form-control {
  padding: 0.75rem 1rem;
  border: 2px solid #B7C3E8;
  border-radius: 10px;
  font-size: 1rem;
  background-color: #fff;
  transition: all 0.3s ease;
}

.form-control:focus {
  outline: none;
  border-color: #7A8ECC;
  box-shadow: 0 0 0 3px rgba(122, 142, 204, 0.1);
}

.form-control::placeholder {
  color: #999;
}

select.form-control {
  cursor: pointer;
}

.form-actions {
  display: flex;
  justify-content: center;
  gap: 1rem;
  margin-top: 1rem;
}

.btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 25px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
}

.btn-primary {
  background: linear-gradient(135deg, #7A8ECC, #98A2C3);
  color: white;
  box-shadow: 0 4px 15px rgba(122, 142, 204, 0.3);
}

.btn-primary:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(122, 142, 204, 0.4);
  background: linear-gradient(135deg, #6B7FB8, #8591B8);
}

.btn-primary:disabled {
  background: #95a5a6;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

.btn-secondary {
  background-color: #B7C3E8;
  color: #333;
  box-shadow: 0 4px 15px rgba(183, 195, 232, 0.3);
}

.btn-secondary:hover {
  background-color: #A5B8E0;
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(183, 195, 232, 0.4);
}

/* Responsive Design */
@media (max-width: 768px) {
  .container {
    padding: 2rem 1rem;
  }

  .registro-header h1 {
    font-size: 2rem;
  }

  .registro-card {
    padding: 1.5rem;
  }

  .form-row {
    grid-template-columns: 1fr;
    gap: 1rem;
  }

  .form-actions {
    flex-direction: column;
    align-items: stretch;
  }

  .btn {
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .container {
    padding: 1rem 0.5rem;
  }

  .registro-header h1 {
    font-size: 1.5rem;
  }

  .subtitle {
    font-size: 1rem;
  }

  .registro-card {
    padding: 1rem;
    border-radius: 20px;
  }

  .card-header h3 {
    font-size: 1.5rem;
  }
}
</style>