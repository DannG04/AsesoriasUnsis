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
            :class="[fieldIndex < row.fields.length - 1 ? 'mr-4' : '', field.customClass]">            <div class="field-label">{{ field.label }}:</div>            <!-- Campo de texto estándar -->
            <v-text-field v-if="field.type === 'text' || field.type === 'date'" v-model="formData[field.model]"
              :type="field.type" :variant="field.readonly ? 'outlined' : 'outlined'" :rules="field.readonly ? [] : (field.rules || requiredRules)" class="bordered-field"
              density="comfortable" :readonly="field.readonly" :maxlength="field.model === 'matricula' ? 10 : undefined"
              @input="field.model === 'matricula' ? filtrarSoloNumeros($event) : null"
              :bg-color="field.readonly ? '#f5f5f5' : 'white'"
              :persistent-placeholder="field.readonly"
              :placeholder="field.readonly ? field.label : ''"
              :style="field.readonly ? 'pointer-events: none;' : ''">
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
            <v-select v-if="field.type === 'select'" v-model="formData[field.model]" :items="field.options"
              variant="outlined" :rules="requiredRules" class="bordered-field" density="comfortable" bg-color="#B7C3E8">
            </v-select>            <!-- Radio buttons -->
            <v-radio-group v-if="field.type === 'radio'" v-model="formData[field.model]"
              :row="false" class="radio-group">
              <v-radio v-for="(option, i) in field.options" :key="i" :label="option" :value="option" class="mb-2"></v-radio>
            </v-radio-group>

            <!-- Textarea -->
            <v-textarea v-if="field.type === 'textarea'" v-model="formData[field.model]" variant="outlined" rows="2"
              class="bordered-field" :placeholder="field.placeholder" density="comfortable"></v-textarea>
          </div>
        </div>        <!-- Botones de acción -->
        <div class="d-flex justify-end mt-6 action-buttons">
          <div v-for="(btn, btnIndex) in actionButtons" :key="btnIndex" 
               :class="{ 'mr-4': btnIndex === 0 }"
               class="button-container">
            
            <v-btn color="#B7C3E8" 
                   class="action-btn"
                   @click="btn.action" 
                   :disabled="btn.disabled">
              <v-icon class="mr-1">{{ btn.icon }}</v-icon>
              {{ btn.label }}
            </v-btn>
            
            <!-- Mensaje informativo para Agregar Alumno cuando está deshabilitado -->
            <div v-if="btn.label === 'Agregar Alumno' && btn.disabled" 
                 class="help-text mt-1">
              <v-icon size="small" color="grey">mdi-information-outline</v-icon>
              <span class="text-caption text-grey-darken-1 ml-1">
                Solo para asesorías múltiples
              </span>
            </div>
          </div>
        </div>
      </v-form>
    </v-card>    <!-- Modal para agregar alumno -->
    <v-dialog v-model="dialogAgregarAlumno" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h5 modal-header" style="background-color: #B7C3E8; color: #333;">
          <v-icon class="mr-2">mdi-account-plus</v-icon>
          Registrar Alumno Adicional
        </v-card-title>
          <v-card-text class="pb-0">
          <div class="text-body-2 mb-4 text-grey-darken-1">
            <strong>Asesoría múltiple:</strong> Se registrará una nueva asesoría con los mismos datos 
            (fecha, hora, materia y observaciones) para el alumno adicional que ingreses a continuación.
          </div>
          
          <v-form ref="formModal" v-model="validModal">
            <v-text-field
              v-model="matriculaModal"
              label="Matrícula del Alumno"
              variant="outlined"
              :rules="matriculaModalRules"
              maxlength="10"
              @input="filtrarSoloNumerosModal"
              class="bordered-field"
              density="comfortable"
              hint="Ingresa exactamente 10 dígitos"
              persistent-hint
            ></v-text-field>

            <!-- Mostrar datos del estudiante encontrado -->
            <div v-if="estudianteEncontrado" class="mt-4">
              <v-divider class="mb-3"></v-divider>
              <div class="text-subtitle-2 mb-2 text-success">
                <v-icon class="mr-1" color="success">mdi-check-circle</v-icon>
                Estudiante encontrado:
              </div>
                <v-text-field
                :value="datosEstudianteModal.nombreCompleto"
                label="Nombre completo"
                variant="outlined"
                readonly
                density="comfortable"
                class="mb-2"
                bg-color="#f5f5f5"
                persistent-placeholder
                style="pointer-events: none;"
              ></v-text-field>
              
              <v-text-field
                :value="datosEstudianteModal.licenciatura"
                label="Licenciatura"
                variant="outlined"
                readonly
                density="comfortable"
                class="mb-2"
                bg-color="#f5f5f5"
                persistent-placeholder
                style="pointer-events: none;"
              ></v-text-field>
              
              <v-text-field
                :value="datosEstudianteModal.grupo"
                label="Grupo"
                variant="outlined"
                readonly
                density="comfortable"
                bg-color="#f5f5f5"
                persistent-placeholder
                style="pointer-events: none;"
              ></v-text-field>
            </div>            <!-- Mensaje de error si no se encuentra el estudiante -->
            <div v-if="matriculaModal && matriculaModal.length === 10 && !estudianteEncontrado" class="mt-4">
              <v-alert v-if="matriculaModal === formData.matricula" 
                       type="error" 
                       variant="outlined" 
                       class="text-body-2">
                <v-icon class="mr-1">mdi-alert-circle</v-icon>
                Esta matrícula ya está registrada en el formulario principal
              </v-alert>
              <v-alert v-else
                       type="warning" 
                       variant="outlined" 
                       class="text-body-2">
                <v-icon class="mr-1">mdi-alert</v-icon>
                No se encontró un estudiante con esta matrícula
              </v-alert>
            </div>
          </v-form>
        </v-card-text>

        <v-card-actions class="px-6 pb-4">
          <v-spacer></v-spacer>
          <v-btn 
            color="grey-darken-1" 
            variant="text" 
            @click="cerrarModalAgregarAlumno"
          >
            Cancelar
          </v-btn>
          <v-btn 
            color="#B7C3E8" 
            variant="elevated"
            @click="confirmarAgregarAlumno"
            :disabled="!estudianteEncontrado || !validModal"
            style="color: #333;"
          >
            <v-icon class="mr-1">mdi-content-save</v-icon>
            Registrar Asesoría
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script setup>
import { ref, reactive, computed, onMounted, watch } from 'vue';
import { useStore } from 'vuex';
import api from '@/services/api';
import { useToast } from "vue-toastification";

// Inicializar toast
const toast = useToast();

// Store
const store = useStore();

// Computed para obtener datos del profesor
const currentProfesor = computed(() => store.getters.currentProfesor);

// Estado para las materias
const materias = ref([]);

// Función para cargar materias del profesor
const cargarMateriasProfesor = async (profesorId) => {
  try {
    const response = await api.getMateriasPorProfesorId(profesorId);
    materias.value = response.data.map(materia => ({
      value: materia[1],  // ID de la materia
      text: materia[0]    // Nombre de la materia
    }));

    // Seleccionar automáticamente la primera materia
    if (materias.value.length > 0) {
      formData.materiaSeleccionada = materias.value[0].text;
    }
  } catch (error) {
    console.error('Error al obtener materias:', error);
    materias.value = [];
  }
};

// Función para cargar datos del profesor
const cargarDatosProfesor = async () => {
  try {
    await store.dispatch('dataProfesor');

    // Cargar materias una vez que se tengan los datos del profesor
    if (currentProfesor.value?.idProfesor) {
      await cargarMateriasProfesor(currentProfesor.value.idProfesor);
    }
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
  materiaSeleccionada: '',
  lugarAsesoria: 'Biblioteca',
  observaciones: ''
});

// Variables para el modal de agregar alumno
const dialogAgregarAlumno = ref(false);
const validModal = ref(false);
const matriculaModal = ref('');
const estudianteEncontrado = ref(false);
const datosEstudianteModal = reactive({
  nombreCompleto: '',
  licenciatura: '',
  grupo: ''
});

// Watcher para actualizar el nombre del profesor cuando cambien los datos
watch(currentProfesor, async (newProfesor) => {
  if (newProfesor?.nomProf) {
    const nombreCompleto = [
      newProfesor.nomProf,
      newProfesor.apellidoProf,
      newProfesor.apellidoMProf
    ].filter(Boolean).join(' ');

    formData.nombreProfesor = nombreCompleto;

    if (newProfesor.idProfesor) {
      await cargarMateriasProfesor(newProfesor.idProfesor);
    }
  }
}, { immediate: true });

// Función para obtener datos del estudiante por matrícula
const obtenerDatosEstudiante = async (matricula) => {
  try {
    const response = await api.getEstudiantePorMatricula(matricula);

    if (response.data) {
      const estudiante = response.data;

      // Actualizar los campos del formulario con los datos del estudiante
      formData.nombreAlumno = estudiante.nomEst || '';
      formData.apellidosAlumno = [
        estudiante.apellidoPEst || '',
        estudiante.apellidoMEst || ''
      ].filter(part => part.trim() !== '').join(' ');

      const responseCarrera = await api.getCarreraPorId(estudiante.idCarrera);
      formData.licenciatura = responseCarrera.data?.nombreCarrera || '';

      const grupoEstudiante = estudiante.idCarrera < 10
        ? `${estudiante.idSemestre}0${estudiante.idCarrera}`
        : `${estudiante.idSemestre}${estudiante.idCarrera}`;

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

// Watcher para la matrícula del modal
watch(() => matriculaModal.value, (newMatricula) => {
  if (newMatricula && /^\d{10}$/.test(newMatricula)) {
    obtenerDatosEstudianteModal(newMatricula);
  } else {
    // Limpiar datos si la matrícula no es válida
    estudianteEncontrado.value = false;
    datosEstudianteModal.nombreCompleto = '';
    datosEstudianteModal.licenciatura = '';
    datosEstudianteModal.grupo = '';
  }
});

// Reglas de validación
const requiredRules = [v => !!v || 'Este campo es requerido'];
const matriculaRules = [
  ...requiredRules,
  v => /^\d{10}$/.test(v) || 'La matrícula debe tener exactamente 10 dígitos'
];

// Reglas de validación para el modal
const matriculaModalRules = [
  v => !!v || 'Este campo es requerido',
  v => /^\d{10}$/.test(v) || 'La matrícula debe tener exactamente 10 dígitos',
  v => v !== formData.matricula || 'No puedes agregar la misma matrícula del formulario principal'
];

// Información de cabecera - ahora como computed para que sea reactiva
const infoHeader = computed(() => [
  { label: 'Nombre del Profesor', value: formData.nombreProfesor || 'Cargando...' },
  { label: 'Periodo', value: formData.periodo }
]);

// Configuración de filas del formulario - actualizada para usar materias reactivas
const formRows = computed(() => {
  const lugarOptions = ['Biblioteca', 'Sala de Cómputo', 'Otro'];
  console.log('Opciones de lugar:', lugarOptions); // Debug temporal
  
  return [
    {
      fields: [
        { label: 'Matrícula', model: 'matricula', type: 'text', rules: matriculaRules },
        { label: 'Nombre del alumno', model: 'nombreAlumno', type: 'text', readonly: true },
        { label: 'Apellidos del alumno', model: 'apellidosAlumno', type: 'text', readonly: true }
      ]
    },
    {
      fields: [
        { label: 'Licenciatura', model: 'licenciatura', type: 'text', readonly: true },
        { label: 'Grupo', model: 'grupo', type: 'text', readonly: true }
      ]
    }, {
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
          options: materias.value?.map(materia => materia.text) || []
        }
      ]
    },
    {
      fields: [
        {
          label: 'En sustitución de',
          model: 'lugarAsesoria',
          type: 'radio',
          options: lugarOptions,
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
});

// Configuración de botones
const actionButtons = computed(() => [
  {
    label: 'Agregar Alumno',
    icon: 'mdi-account-plus',
    action: agregarAlumno,
    disabled: !formData.fecha || !formData.horaInicio || !formData.horaFinal || 
              !formData.materiaSeleccionada || !formData.nombreAlumno || 
              !formData.apellidosAlumno || !formData.licenciatura || !formData.grupo
  },
  {
    label: 'Registrar',
    icon: 'mdi-content-save',
    action: registrar,
    disabled: !formData.matricula || formData.matricula.length !== 10
  }
]);

// Métodos
function filtrarSoloNumeros(event) {
  // Obtener solo los números del valor ingresado
  const valor = event.target.value.replace(/\D/g, '');
  // Limitar a máximo 10 dígitos
  const valorLimitado = valor.slice(0, 10);
  // Actualizar el valor en el modelo
  formData.matricula = valorLimitado;
}

// Función específica para el modal
function filtrarSoloNumerosModal(event) {
  const valor = event.target.value.replace(/\D/g, '');
  const valorLimitado = valor.slice(0, 10);
  matriculaModal.value = valorLimitado;
}

// Función para obtener datos del estudiante en el modal
async function obtenerDatosEstudianteModal(matricula) {
  try {
    // Verificar que no sea la misma matrícula del formulario principal
    if (matricula === formData.matricula) {
      estudianteEncontrado.value = false;
      datosEstudianteModal.nombreCompleto = '';
      datosEstudianteModal.licenciatura = '';
      datosEstudianteModal.grupo = '';
      
      toast.warning('No puedes agregar la misma matrícula que ya está registrada en el formulario principal', {
        timeout: 5000
      });
      return;
    }

    const response = await api.getEstudiantePorMatricula(matricula);

    if (response.data) {
      const estudiante = response.data;
      
      // Actualizar los datos del estudiante en el modal
      const nombreCompleto = [
        estudiante.nomEst || '',
        estudiante.apellidoPEst || '',
        estudiante.apellidoMEst || ''
      ].filter(part => part.trim() !== '').join(' ');

      datosEstudianteModal.nombreCompleto = nombreCompleto;

      const responseCarrera = await api.getCarreraPorId(estudiante.idCarrera);
      datosEstudianteModal.licenciatura = responseCarrera.data?.nombreCarrera || '';

      const grupoEstudiante = estudiante.idCarrera < 10
        ? `${estudiante.idSemestre}0${estudiante.idCarrera}`
        : `${estudiante.idSemestre}${estudiante.idCarrera}`;

      datosEstudianteModal.grupo = grupoEstudiante || '';
      estudianteEncontrado.value = true;
    } else {
      estudianteEncontrado.value = false;
    }

  } catch (error) {
    console.error('Error al obtener datos del estudiante:', error);
    estudianteEncontrado.value = false;
    datosEstudianteModal.nombreCompleto = '';
    datosEstudianteModal.licenciatura = '';
    datosEstudianteModal.grupo = '';
  }
}

function agregarAlumno() {
  // Verificar que ya haya un estudiante registrado en el formulario principal
  if (!formData.nombreAlumno || !formData.apellidosAlumno || !formData.licenciatura || !formData.grupo) {
    toast.warning('Primero debes ingresar la matrícula de un estudiante para poder agregar más alumnos', {
      timeout: 5000
    });
    return;
  }
  
  // Verificar que los datos principales estén completos
  if (!formData.fecha || !formData.horaInicio || !formData.horaFinal || !formData.materiaSeleccionada) {
    toast.warning('Completa todos los campos principales antes de agregar más alumnos', {
      timeout: 5000
    });
    return;
  }
  
  dialogAgregarAlumno.value = true;
}

function cerrarModalAgregarAlumno() {
  dialogAgregarAlumno.value = false;
  matriculaModal.value = '';
  estudianteEncontrado.value = false;
  datosEstudianteModal.nombreCompleto = '';
  datosEstudianteModal.licenciatura = '';
  datosEstudianteModal.grupo = '';
}

function confirmarAgregarAlumno() {
  // Validación final para evitar matrículas duplicadas
  if (matriculaModal.value === formData.matricula) {
    toast.error('No puedes agregar la misma matrícula que ya está en el formulario principal', {
      timeout: 5000
    });
    return;
  }
  
  if (estudianteEncontrado.value && matriculaModal.value) {
    // Registrar nueva asesoría con los mismos datos pero diferente alumno
    registrarNuevaAsesoria(matriculaModal.value);
  }
}

async function registrarNuevaAsesoria(matriculaAlumno) {
  try {
    // Verificar que los datos principales estén completos
    if (!formData.fecha || !formData.horaInicio || !formData.horaFinal || 
        !formData.materiaSeleccionada || !currentProfesor.value?.idProfesor) {
      toast.error('Por favor, completa todos los campos principales antes de agregar más alumnos', {
        timeout: 5000
      });
      return;
    }

    // Usar las observaciones del formulario principal, o "Sin observaciones" si está vacío
    const observaciones = formData.observaciones || 'Sin observaciones';

    // Crear los datos de la nueva asesoría con la matrícula del modal
    const datosNuevaAsesoria = {
      matricula: matriculaAlumno,
      idProfesor: currentProfesor.value.idProfesor,
      fecha: formData.fecha,
      horaInicio: formData.horaInicio,
      horaFinal: formData.horaFinal,
      materiaSeleccionada: formData.materiaSeleccionada,
      lugarAsesoria: formData.lugarAsesoria,
      observaciones: observaciones
    };

    console.log('Registrando nueva asesoría para alumno adicional:', datosNuevaAsesoria);

    // Enviar datos al backend
    const response = await api.enviarAsesoria(
      matriculaAlumno,
      formData.horaInicio,
      formData.horaFinal,
      formData.materiaSeleccionada,
      formData.lugarAsesoria,
      observaciones,
      currentProfesor.value.idProfesor,
      formData.fecha
    );    console.log('Respuesta del servidor para alumno adicional:', response.data);
    
    // Mostrar mensaje de éxito específico
    toast.success('Asesoría registrada', {
      timeout: 4000
    });
    
    // Cerrar modal
    cerrarModalAgregarAlumno();

  } catch (error) {
    console.error('Error al registrar la asesoría del alumno adicional:', error);
    toast.error('Error al registrar la asesoría para el alumno adicional. Por favor, inténtalo de nuevo.', {
      timeout: 5000
    });
  }
}

async function registrar() {
  try {
    if (formData.observaciones == null || formData.observaciones == '') {
      formData.observaciones = 'Sin observaciones';
    }

    //Obtiene los datos del formulario
    const datosRegistro = {
      matricula: formData.matricula,
      idProfesor: currentProfesor.value.idProfesor,
      fecha: formData.fecha,
      horaInicio: formData.horaInicio,
      horaFinal: formData.horaFinal,
      materiaSeleccionada: formData.materiaSeleccionada,
      lugarAsesoria: formData.lugarAsesoria,
      observaciones: formData.observaciones
    };

    console.log('Enviando datos de registro:', datosRegistro);

    // Enviar datos al backend usando el método de la API
    const response = await api.enviarAsesoria(formData.matricula, formData.horaInicio, formData.horaFinal, formData.materiaSeleccionada, formData.lugarAsesoria, formData.observaciones, currentProfesor.value.idProfesor, formData.fecha);
    console.log('Respuesta del servidor:', response.data);
    // Mostrar mensaje de éxito
    toast.success('Asesoría registrada', {
      timeout: 5000
    });
    //Limpiar el formulario después del registro exitoso
    formData.matricula = '';
    formData.nombreAlumno = '';
    formData.apellidosAlumno = '';
    formData.licenciatura = '';
    formData.grupo = '';
    formData.observaciones = '';
  } catch (error) {
    toast.error('Error al registrar la asesoría. Por favor, inténtalo de nuevo.', {
      timeout: 5000
    });
  }
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
  border-radius: 8px;
  max-width: 1200px;
  margin: 0 auto;
}

.bordered-field {
  border-radius: 4px;
  padding: 4px 0;
  background-color: white;
  width: 100% !important;
  overflow: hidden;
}

/* Arreglar el borde azul del focus */
:deep(.v-field--focused) {
  overflow: hidden;
}

:deep(.v-field--focused .v-field__outline) {
  border-radius: 4px;
}

.field-container {
  margin-bottom: 8px;
  flex-grow: 1;
  position: relative;
  width: 100%;
  height: 90px;
}

/* Contenedor específico para radio buttons - sin overflow hidden */
.field-container.radio-container {
  height: 150px !important;
  overflow: visible;
}

.radio-group {
  padding: 8px 0;
  background-color: white;
}

/* Estilos específicos para radio buttons */
:deep(.v-radio-group) {
  margin-top: 0 !important;
}

:deep(.v-radio) {
  margin-bottom: 12px !important;
}

:deep(.v-selection-control) {
  min-height: 40px !important;
}

:deep(.v-selection-control__wrapper) {
  height: auto !important;
}

.action-btn {
  background-color: #B7C3E8;
  color: #333;
  font-weight: 500;
}

.button-container {
  display: inline-block;
  position: relative;
}

.help-text {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.75rem;
  color: #757575;
  margin-top: 4px;
}

/* Estilos para el tooltip */
:deep(.v-tooltip .v-overlay__content) {
  background-color: #424242 !important;
  border-radius: 6px;
  padding: 8px 12px;
}

.field-label {
  margin-bottom: 4px;
  color: #616161;
  font-size: 0.875rem;
}

.info-value {
  padding: 6px 0;
  font-size: 1rem;
}

/* Especial para el contenedor de tiempo */
.time-field-container {
  height: 110px;
}

/* Especial para el contenedor de radio buttons */
.radio-container {
  height: 150px !important;
}

/* Contenedor de observaciones */
.textarea-container {
  height: 120px;
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

.arrow-separator {
  transform: rotate(0deg);
  margin: 0 4px;
}

/* Para mantener consistencia en los mensajes de error */
:deep(.v-messages) {
  min-height: 20px;
  height: 20px;
  overflow: hidden;
  position: absolute;
}

:deep(.v-field__field) {
  height: 44px !important;
  border-radius: 4px;
}

:deep(.v-field__outline) {
  border-radius: 4px !important;
}

:deep(.v-field__outline__start) {
  border-radius: 4px 0 0 4px !important;
}

:deep(.v-field__outline__end) {
  border-radius: 0 4px 4px 0 !important;
}

:deep(.v-input) {
  width: 100% !important;
}

/* Estilos para el modal */
.modal-header {
  background-color: #B7C3E8 !important;
  color: #333 !important;
  padding: 16px 24px;
}

:deep(.v-dialog .v-card-title) {
  background-color: #B7C3E8 !important;
  color: #333 !important;
  margin: 0;
}

:deep(.v-alert--variant-outlined) {
  border-width: 1px;
}

/* Mejorar la visualización de campos readonly */
:deep(.v-field--variant-filled) {
  background-color: #f5f5f5 !important;
}

:deep(.v-field--variant-filled .v-field__overlay) {
  background-color: #f5f5f5 !important;
}

/* Estilos específicos para campos readonly */
:deep(.v-text-field--readonly) {
  background-color: #f5f5f5 !important;
}

:deep(.v-text-field--readonly .v-field__overlay) {
  background-color: #f5f5f5 !important;
}

:deep(.v-text-field--readonly .v-field__field) {
  background-color: #f5f5f5 !important;
}

:deep(.v-text-field--readonly .v-field__input) {
  color: #424242 !important;
  font-weight: 500 !important;
}

:deep(.v-text-field--readonly .v-label) {
  color: #616161 !important;
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
  .button-container {
    width: 100%;
  }

  .help-text {
    font-size: 0.7rem;
    margin-top: 2px;
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
    min-height: 150px;
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
