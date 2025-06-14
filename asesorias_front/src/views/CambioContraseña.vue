<template>
    <div class="principal">
        <h1>SEGURIDAD</h1>
        <div class="general">
          <div class="contenido">
            <div class="izquierda">
                <label class="label">Usuario:</label>
                <label class="label">Contraseña Actual <label class="asterisco">*</label></label>
                <label class="label">Nueva Contraseña <label class="asterisco">*</label></label>
                <label class="label">Confirmar contraseña <label class="asterisco">*</label></label>
            </div>
            <div class="derecha">
                <label>{{ usuario }}</label>
                <input 
                  v-model="form.contrasenaActual"
                  class="input" 
                  type="password" 
                  placeholder="Contraseña Actual"
                  :class="{ 'input-error': errors.contrasenaActual }"
                />
                <span v-if="errors.contrasenaActual" class="error-text">{{ errors.contrasenaActual }}</span>
                
                <input 
                  v-model="form.nuevaContrasena"
                  class="input" 
                  type="password" 
                  placeholder="Nueva Contraseña"
                  :class="{ 'input-error': errors.nuevaContrasena }"
                />
                <span v-if="errors.nuevaContrasena" class="error-text">{{ errors.nuevaContrasena }}</span>
                
                <input 
                  v-model="form.confirmarContrasena"
                  class="input" 
                  type="password" 
                  placeholder="Confirmar Contraseña"
                  :class="{ 'input-error': errors.confirmarContrasena }"
                />
                <span v-if="errors.confirmarContrasena" class="error-text">{{ errors.confirmarContrasena }}</span>
            </div>
          </div>
          <div class="parrafo">
            <p>
            La contraseña deberá tener al menos 8 caracteres, al menos 1 dígito(s), al menos 1 minúscula(s),
            al menos 1 MAYÚSCULA(S) y al menos 1 caracter(es) no-alfanumérico(s) como . $ ? / * - + # @
            </p>
          </div>
          <!-- Botones de acción -->
          <div class="botones">
            <button class="boton-guardar" @click="cambiarContrasena" :disabled="loading">
              {{ loading ? 'Guardando...' : 'Guardar cambios' }}
            </button>
            <button class="boton-cancelar" @click="irAPerfil">Cancelar</button>
          </div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import api from '@/services/api'
import { useToast } from "vue-toastification"

export default {
  name: 'cambioContraseña',
  data() {
    return {
      usuario: '',
      loading: false,
      form: {
        contrasenaActual: '',
        nuevaContrasena: '',
        confirmarContrasena: ''
      },
      errors: {}
    }
  },
  computed: {
    ...mapGetters(['currentUser', 'currentProfesor'])
  },
  setup() {
    const toast = useToast()
    return { toast }
  },
  mounted() {
    // Obtener el usuario actual del store
    if (this.currentUser?.usuario) {
      this.usuario = this.currentUser.usuario
    } else if (this.currentProfesor?.nomProf) {
      // Si no hay usuario en el store, usar datos del profesor
      this.usuario = this.currentProfesor.nomProf
    } else {
      // Fallback temporal
      this.usuario = '2022060488li'
    }
  },
  methods: {
    validateForm() {
      this.errors = {}
      let isValid = true

      // Validar contraseña actual
      if (!this.form.contrasenaActual) {
        this.errors.contrasenaActual = 'La contraseña actual es requerida'
        isValid = false
      }

      // Validar nueva contraseña
      if (!this.form.nuevaContrasena) {
        this.errors.nuevaContrasena = 'La nueva contraseña es requerida'
        isValid = false
      } else if (this.form.nuevaContrasena.length < 8) {
        this.errors.nuevaContrasena = 'La contraseña debe tener al menos 8 caracteres'
        isValid = false
      } else if (!this.validatePasswordStrength(this.form.nuevaContrasena)) {
        this.errors.nuevaContrasena = 'La contraseña debe cumplir con los requisitos de seguridad'
        isValid = false
      }

      // Validar confirmación
      if (!this.form.confirmarContrasena) {
        this.errors.confirmarContrasena = 'Debe confirmar la nueva contraseña'
        isValid = false
      } else if (this.form.nuevaContrasena !== this.form.confirmarContrasena) {
        this.errors.confirmarContrasena = 'Las contraseñas no coinciden'
        isValid = false
      }

      return isValid
    },

    validatePasswordStrength(password) {
      // Al menos 1 dígito, 1 minúscula, 1 mayúscula, 1 carácter especial
      const hasDigit = /\d/.test(password)
      const hasLower = /[a-z]/.test(password)
      const hasUpper = /[A-Z]/.test(password)
      const hasSpecial = /[.$ ? / * \- + # @]/.test(password)
      
      return hasDigit && hasLower && hasUpper && hasSpecial
    },

    async cambiarContrasena() {
      if (!this.validateForm()) {
        return
      }

      this.loading = true
      
      try {
        await api.cambiarContrasena(
          this.usuario,
          this.form.contrasenaActual,
          this.form.nuevaContrasena
        )

        this.toast.success('Contraseña cambiada exitosamente', {
          timeout: 5000
        })

        // Limpiar formulario
        this.form = {
          contrasenaActual: '',
          nuevaContrasena: '',
          confirmarContrasena: ''
        }
        this.errors = {}

        // Redirigir al perfil después de un momento
        setTimeout(() => {
          this.$router.push('/perfil')
        }, 2000)

      } catch (error) {
        console.error('Error al cambiar contraseña:', error)
        
        let errorMessage = 'Error al cambiar la contraseña'
        if (error.response?.data) {
          errorMessage = error.response.data
        }
        
        this.toast.error(errorMessage, {
          timeout: 5000
        })
      } finally {
        this.loading = false
      }
    },

    irAPerfil() {
      this.$router.push('/perfil')
    }
  }
}
</script>

<style scoped>

/*Todo el contenedor */
.principal {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    top: 50%;
    left: 50%;
    width: 90%;
    height: auto;
    padding-left: 10%;
    gap: 40px; 
    padding-top: 60px;
    align-content: center;
}

/*Contenedor general del contenido */
.general {
    background-color: #DDE5FF;
    width: 80%;
    height: 100%;
    border-radius: 30px;
    flex-direction: column;
    display: flex;
    align-items: center;
}

/*Contenedor general del contenido  */
.contenido {
    flex-grow: 1;
    background-color: #DDE5FF;
    width: 100%;
    height: 100%;
    border-radius: 30px;
    flex-direction: row;
    display: flex;
}

.izquierda {
    display: flex;
    flex: 1;
    flex-direction: column;
    align-items: start;
    justify-content: space-between;
    width: 100%;
    height: 100%;
    padding: 10%;
    gap: 30px;
}

.derecha {
    display: flex;
    flex: 1;
    flex-direction: column;
    align-items: start;
    justify-content: space-between;
    width: 100%;
    height: 100%;
    padding: 10%;
    margin-right: auto;
    gap: 20px;
}

.botones {
    display: flex;
    flex-grow: 1;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    gap: 30px;
    padding-bottom: 20px;
}

h1 {
    font-weight: bold;
    font-size: 300%;
}

.parrafo {
    flex-grow: 1;
    color: #7E828F;
    text-align: center;
    padding-left: 10%;
    padding-right: 10%;
    padding-bottom: 2px;
    padding-top: 0; /* Eliminar espacio superior */
    margin-top: -10px; /* Acercar más al contenido */
    width: auto;
    height: auto;
}

.label {
  font-size: 120%;
  font-weight: bold;  
  white-space: nowrap;
}

.asterisco {
  color: red;
  font-weight: bold;
}

.input {
  width: 100%;
  height: 50px;
  border-radius: 5px;
  padding: 5px;
  border: 1px solid #ccc;
  background-color: #fff;
}

.input-error {
  border: 2px solid #F6888A !important;
  background-color: #ffeaea;
}

.error-text {
  color: #F6888A;
  font-size: 12px;
  margin-top: 2px;
  font-weight: 500;
}

.boton-guardar {
  cursor: pointer;
  background-color: #B7C3E8;
  text-align: center;
  padding: 10px;
  min-width: 120px;
  min-height: 50px;
  display: inline-block;
  font-weight: 600;
} 

.boton-guardar:disabled {
  background-color: #ccc;
  cursor: not-allowed;
  opacity: 0.6;
}

.boton-cancelar {
  cursor: pointer;
  background-color: #F6888A;
  text-align: center;
  padding: 10px;
  min-width: 120px;
  min-height: 50px;
  display: inline-block;
  font-weight: 600;
}

/*Estilos responsivos */
@media (max-width: 768px) {
  .contenido {
    flex-direction: column; /* Apila las columnas verticalmente */
    gap: 10px;
  }

  .izquierda, .derecha {
    padding: 5%; /* Reduce el padding en pantallas pequeñas */
    width: 100%; /* Ocupan todo el ancho */
  }

  .botones {
    flex-direction: column; /* Apila los botones verticalmente */
    gap: 10px;
  }

  .parrafo {
    padding-left: 10%;
    padding-right: 10%;
  }
}

@media (max-width: 480px) {
  .principal {
    padding-left: 5%; /* Reduce el padding en pantallas muy pequeñas */
  }

  .label {
    font-size: 100%; /* Reduce el tamaño del texto */
  }

  .input {
    height: 40px; /* Reduce la altura de los inputs */
  }

  .boton-guardar, .boton-cancelar {
    min-width: 100px; /* Reduce el tamaño mínimo de los botones */
    min-height: 40px;
  }
}


</style>