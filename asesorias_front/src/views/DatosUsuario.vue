<template>
    <div class="principal">
        <h1>DATOS DEL USUARIO</h1>
        <div class="general">
          <div class="contenido">
                <label class="label">Descripción:</label>
                <textarea 
                  class="input" 
                  v-model="descripcion" 
                  @input="validarLongitud"
                ></textarea>
                <p class="contador">{{ descripcion.length }}/400 caracteres</p>
          </div>
          <!-- Botones de acción -->
          <div class="botones">
            <button class="boton-guardar">Guardar cambios</button>
            <button class="boton-cancelar">Cancelar</button>
          </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'DatosUsuario',
  data() {
    return {
      descripcion: ''
    }
  },
  methods: {
    validarLongitud() {
      if (this.descripcion.length > 400) {
        this.descripcion = this.descripcion.slice(0, 400); // Trunca el texto a 400 caracteres
      }
    }
  },
  mounted() {
    this.descripcion = 'Doctor en Ciencias de la Computación especializado en Inteligencia Artificial. Imparto cursos de Algoritmos y Minería de Datos. 15 años de experiencia docente. Disponible para asesorías en proyectos de investigación y tesis relacionadas con IA y análisis de datos.';
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
    gap: 30px; /* Espacio entre columnas */
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