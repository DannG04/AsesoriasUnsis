<template>
  <div class="principal">
    <h1>DATOS DEL USUARIO</h1>
    <div class="general">
      <div class="contenido">
        <label class="label">Descripción:</label>
        <textarea class="input" v-model="descripcion" @input="validarLongitud"></textarea>
        <p class="contador">{{ descripcion.length }}/400 caracteres</p>
      </div>
      <!-- Botones de acción -->
      <div class="botones">
        <button class="boton-guardar" @click="guardarCambios">Guardar cambios</button>
        <button class="boton-cancelar" @click="cancelarCambios">Cancelar</button>
      </div>
    </div>
  </div>
</template>

<script>
import api from '@/services/api';
import { useToast } from "vue-toastification";

export default {
  name: 'DatosUsuario',
  data() {
    return {
      descripcion: '',
      descripcionOriginal: '', // Para poder cancelar cambios
      idProfesor: null,
      toast: null // Instancia de toast para notificaciones
    }
  },
  computed: {
    currentProfesor() {
      return this.$store.getters.currentProfesor;
    }
  },
  created() {
    // Inicializa la instancia de toast
    this.toast = useToast();
  },
  methods: {
    validarLongitud() {
      if (this.descripcion.length > 400) {
        this.descripcion = this.descripcion.slice(0, 400); // Trunca el texto a 400 caracteres
      }
    },
    async obtenerDescripcionProfesor() {
      try {
        const response = await api.getDatosPerfilProfesor();
        const datos = response.data || response;
        console.log('Datos del perfil obtenidos:', datos);

        if (Array.isArray(datos) && datos.length > 0) {
          const perfilData = datos[0];
          this.descripcion = perfilData.descripcion || perfilData.descripsion || '';
          this.descripcionOriginal = this.descripcion; // Guardar como original
          console.log('Descripción cargada:', this.descripcion);
        } else {
          console.warn('No se encontraron datos del perfil');
          // Mantener la descripción por defecto si no hay datos
          this.descripcion = 'Doctor en Ciencias de la Computación especializado en Inteligencia Artificial. Imparto cursos de Algoritmos y Minería de Datos. 15 años de experiencia docente. Disponible para asesorías en proyectos de investigación y tesis relacionadas con IA y análisis de datos.';
          this.descripcionOriginal = this.descripcion;
        }
      } catch (error) {
        console.error('Error al obtener la descripción del profesor:', error);
        // En caso de error, usar descripción por defecto
        this.descripcion = 'Doctor en Ciencias de la Computación especializado en Inteligencia Artificial. Imparto cursos de Algoritmos y Minería de Datos. 15 años de experiencia docente. Disponible para asesorías en proyectos de investigación y tesis relacionadas con IA y análisis de datos.';
        this.descripcionOriginal = this.descripcion;
      }
    },
    async guardarCambios() {
      try {
        // Obtener ID del profesor desde el store
        const profesorId = this.currentProfesor?.idProfesor;

        if (!profesorId) {
          // Si no está en el store, intentar obtenerlo desde localStorage como fallback
          const user = JSON.parse(localStorage.getItem('user') || '{}');
          this.idProfesor = user.idProfesor || null;
        } else {
          this.idProfesor = profesorId;
        }

        if (!this.idProfesor) {
          this.toast.error('Error: No se pudo obtener el ID del profesor');
          console.log('Store profesor:', this.currentProfesor);
          console.log('User localStorage:', JSON.parse(localStorage.getItem('user') || '{}'));
          return;
        }

        await api.actualizarDescripcionProfesor(this.idProfesor, this.descripcion);
        this.descripcionOriginal = this.descripcion; // Actualizar la versión original
        this.toast.success('Descripción actualizada correctamente');

        // Navegar de vuelta a la ventana de perfil después de guardar
        setTimeout(() => {
          this.$router.push('/perfil');
        }, 1500); // Esperar 1.5 segundos para que el usuario vea el mensaje de éxito
      } catch (error) {
        console.error('Error al actualizar la descripción:', error);
        this.toast.error('Error al actualizar la descripción');
      }
    },
    cancelarCambios() {
      this.descripcion = this.descripcionOriginal; // Restaurar descripción original
      this.toast.info('Cambios cancelados');
      // Navegar de vuelta a la ventana de perfil
      this.$router.push('/perfil');
    },
    obtenerIdProfesor() {
      // Primero intentar desde el store
      if (this.currentProfesor?.idProfesor) {
        this.idProfesor = this.currentProfesor.idProfesor;
        console.log('ID obtenido desde store:', this.idProfesor);
        return;
      }

      // Si no está en el store, intentar desde localStorage
      const user = JSON.parse(localStorage.getItem('user') || '{}');
      this.idProfesor = user.idProfesor || null;
      console.log('ID obtenido desde localStorage:', this.idProfesor);

      if (!this.idProfesor) {
        console.warn('No se pudo obtener el ID del profesor ni desde store ni desde localStorage');
        console.log('Store profesor:', this.currentProfesor);
        console.log('User localStorage:', user);
      }
    }
  },
  async mounted() {
    // Obtener la descripción real del profesor desde la API
    await this.obtenerDescripcionProfesor();
    // Obtener el ID del profesor
    this.obtenerIdProfesor();
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
  height: 80%;
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
  padding: 50px;
  gap: 30px;
  /* Espacio entre columnas */
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

.label {
  font-size: 120%;
  font-weight: bold;
  white-space: nowrap;
}

.input {
  width: 100%;
  height: 150px;
  border-radius: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  background-color: #fff;
  font-size: 14px;
  resize: none;
}

.contador {
  font-size: 12px;
  color: #7E828F;
  text-align: right;
  margin-top: 5px;
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
</style>