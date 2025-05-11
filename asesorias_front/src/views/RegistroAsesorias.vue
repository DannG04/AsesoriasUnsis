<template> <v-container class="pa-0 fill-height full-size registro-container">
    <v-card class="mx-auto pa-6 full-size" rounded="lg" elevation="0">
      <v-form ref="form" v-model="valid" class="form-fixed-height"> <!-- Profesor y Periodo -->
        <div class="d-flex mb-4 info-header">
          <div class="mr-4 flex-grow-1">
            <div class="text-subtitle-1 text-grey-darken-1">Nombre del Profesor:</div>
            <div class="text-body-1 font-weight-medium info-value">{{ profesor }}</div>
          </div>

          <div class="flex-grow-1">
            <div class="text-subtitle-1 text-grey-darken-1">Periodo:</div>
            <div class="text-body-1 font-weight-medium info-value">{{ periodo }}</div>
          </div>
        </div> <!-- Primera fila: Matrícula, Nombre y Apellidos -->
        <div class="d-flex mb-4">
          <div class="field-container mr-4">
            <div class="field-label">Matrícula:</div>
            <v-text-field v-model="matricula" required variant="outlined" :rules="matriculaRules" class="bordered-field"
              density="comfortable"></v-text-field>
          </div>
          <div class="field-container mr-4">
            <div class="field-label">Nombre del alumno:</div>
            <v-text-field v-model="nombreAlumno" required variant="outlined" :rules="requiredRules"
              class="bordered-field" density="comfortable"></v-text-field>
          </div>
          <div class="field-container">
            <div class="field-label">Apellidos del alumno:</div>
            <v-text-field v-model="apellidosAlumno" required variant="outlined" :rules="requiredRules"
              class="bordered-field" density="comfortable"></v-text-field>
          </div>
        </div>

        <!-- Segunda fila: Licenciatura y Grupo -->
        <div class="d-flex mb-4">
          <div class="field-container mr-4">
            <div class="field-label">Licenciatura:</div>
            <v-text-field v-model="licenciatura" required variant="outlined" :rules="requiredRules"
              class="bordered-field" density="comfortable"></v-text-field>
          </div>
          <div class="field-container">
            <div class="field-label">Grupo:</div>
            <v-text-field v-model="grupo" required variant="outlined" :rules="requiredRules" class="bordered-field"
              density="comfortable"></v-text-field>
          </div>
        </div>

        <!-- Tercera fila: Fecha, Duración y Materia -->
        <div class="d-flex mb-4">
          <div class="field-container mr-4">
            <div class="field-label">Fecha:</div>
            <v-text-field v-model="fecha" required type="date" variant="outlined" :rules="requiredRules"
              class="bordered-field" density="comfortable"></v-text-field>
          </div>
          <div class="field-container mr-4 time-field-container">
            <div class="field-label">Duración de la asesoría:</div>
            <div class="d-flex time-inputs-container">
              <div class="time-input-wrapper">
                <v-text-field label="Hora de inicio" v-model="horaInicio" type="time" variant="outlined"
                  :rules="requiredRules" class="bordered-field" density="comfortable"></v-text-field>
              </div>
              <div class="mx-1 d-flex align-center arrow-separator">→</div>
              <div class="time-input-wrapper">
                <v-text-field label="Hora final" v-model="horaFinal" type="time" variant="outlined"
                  :rules="requiredRules" class="bordered-field" density="comfortable"></v-text-field>
              </div>
            </div>
          </div>

          <div class="field-container">
            <div class="field-label">Materia:</div>
            <v-menu offset-y>
              <template v-slot:activator="{ props }"> <v-text-field v-model="materiaSeleccionada" variant="outlined"
                  readonly :rules="requiredRules" v-bind="props" class="bordered-field" density="comfortable"
                  append-icon="mdi-menu-down" bg-color="#B7C3E8"></v-text-field>
              </template>
              <v-list>
                <v-list-item v-for="(materia, index) in materias" :key="index" :value="materia"
                  @click="seleccionarMateria(materia)">
                  <v-list-item-title>{{ materia }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </div>

        <!-- Cuarta fila: Lugar y Observaciones -->
        <div class="d-flex mb-4">
          <div class="field-container mr-4 radio-container">
            <div class="field-label">En sustitución de:</div>
            <v-radio-group v-model="lugarAsesoria" :row="$vuetify.display ? $vuetify.display.mdAndUp : true"
              class="radio-group">
              <v-radio label="Biblioteca" value="Biblioteca"></v-radio>
              <v-radio label="Sala de computo" value="Sala de computo"></v-radio>
            </v-radio-group>
          </div>
          <div class="field-container textarea-container">
            <div class="field-label">Observaciones:</div> <v-textarea v-model="observaciones" variant="outlined"
              rows="2" class="bordered-field" placeholder="Ejemplo de observación" density="comfortable"></v-textarea>
          </div>
        </div> <!-- Botones de acción -->
        <div class="d-flex justify-end mt-6 action-buttons">
          <v-btn color="#B7C3E8" class="mr-4 action-btn" @click="agregarAlumno">
            <v-icon class="mr-1">mdi-account-plus</v-icon>
            Agregar Alumno
          </v-btn>
          <v-btn color="#B7C3E8" class="action-btn" @click="registrar" :disabled="!valid">
            <v-icon class="mr-1">mdi-content-save</v-icon>
            Registrar
          </v-btn>
        </div>
      </v-form>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref } from 'vue';

// Variables reactivas
const valid = ref(false);
const profesor = ref('Eren Jaeger López Cruz');
const periodo = ref('Febrero-Julio 25-26A');

const matricula = ref('2022060488');
const nombreAlumno = ref('Daniel');
const apellidosAlumno = ref('González Ruiz');
const licenciatura = ref('Informática');
const grupo = ref('606');
const fecha = ref('');
const horaInicio = ref('');
const horaFinal = ref('');
const materiaSeleccionada = ref('Materia1');
const lugarAsesoria = ref('Biblioteca');
const observaciones = ref('');

// Lista de materias
const materias = ref(['Materia1', 'Materia2', 'Materia3', 'Materia4', 'Materia5']);

// Reglas de validación
const requiredRules = [
  v => !!v || 'Este campo es requerido',
];

// Reglas para campos específicos
const matriculaRules = [
  ...requiredRules,
  v => /^\d{9,10}$/.test(v) || 'La matrícula debe tener entre 9 y 10 dígitos'
];

// Métodos
const seleccionarMateria = (materia) => {
  materiaSeleccionada.value = materia;
};

const agregarAlumno = () => {
  // Lógica para agregar un alumno
  console.log('Agregar alumno');
  alert('Funcionalidad para agregar alumno');
};

const registrar = () => {
  // Lógica para registrar la asesoría
  console.log('Registrar asesoría');

  const formData = {
    profesor: profesor.value,
    periodo: periodo.value,
    matricula: matricula.value,
    nombreAlumno: nombreAlumno.value,
    apellidosAlumno: apellidosAlumno.value,
    licenciatura: licenciatura.value,
    grupo: grupo.value,
    fecha: fecha.value,
    horaInicio: horaInicio.value,
    horaFinal: horaFinal.value,
    materia: materiaSeleccionada.value,
    lugarAsesoria: lugarAsesoria.value,
    observaciones: observaciones.value
  };

  console.log(formData);
  alert('Asesoría registrada correctamente');
};
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
    /* Anular altura fija en móvil */
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
    /* Botones más grandes en móvil */
    font-size: 16px;
  }

  .v-card {
    padding: 16px !important;
    margin: 0 !important;
    border-radius: 0;
  }

  /* Ajustes para el campo de hora en móvil */
  .time-inputs-container {
    flex-direction: column;
  }

  .time-input-wrapper {
    width: 100%;
    margin-bottom: 8px;
  }

  .mx-1.d-flex.align-center {
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

  /* Ajustes adicionales para móvil */
  .form-fixed-height {
    min-height: auto !important;
    padding-bottom: 80px;
    /* Espacio para los botones fijos */
  }

  /* Botones fijos en la parte inferior en móvil */
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
}

/* Estilos para la información estática */
.info-value {
  padding: 6px 0;
  font-size: 1rem;
}

/* Ajustes específicos para la flecha separadora en móvil */
@media (max-width: 768px) {
  .arrow-separator {
    transform: rotate(90deg);
    margin: 8px 0;
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
    /* Evita zoom automático en iOS */
  }

  :deep(.v-label) {
    font-size: 14px;
  }

  .field-label {
    font-size: 0.95rem;
    margin-bottom: 6px;
  }

  /* Mejorar aspecto de botones en móvil */
  .v-btn {
    font-size: 15px !important;
    height: 44px;
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
  @media screen and (orientation: landscape) {
    .form-fixed-height {
      padding-bottom: 70px;
    }
  }
}
</style>