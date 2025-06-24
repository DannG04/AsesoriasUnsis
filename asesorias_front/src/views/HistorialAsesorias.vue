<template>   <!-- Header con botones y buscador -->
      <v-card-text class="pa-4">
        <div class="d-flex justify-space-between align-center mb-4 flex-wrap ga-3">
          <!-- Buscador por matrícula y nombre -->
          <div class="search-container w-75">
            <v-text-field
              v-model="searchQuery"
              prepend-inner-icon="mdi-magnify"
              label="Buscar por matrícula o nombre..."
              variant="outlined"
              density="comfortable"
              hide-details
              class="search-field"
              clearable
              @update:model-value="handleSearch"
            ></v-text-field>
          </div>

          <div class="d-flex ga-2 pr-8">
            <v-btn
                icon
                variant="flat"
                color="grey-darken-1"
                size="default"
                @click="showFilterDialog = true"
            >
              <v-icon>mdi-tune</v-icon>
              <v-tooltip activator="parent" location="bottom">Filtrar por periodo</v-tooltip>
            </v-btn>

            <v-btn
                icon
                variant="flat"
                color="red"
                size="default"
                @click="exportToPDF"
            >
              <v-icon>mdi-file-pdf-box</v-icon>
              <v-tooltip activator="parent" location="bottom">Exportar PDF</v-tooltip>
            </v-btn>
          </div>
        </div>

        <!-- Título principal -->
        <div class="text-center py-6">
          <h1 class="text-h3 font-weight-bold text-grey-darken-3" >{{ selectedPeriodo || Periodo }}</h1>
        </div>

        <!-- Tabla de datos -->
        <v-data-table
            id="student-table"
            :headers="headers"
            :items="filteredStudents"
            class="elevation-2 rounded-lg "
            :items-per-page="10"
            :items-per-page-options="[5, 10, 25, 50]"
            show-current-page
            :loading="loading"
        >
          <!-- Slot para acciones -->
          <template v-slot:[`item.actions`]="{ item }">
            <div class="d-flex ga-2 justify-center">
              <v-btn
                  size="small"
                  color="red"
                  variant="flat"
                  @click="confirmDelete(item)"
                  class="text-caption"
              >
                Eliminar
              </v-btn>
            </div>
          </template>

          <!-- Slot para la columna OBS -->
          <template v-slot:[`item.obs`]="{ item }">
            <span class="text-body-2">{{ item.obs || '' }}</span>
          </template>

          <!-- Headers personalizados -->
          <template v-slot:[`header.matricula`]>
            <strong>MATRÍCULA</strong>
          </template>
          <template v-slot:[`header.alumno`]>
            <strong>ALUMNO</strong>
          </template>
          <template v-slot:[`header.carrera`]>
            <strong>CARRERA</strong>
          </template>
          <template v-slot:[`header.semestre`]>
            <strong>SEMESTRE</strong>
          </template>
          <template v-slot:[`header.materia`]>
            <strong>MATERIA</strong>
          </template>
          <template v-slot:[`header.fecha`]>
            <strong>FECHA</strong>
          </template>
          <template v-slot:[`header.obs`]>
            <strong>OBS</strong>
          </template>
        </v-data-table>
      </v-card-text>

    <!-- Dialog de filtros por periodo -->
    <v-dialog v-model="showFilterDialog" max-width="400px">
      <v-card rounded="xl">
        <v-card-title class="pa-6 bg-grey-lighten-4">Seleccionar periodo</v-card-title>
        <v-card-text class="pa-6">
          <v-select
              v-model="selectedPeriodo"
              :items="periodos"
              label="Periodo"
              variant="outlined"
              density="comfortable"
              class="mb-4"
          />
        </v-card-text>
        <v-card-actions class="pa-4">
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              variant="flat"
              @click="aplicarFiltro"
          >
            Aplicar
          </v-btn>
          <v-btn
              color="grey"
              variant="flat"
              @click="showFilterDialog = false"
          >
            Cancelar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Snackbar para notificaciones -->
    <v-snackbar
        v-model="snackbar"
        :color="snackbarColor"
        :timeout="3000"
        location="bottom right"
    >
      {{ snackbarText }}
      <template v-slot:actions>
        <v-btn
            color="white"
            variant="text"
            @click="snackbar = false"
            icon="mdi-close"
        />
      </template>
    </v-snackbar>
</template>

<script setup>
import {ref, computed, onMounted, watch} from 'vue'
import { useStore } from 'vuex';
import html2pdf from "html2pdf.js";
import api from "@/services/api";


// Estados reactivos
const snackbar = ref(false)
const snackbarText = ref('')
const snackbarColor = ref('success')
const showFilterDialog = ref(false)
const searchQuery = ref('') // Añadido para la búsqueda por matrícula o nombre
const loading = ref(false)

// Store y datos del profesor
const store = useStore();
const currentProfesor = computed(() => store.getters.currentProfesor);

//Periodo actual
const Periodo = ref('')
const selectedPeriodo = ref('')

// Función para obtener el ciclo actual desde la base de datos
const obtenerCicloActual = async () => {
  try {
    const respuesta = await api.getCicloActual();
    Periodo.value = respuesta.data;
    console.log('Ciclo actual obtenido:', Periodo.value);
    
    // Actualizar la lista de períodos para incluir el ciclo actual
    actualizarListaPeriodos(Periodo.value);
    
    return Periodo.value;
  } catch (error) {
    console.error('Error al obtener el ciclo actual:', error);
    Periodo.value = '2024-2025B'; // Valor por defecto
    return Periodo.value;
  }
};

// Headers de la tabla - exactamente como en la imagen
const headers = ref([
  { title: 'MATRÍCULA', key: 'matricula', sortable: true, width: '120px' },
  { title: 'ALUMNO', key: 'alumno', sortable: true, width: '180px' },
  { title: 'CARRERA', key: 'carrera', sortable: true, width: '130px' },
  { title: 'SEMESTRE', key: 'semestre', sortable: true, width: '100px', align: 'center' },
  { title: 'MATERIA', key: 'materia', sortable: true, width: '120px' },
  { title: 'FECHA', key: 'fecha', sortable: true, width: '110px' },
  { title: 'OBS', key: 'obs', sortable: false, width: '150px' }, // Aumentado de 80px a 150px
])

// Lista de estudiantes - será reemplazada por datos reales de la API
const students = ref([])
const studentspdf = ref([]) // Para almacenar los datos del PDF

// Lista de períodos que se actualizará dinámicamente
const periodos = ref([
  '2024-2025A',
  '2024-2025B',
  '2023-2024A',
  '2023-2024B'
])

// Función para actualizar la lista de períodos con el período actual
const actualizarListaPeriodos = (cicloActual) => {
  if (cicloActual && !periodos.value.includes(cicloActual)) {
    periodos.value.unshift(cicloActual); // Agregar al inicio de la lista
  }
};

// Computed para estudiantes filtrados
const filteredStudents = computed(() => {
  let result = [...students.value]

  // Filtrar por búsqueda
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase();
    result = result.filter(student =>
      student.matricula.toLowerCase().includes(query) ||
      student.alumno.toLowerCase().includes(query)
    );
  }

  // Ordenar por fecha (de más reciente a más antigua)
  result.sort((a, b) => {
    // Convertir las fechas a formato Date para comparar correctamente
    const fechaA = new Date(a.fecha.split('/').reverse().join('-'));
    const fechaB = new Date(b.fecha.split('/').reverse().join('-'));
    return fechaB - fechaA; // Orden descendente (más reciente primero)
  });

  return result;
})

// Funciones
// Función para cargar el historial de asesorías usando asesorias_pdf
const cargarHistorialAsesorias = async (idProfesor, periodo) => {
  loading.value = true;
  try {
    console.log(`Cargando asesorías para profesor ID: ${idProfesor} en periodo: ${periodo}`);
    const respuesta = await api.getAsesoriasPorProfesorYCicloValido(idProfesor, periodo);
    const respuestapdf = await api.getAsesoriasPdf(idProfesor, periodo);
    console.log('Datos recibidos:', respuesta.data);

    // Transformar los datos al formato que espera la tabla
    // Asegurarnos de que cada registro es único basado en sus propiedades
    students.value = respuesta.data.map(asesoria => ({
      id: asesoria.id || `${asesoria.idest}_${asesoria.fechaest}_${Math.random()}`, // Crear ID único
      matricula: asesoria.idest,
      alumno: asesoria.nombreest,
      carrera: asesoria.carreraest,
      semestre: asesoria.semestreest,
      materia: asesoria.matest,
      fecha: asesoria.fechaest,
      obs: asesoria.obsest || ''
    }));

    studentspdf.value = respuestapdf.data.map(asesoria => ({
      fechaAses : asesoria.fechaAses,
      nombreAlumn : asesoria.nombreAlumn,
      grupoAlumn : asesoria.grupoAlumn,
      enSustitcion : asesoria.enSustitcion,
      nombreMat : asesoria.nombreMat,
      horaStart : asesoria.horaStart,
      horaEnd : asesoria.horaEnd,
      obs : asesoria.obs,
    }));

    console.log('Datos transformados:', students.value);
    showSnackbar('Datos cargados correctamente', 'success');
  } catch (error) {
    console.error('Error al cargar el historial de asesorías:', error);
    showSnackbar('Error al cargar los datos', 'error');
  } finally {
    loading.value = false;
  }
};

// Función para aplicar el filtro de periodo
const aplicarFiltro = async () => {
  if (currentProfesor.value?.idProfesor) {
    const periodoAUsar = selectedPeriodo.value || Periodo.value;
    await cargarHistorialAsesorias(currentProfesor.value.idProfesor, periodoAUsar);
  }
  showFilterDialog.value = false;
};

// Cargar datos del profesor
const cargarDatosProfesor = async () => {
  try {
    await store.dispatch('dataProfesor');

    // Obtener el ciclo actual desde la base de datos
    const cicloActual = await obtenerCicloActual();

    // Cargar historial de asesorías una vez que se tengan los datos del profesor y el ciclo
    if (currentProfesor.value?.idProfesor) {
      await cargarHistorialAsesorias(currentProfesor.value.idProfesor, cicloActual);
    }
  } catch (error) {
    console.error('Error al cargar datos del profesor:', error);
    showSnackbar('Error al cargar datos del profesor', 'error');
  }
};

onMounted(async () => {
  // Cargar datos del profesor al montar el componente
  await cargarDatosProfesor();
});

// Observar cambios en selectedPeriodo
watch(selectedPeriodo, (newPeriodo) => {
  if (newPeriodo) {
    console.log(`Periodo seleccionado: ${newPeriodo}`);
  }
});

// Computed para el nombre completo del profesor
const profesorNombre = computed(() => {
  if (currentProfesor.value) {
    return [
      currentProfesor.value.nomProf,
      currentProfesor.value.apellidoProf,
      currentProfesor.value.apellidoMProf
    ].filter(Boolean).join(' ');
  }
  return 'Nombre del Profesor';
});

// Jefe de carrera - temporal, se puede cambiar según sea necesario
const jefeCarrera = 'Everardo Pacheco'; // Constante temporal

const exportToPDF = () => {
  // Configurar opciones de PDF
  const options = {
    margin: [10, 10, 10, 10],
    filename: `asesorias_${selectedPeriodo.value || Periodo.value}.pdf`,
    image: { type: 'jpeg', quality: 0.98 },
    html2canvas: { scale: 2, useCORS: true },
    jsPDF: { unit: 'mm', format: 'a4', orientation: 'landscape' },
    pagebreak: { mode: 'avoid-all' },
    footer: { height: '0mm' }
  };

  // Crear una copia temporal de la tabla con el formato de la imagen
  const printTable = document.createElement('div');
  printTable.innerHTML = `
    <div style="padding: 10px 30px 0 30px; font-family: Arial, sans-serif;">
      <div style="text-align: center; font-weight: bold; font-size: 18px;">UNIVERSIDAD DE LA SIERRA SUR</div>
      <div style="text-align: center; font-weight: bold; font-size: 15px;">DEPARTAMENTO DE SERVICIOS ESCOLARES</div>
      <div style="text-align: center; font-weight: bold; font-size: 15px; margin-bottom: 8px;">REGISTRO DE ASESORIAS</div>
      <div style="display: flex; justify-content: space-between; font-size: 13px; margin-bottom: 8px;">
        <div>Nombre del Prof: <b>${profesorNombre.value}</b></div>
        <div>PERIODO: <b>${selectedPeriodo.value || Periodo.value}</b></div>
      </div>
      <table style="width: 100%; border-collapse: collapse; font-size: 12px;">
        <thead>
          <tr style="background: #f5f5f5;">
            <th style="border: 1px solid #000; padding: 4px; width: 110px;">FECHA</th>
            <th style="border: 1px solid #000; padding: 4px;">NOMBRE DEL ALUMNO</th>
            <th style="border: 1px solid #000; padding: 4px;">GRUPO</th>
            <th style="border: 1px solid #000; padding: 4px;">EN SUSTITUCIÓN DE:<br><span style='font-weight:normal;font-size:10px;'>(Biblioteca o sala de computo)</span></th>
            <th style="border: 1px solid #000; padding: 4px;">NOMBRE DE LA MATERIA</th>
            <th style="border: 1px solid #000; padding: 4px;">HORA DE INICIO</th>
            <th style="border: 1px solid #000; padding: 4px;">HORA DE TERMINO</th>
            <th style="border: 1px solid #000; padding: 4px; width: 70px;">FIRMA DEL ALUMNO</th>
            <th style="border: 1px solid #000; padding: 4px;">OBSERVACIONES</th>
          </tr>
        </thead>
        <tbody>
          ${studentspdf.value.map(asesoria => `
            <tr>
              <td style="border: 1px solid #000; padding: 4px;">${asesoria.fechaAses || ''}</td>
              <td style="border: 1px solid #000; padding: 4px;">${asesoria.nombreAlumn || ''}</td>
              <td style="border: 1px solid #000; padding: 4px;">${asesoria.grupoAlumn || ''}</td>
              <td style="border: 1px solid #000; padding: 4px;">${asesoria.enSustitcion || ''}</td>
              <td style="border: 1px solid #000; padding: 4px;">${asesoria.nombreMat || ''}</td>
              <td style="border: 1px solid #000; padding: 4px;">${asesoria.horaStart || ''}</td>
              <td style="border: 1px solid #000; padding: 4px;">${asesoria.horaEnd || ''}</td>
              <td style="border: 1px solid #000; padding: 4px;"></td>
              <td style="border: 1px solid #000; padding: 4px;">${asesoria.obs || ''}</td>
            </tr>
          `).join('')}
          ${Array(10 - studentspdf.value.length > 0 ? 10 - studentspdf.value.length : 0).fill('<tr>' +
            '<td style="border: 1px solid #000; padding: 16px 4px; height: 24px;"></td>'.repeat(9) + '</tr>').join('')}
        </tbody>
      </table>
      <div style="display: flex; justify-content: space-between; margin-top: 40px; font-size: 13px;">
        <div style="width: 40%; text-align: center;">
          _______________________________<br>
          Nombre y firma del profesor responsable<br>
          <b>${profesorNombre.value}</b>
        </div>
        <div style="width: 40%; text-align: center;">
          _______________________________<br>
          Vo.Bo. Jefe de Carrera<br>
          <b>${jefeCarrera}</b>
        </div>
      </div>
    </div>
  `;

  // Generar el PDF
  html2pdf().from(printTable).set(options).save();
  showSnackbar('PDF exportado correctamente', 'success');
}

const showSnackbar = (text, color = 'success') => {
  snackbarText.value = text
  snackbarColor.value = color
  snackbar.value = true
}

// Función para manejar la búsqueda
const handleSearch = () => {
  // No es necesario hacer nada aquí, ya que el filtrado se hace automáticamente
  // con el computed property filteredStudents
}
</script>

<style scoped>

  .d-flex.justify-space-between {
    flex-direction: column;
    gap: 16px;
  }

  .d-flex.ga-2 {
    justify-content: center;
  }


  .text-h3 {
    font-size: 1.8rem !important;
  }

</style>