<template>
  <v-container class="pa-0 fill-height full-size registro-container">
    <v-card class="mx-auto pa-6 full-size" rounded="lg" elevation="0">
      <v-form ref="form" v-model="valid" class="form-fixed-height">
        <!-- Información del profesor y periodo -->
        <div class="d-flex mb-4 info-header">
          <div v-for="(item, index) in infoHeader" :key="index" class="flex-grow-1" :class="{ 'mr-4': index === 0 }">
            <div class="text-subtitle-1 text-grey-darken-1">{{ item.label }}:</div>
            <div class="text-body-1 font-weight-medium info-value">{{ item.value }}</div>
          </div>
        </div>

        <!-- Campos de formulario agrupados -->
        <div v-for="(row, rowIndex) in formRows" :key="rowIndex" class="d-flex mb-4">
          <div v-for="(field, fieldIndex) in row.fields" :key="fieldIndex" class="field-container"
            :class="[fieldIndex < row.fields.length - 1 ? 'mr-4' : '', field.customClass]">
            <div class="field-label">{{ field.label }}:</div>

            <!-- Campo de texto estándar -->
            <v-text-field v-if="field.type === 'text' || field.type === 'date'" v-model="formData[field.model]"
              :type="field.type" variant="outlined" :rules="field.rules || requiredRules" class="bordered-field"
              density="comfortable">
            </v-text-field>

            <!-- Campo de tiempo doble -->
            <div v-if="field.type === 'time'" class="d-flex time-inputs-container">
              <div class="time-input-wrapper">
                <v-text-field label="Hora de inicio" v-model="formData.horaInicio" type="time" variant="outlined"
                  :rules="requiredRules" class="bordered-field" density="comfortable"></v-text-field>
              </div>
              <div class="mx-1 d-flex align-center arrow-separator">→</div>
              <div class="time-input-wrapper">
                <v-text-field label="Hora final" v-model="formData.horaFinal" type="time" variant="outlined"
                  :rules="requiredRules" class="bordered-field" density="comfortable"></v-text-field>
              </div>
            </div>

            <!-- Menú desplegable -->
            <v-menu v-if="field.type === 'select'" offset-y>
              <template v-slot:activator="{ props }">
                <v-text-field v-model="formData[field.model]" variant="outlined" readonly :rules="requiredRules"
                  v-bind="props" class="bordered-field" density="comfortable" append-icon="mdi-menu-down"
                  bg-color="#B7C3E8"></v-text-field>
              </template>
              <v-list>
                <v-list-item v-for="(option, i) in field.options" :key="i" :value="option"
                  @click="formData[field.model] = option">
                  <v-list-item-title>{{ option }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>

            <!-- Radio buttons -->
            <v-radio-group v-if="field.type === 'radio'" v-model="formData[field.model]"
              :row="$vuetify.display ? $vuetify.display.mdAndUp : true" class="radio-group">
              <v-radio v-for="(option, i) in field.options" :key="i" :label="option" :value="option"></v-radio>
            </v-radio-group>

            <!-- Textarea -->
            <v-textarea v-if="field.type === 'textarea'" v-model="formData[field.model]" variant="outlined" rows="2"
              class="bordered-field" :placeholder="field.placeholder" density="comfortable"></v-textarea>
          </div>
        </div>

        <!-- Botones de acción -->
        <div class="d-flex justify-end mt-6 action-buttons">
          <v-btn v-for="(btn, btnIndex) in actionButtons" :key="btnIndex" color="#B7C3E8" class="action-btn"
            :class="{ 'mr-4': btnIndex === 0 }" @click="btn.action" :disabled="btn.disabled">
            <v-icon class="mr-1">{{ btn.icon }}</v-icon>
            {{ btn.label }}
          </v-btn>
        </div>
      </v-form>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref, reactive, computed, onMounted, watch } from 'vue';
import { useStore } from 'vuex';
import api from '@/services/api';

// Store
const store = useStore();

// Computed para obtener datos del profesor
const currentProfesor = computed(() => store.getters.currentProfesor);

// Función para cargar datos del profesor
const cargarDatosProfesor = async () => {
  try {
    await store.dispatch('dataProfesor');
    console.log('Datos del profesor cargados:', currentProfesor.value);
  } catch (error) {
    console.error('Error al cargar datos del profesor:', error);
  }
};

// Cargar datos al montar el componente
onMounted(async () => {
  await cargarDatosProfesor();
});

// Función para calcular el periodo académico basado en la fecha actual
const calcularPeriodoActual = () => {
  const ahora = new Date();
  const mes = ahora.getMonth() + 1; // getMonth() devuelve 0-11, por eso sumamos 1
  const año = ahora.getFullYear();

  // Los dos últimos dígitos del año actual y siguiente
  const añoActual = año.toString().slice(-2);
  const añoSiguiente = (año + 1).toString().slice(-2);

  // Determinar el periodo basado en el mes
  if (mes >= 2 && mes <= 7) {
    // Febrero a Julio (semestre par)
    return `Febrero-Julio ${añoActual}-${añoSiguiente}A`;
  } else if (mes >= 8 && mes <= 12) {
    // Agosto a Diciembre (semestre impar)
    return `Agosto-Diciembre ${añoActual}-${añoSiguiente}B`;
  } else {
    // Enero (continúa el semestre impar del año anterior)
    const añoAnterior = (año - 1).toString().slice(-2);
    return `Agosto-Diciembre ${añoAnterior}-${añoActual}B`;
  }
};

// Función para obtener la fecha actual en formato YYYY-MM-DD
const obtenerFechaActual = () => {
  const hoy = new Date();
  return hoy.toISOString().split('T')[0];
};

// Función para obtener la hora actual en formato HH:MM
const obtenerHoraActual = () => {
  const ahora = new Date();
  return ahora.toTimeString().slice(0, 5);
};

// Función para obtener una hora después de la hora actual
const obtenerHoraMasUna = () => {
  const ahora = new Date();
  ahora.setHours(ahora.getHours() + 1);
  return ahora.toTimeString().slice(0, 5);
};

// Formulario reactivo
const valid = ref(false);
const formData = reactive({
  nombreProfesor: '',
  periodo: calcularPeriodoActual(), // Calcular automáticamente el periodo
  matricula: '',
  nombreAlumno: '',
  apellidosAlumno: '',
  licenciatura: '',
  grupo: '',
  fecha: obtenerFechaActual(), // Establecer fecha actual
  horaInicio: obtenerHoraActual(), // Establecer hora actual
  horaFinal: obtenerHoraMasUna(), // Establecer una hora después
  materiaSeleccionada: 'Materia1',
  lugarAsesoria: 'Biblioteca',
  observaciones: ''
});

// Watcher para actualizar el nombre del profesor cuando cambien los datos
watch(currentProfesor, (newProfesor) => {
  console.log('Datos del profesor recibidos:', newProfesor); // Para debugging
  if (newProfesor && newProfesor.nomProf) {
    // Construir el nombre completo: nombre + apellido paterno + apellido materno (si existe)
    const nombreCompleto = [
      newProfesor.nomProf,
      newProfesor.apellidoProf,
      newProfesor.apellidoMProf || '' // El apellido materno puede ser nulo
    ].filter(part => part && part.trim() !== '').join(' ');

    formData.nombreProfesor = nombreCompleto;
  }
}, { immediate: true });

// Función para obtener datos del estudiante por matrícula
const obtenerDatosEstudiante = async (matricula) => {
  try {
    console.log('Consultando estudiante con matrícula:', matricula);
    const response = await api.getEstudiantePorMatricula(matricula);

    if (response.data) {
      const estudiante = response.data;
      console.log('Datos del estudiante recibidos:', estudiante);

      // Actualizar los campos del formulario con los datos del estudiante
      // Mapear los campos según el modelo del backend
      formData.nombreAlumno = estudiante.nomEst || '';
      formData.apellidosAlumno = [
        estudiante.apellidoPEst || '',
        estudiante.apellidoMEst || ''
      ].filter(part => part.trim() !== '').join(' ');

      const responseCarrera = await api.getCarreraPorId(estudiante.idCarrera);
      formData.licenciatura = responseCarrera.data?.nombreCarrera || '';
      let grupoEstudiante = '';
      if (estudiante.idCarrera < 10) {
        grupoEstudiante = `${estudiante.idSemestre}0${estudiante.idCarrera}` || '';
      } else {
        grupoEstudiante = `${estudiante.idSemestre}${estudiante.idCarrera}`;
      }

      formData.grupo = grupoEstudiante || '';
    }

  } catch (error) {
    console.error('Error al obtener datos del estudiante:', error);
    // Limpiar los campos si hay error
    formData.nombreAlumno = '';
    formData.apellidosAlumno = '';
    formData.licenciatura = '';
    formData.grupo = '';
  }
};

// Watcher para la matrícula - ejecuta la consulta cuando tenga exactamente 10 dígitos
watch(() => formData.matricula, (newMatricula) => {
  // Verificar que la matrícula tenga exactamente 10 dígitos numéricos
  if (newMatricula && /^\d{10}$/.test(newMatricula)) {
    obtenerDatosEstudiante(newMatricula);
  } else if (newMatricula && newMatricula.length < 10) {
    // Si tiene menos de 10 dígitos, limpiar los campos del estudiante
    formData.nombreAlumno = '';
    formData.apellidosAlumno = '';
    formData.licenciatura = '';
    formData.grupo = '';
  }
});

// Watcher para actualizar automáticamente la hora final cuando cambie la hora de inicio
watch(() => formData.horaInicio, (newHoraInicio) => {
  if (newHoraInicio) {
    const [horas, minutos] = newHoraInicio.split(':').map(Number);
    const fecha = new Date();
    fecha.setHours(horas + 1, minutos, 0, 0);
    formData.horaFinal = fecha.toTimeString().slice(0, 5);
  }
});

// Reglas de validación
const requiredRules = [v => !!v || 'Este campo es requerido'];
const matriculaRules = [
  ...requiredRules,
  v => /^\d{10}$/.test(v) || 'La matrícula debe tener exactamente 10 dígitos'
];

// Información de cabecera - ahora como computed para que sea reactiva
const infoHeader = computed(() => [
  { label: 'Nombre del Profesor', value: formData.nombreProfesor || 'Cargando...' },
  { label: 'Periodo', value: formData.periodo }
]);

// Lista de materias
const materias = ['Materia1', 'Materia2', 'Materia3', 'Materia4', 'Materia5'];

// Configuración de filas del formulario
const formRows = [
  {
    fields: [
      { label: 'Matrícula', model: 'matricula', type: 'text', rules: matriculaRules },
      { label: 'Nombre del alumno', model: 'nombreAlumno', type: 'text' },
      { label: 'Apellidos del alumno', model: 'apellidosAlumno', type: 'text' }
    ]
  },
  {
    fields: [
      { label: 'Licenciatura', model: 'licenciatura', type: 'text' },
      { label: 'Grupo', model: 'grupo', type: 'text' }
    ]
  },
  {
    fields: [
      { label: 'Fecha', model: 'fecha', type: 'date' },
      {
        label: 'Duración de la asesoría',
        type: 'time',
        customClass: 'time-field-container'
      },
      {
        label: 'Materia',
        model: 'materiaSeleccionada',
        type: 'select',
        options: materias
      }
    ]
  },
  {
    fields: [
      {
        label: 'En sustitución de',
        model: 'lugarAsesoria',
        type: 'radio',
        options: ['Biblioteca', 'Sala de computo'],
        customClass: 'radio-container'
      },
      {
        label: 'Observaciones',
        model: 'observaciones',
        type: 'textarea',
        placeholder: 'Ejemplo de observación',
        customClass: 'textarea-container'
      }
    ]
  }
];

// Configuración de botones
const actionButtons = [
  {
    label: 'Agregar Alumno',
    icon: 'mdi-account-plus',
    action: agregarAlumno,
    disabled: false
  },
  {
    label: 'Registrar',
    icon: 'mdi-content-save',
    action: registrar,
    disabled: !valid.value
  }
];

// Métodos
function agregarAlumno() {
  console.log('Agregar alumno');
  alert('Funcionalidad para agregar alumno');
}

function registrar() {
  console.log('Registrar asesoría');
  console.log(formData);
  alert('Asesoría registrada correctamente');
}
</script>

<style scoped>
.full-size {
  width: 100%;
  height: 100%;
}

.registro-container {
  max-width: 100% !important;
}

.v-card {
  /* Se elimina el borde de la tarjeta */
  border-radius: 8px;
  max-width: 1200px;
  margin: 0 auto;
}

.bordered-field {
  /* Se eliminan los bordes punteados */
  border-radius: 4px;
  padding: 4px 0;
  background-color: white;
  width: 100% !important;
  /* Asegurar ancho completo y consistente */
}

.field-label {
  margin-bottom: 4px;
  color: #616161;
  font-size: 0.875rem;
}

.field-container {
  margin-bottom: 8px;
  flex-grow: 1;
  position: relative;
  width: 100%;
  /* Asegura que el contenedor ocupe el espacio disponible */
}

.radio-group {
  /* Se eliminan los bordes punteados */
  padding: 8px 0;
  background-color: white;
}

.action-btn {
  /* Se eliminan los bordes punteados */
  background-color: #B7C3E8;
  color: #333;
  font-weight: 500;
}

/* Para mantener consistencia en los mensajes de error */
:deep(.v-messages) {
  min-height: 20px;
  height: 20px;
  overflow: hidden;
  position: absolute;
  /* Posiciona los mensajes de error de forma absoluta */
}

:deep(.v-field__field) {
  height: 44px !important;
}

:deep(.v-input) {
  width: 100% !important;
  /* Forzar que todos los inputs ocupen el 100% del contenedor padre */
}

/* Mantener altura fija para los campos */
.field-container {
  height: 90px;
  /* Altura fija para todos los contenedores de campo */
}

/* Especial para el contenedor de tiempo */
.time-field-container {
  height: 110px;
  /* Más alto para acomodar dos filas de campos */
}

/* Especial para el contenedor de radio buttons */
.radio-container {
  height: 80px !important;
}

/* Contenedor de observaciones */
.textarea-container {
  height: 120px;
  /* Más alto para el área de texto */
}

/* Ajuste para el contenedor de hora */
.time-inputs-container {
  display: flex;
  width: 100%;
  height: auto;
}

.time-input-wrapper {
  width: 47%;
  position: relative;
}

/* Estilos responsivos */
@media (max-width: 768px) {
  .d-flex {
    flex-direction: column;
  }

  .field-container {
    width: 100%;
    margin-right: 0 !important;
    margin-bottom: 15px;
    height: auto !important;
    min-height: 80px;
  }

  .mr-4 {
    margin-right: 0 !important;
  }

  .action-btn {
    width: 100%;
    margin-bottom: 10px;
    margin-right: 0 !important;
    height: 48px;
    font-size: 16px;
  }

  .v-card {
    padding: 16px !important;
    margin: 0 !important;
    border-radius: 0;
  }

  .time-inputs-container {
    flex-direction: column;
  }

  .time-input-wrapper {
    width: 100%;
    margin-bottom: 8px;
  }

  .arrow-separator {
    transform: rotate(90deg);
    margin: 8px 0;
  }

  .time-field-container {
    height: auto !important;
    min-height: 160px;
  }

  .radio-container {
    height: auto !important;
    min-height: 90px;
  }

  .textarea-container {
    height: auto !important;
    min-height: 140px;
  }

  .form-fixed-height {
    min-height: auto !important;
    padding-bottom: 80px;
  }

  .d-flex.justify-end.mt-6 {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    background: white;
    padding: 12px 16px;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
    z-index: 10;
    margin-top: 0 !important;
    justify-content: space-between;
  }

  .info-header {
    flex-direction: column;
  }

  .info-header>div {
    margin-right: 0 !important;
    margin-bottom: 12px;
  }

  :deep(.v-field__input) {
    font-size: 16px !important;
  }

  :deep(.v-label) {
    font-size: 14px;
  }

  .field-label {
    font-size: 0.95rem;
    margin-bottom: 6px;
  }

  .v-btn {
    font-size: 15px !important;
    height: 44px;
  }
}

/* Estilos para información estática */
.info-value {
  padding: 6px 0;
  font-size: 1rem;
}

/* Ajustes específicos para pantallas muy pequeñas */
@media (max-width: 360px) {
  .v-card {
    padding: 12px !important;
  }

  .field-label {
    font-size: 0.85rem;
  }
}

/* Ajustes para orientación horizontal */
@media screen and (orientation: landscape) and (max-width: 768px) {
  .form-fixed-height {
    padding-bottom: 70px;
  }
}
</style>