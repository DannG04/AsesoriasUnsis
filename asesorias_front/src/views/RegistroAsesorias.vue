<template>
  <v-container class="bg-green-lighten-3 pa-0 fill-height">
    <v-card class="mx-auto my-8 pa-6" width="900" rounded="lg">


      <v-form ref="form" v-model="valid" class="form-fixed-height">
        <!-- Profesor y Periodo -->
        <div class="d-flex mb-4">
          <div class="mr-4 flex-grow-1">
            <div class="text-subtitle-1 text-grey-darken-1">Nombre del Profesor:</div>
            <div class="text-body-1 font-weight-medium">{{ profesor }}</div>
          </div>
          
          <div class="flex-grow-1">
            <div class="text-subtitle-1 text-grey-darken-1">Periodo:</div>
            <div class="text-body-1 font-weight-medium">{{ periodo }}</div>
          </div>
        </div>

        <!-- Datos del estudiante -->
        <div class="d-flex mb-4">
          <v-text-field
            label="Matrícula:"
            v-model="matricula"
            required
            variant="outlined"
            :rules="matriculaRules"
            class="mr-4 field-fixed-height"
            density="comfortable"
            width="200"
            persistent-hint
            hint=" "
          ></v-text-field>

          <v-text-field
            label="Nombre del alumno:"
            v-model="nombreAlumno"
            required
            variant="outlined"
            :rules="requiredRules"
            class="mr-4 field-fixed-height"
            density="comfortable"
            width="250"
            persistent-hint
            hint=" "
          ></v-text-field>

          <v-text-field
            label="Apellidos del alumno:"
            v-model="apellidosAlumno"
            required
            variant="outlined"
            :rules="requiredRules"
            class="field-fixed-height"
            density="comfortable"
            width="250"
            persistent-hint
            hint=" "
          ></v-text-field>
        </div>

        <!-- Licenciatura y Grupo -->
        <div class="d-flex mb-4">
          <v-text-field
            label="Licenciatura:"
            v-model="licenciatura"
            required
            variant="outlined"
            :rules="requiredRules"
            class="mr-4 field-fixed-height"
            density="comfortable"
            width="350"
            persistent-hint
            hint=" "
          ></v-text-field>

          <v-text-field
            label="Grupo:"
            v-model="grupo"
            required
            variant="outlined"
            :rules="requiredRules"
            class="field-fixed-height"
            density="comfortable"
            width="150"
            persistent-hint
            hint=" "
          ></v-text-field>
        </div>

        <!-- Fecha y Duración -->
        <div class="d-flex mb-4">
          <v-text-field
            label="Fecha:"
            v-model="fecha"
            required
            type="date"
            variant="outlined"
            :rules="requiredRules"
            class="mr-4 field-fixed-height"
            density="comfortable"
            width="200"
            persistent-hint
            hint=" "
          ></v-text-field>

          <div class="time-container mr-4">
            <label class="text-subtitle-1 pb-1 d-block text-grey-darken-1">Duración de la asesoría: *</label>
            <div class="d-flex">
              <v-text-field
                label="Hora de inicio"
                v-model="horaInicio"
                type="time"
                variant="outlined"
                :rules="requiredRules"
                class="mr-2 field-fixed-height"
                density="comfortable"
                width="150"
                persistent-hint
                hint=" "
              ></v-text-field>

              <v-text-field
                label="Hora final"
                v-model="horaFinal"
                type="time"
                variant="outlined"
                :rules="requiredRules"
                class="field-fixed-height"
                density="comfortable"
                width="150"
                persistent-hint
                hint=" "
              ></v-text-field>
            </div>
          </div>

          <v-menu offset-y>
            <template v-slot:activator="{ props }">
              <v-text-field
                label="Materia:"
                v-model="materiaSeleccionada"
                variant="outlined"
                readonly
                :rules="requiredRules"
                v-bind="props"
                class="field-fixed-height"
                density="comfortable"
                append-icon="mdi-menu-down"
                width="180"
                persistent-hint
                hint=" "
                bg-color="#B7C3E8"
              ></v-text-field>
            </template>
            <v-list>
              <v-list-item
                v-for="(materia, index) in materias"
                :key="index"
                :value="materia"
                @click="seleccionarMateria(materia)"
              >
                <v-list-item-title>{{ materia }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>

        <!-- Lugar y Observaciones -->
        <div class="d-flex mb-4">
          <div class="mr-6 radio-container">
            <label class="text-subtitle-1 mb-1 d-block text-grey-darken-1">En sustitución de: *</label>
            <div class="d-flex">
              <v-radio-group v-model="lugarAsesoria" row class="mt-1">
                <v-radio label="Biblioteca" value="Biblioteca"></v-radio>
                <v-radio label="Sala de computo" value="Sala de computo"></v-radio>
              </v-radio-group>
            </div>
          </div>

          <v-textarea
            label="Observaciones:"
            v-model="observaciones"
            variant="outlined"
            rows="3"
            class="flex-grow-1 field-fixed-height"
            placeholder="Ejemplo de observación"
            density="comfortable"
            persistent-hint
            hint=" "
          ></v-textarea>
        </div>

        <!-- Botones -->
        <div class="d-flex justify-end">
          <v-btn
            color="#B7C3E8"
            class="mr-4"
            @click="agregarAlumno"
          >
            Agregar Alumno
          </v-btn>
          <v-btn
            color="#B7C3E8"
            @click="registrar"
            :disabled="!valid"
          >
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

const matricula = ref('202060488');
const nombreAlumno = ref('Daniel');
const apellidosAlumno = ref('González Ruiz');
const licenciatura = ref('Informática');
const grupo = ref('606');
const fecha = ref('2025-11-30');
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
  v => /^\d{9}$/.test(v) || 'La matrícula debe tener 9 dígitos'
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
.v-card-title {
  color: #333;
}

.field-fixed-height {
  height: 80px !important;
}

.form-fixed-height {
  min-height: 540px;
}

.v-text-field, .v-textarea {
  width: 100%;
}

.time-container {
  width: 310px;
}

.radio-container {
  width: 250px;
  min-height: 80px;
}

/* Para mantener consistencia en los mensajes de error */
:deep(.v-messages) {
  min-height: 20px;
}

:deep(.v-field__field) {
  height: 44px !important;
}
</style>