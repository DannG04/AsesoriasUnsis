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
                <label>{{ usuario }}</label>
                <input class="input" type="password" placeholder="Contraseña"/>
                <input class="input" type="password" placeholder="Nueva Contraseña"/>
                <input class="input" type="password" placeholder="Confirmar Contraseña"/>
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
            <button class="boton-guardar">Guardar cambios</button>
            <button class="boton-cancelar">Cancelar</button>
          </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'PerfilView',
  data() {
    return {
      usuario: ''
    }
},
    mounted() {
        this.usuario = '2022060488li'
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