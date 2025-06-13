<template>
  <div class="principal">
    <div class="arriba">
      <div class="centro">
      <div class="imagen-perfil">
        <img src="@/assets/usuario.png" alt="Imagen de perfil">
      </div>
     <h1>{{ nombre }}</h1>
    </div>
    <div class="justo">
      <!-- Botón para cambiar contraseña -->
      <button class="button-cambiar-contrasena">
        <svg xmlns="http://www.w3.org/2000/svg" width="54" height="54" viewBox="0 0 24 24">
          <path fill="currentColor" d="M17 9V7c0-2.8-2.2-5-5-5S7 4.2 7 7v2c-1.7 0-3 1.3-3 3v7c0 1.7 1.3 3 3 3h10c1.7 0 3-1.3 3-3v-7c0-1.7-1.3-3-3-3M9 7c0-1.7 1.3-3 3-3s3 1.3 3 3v2H9z" />
        </svg>
      </button>
    </div>
    </div>
    <div class="con-info-general">
       <!-- Información general del usuario -->
      <div class="izquierda">
        <!--Correo-->
        <div class="con-info-specific">
          <svg xmlns="http://www.w3.org/2000/svg" width="54" height="54" viewBox="0 0 16 16">
		<g fill="currentColor">
			<path d="M4 4a3 3 0 0 0-3 3v6h6V7a3 3 0 0 0-3-3m0-1h8a4 4 0 0 1 4 4v6a1 1 0 0 1-1 1H1a1 1 0 0 1-1-1V7a4 4 0 0 1 4-4m2.646 1A4 4 0 0 1 8 7v6h7V7a3 3 0 0 0-3-3z" />
			<path d="M11.793 8.5H9v-1h5a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.354-.146zM5 7c0 .552-.448 0-1 0s-1 .552-1 0a1 1 0 0 1 2 0" />
		</g>
	</svg>
          <div class="acomodar-info-specific">
            <span class="style-label">Correo</span>
            <span>{{ correo }}</span>
          </div>
        </div>
        <!-- Rol del profesor -->
        <div class="con-info-specific">
            <svg xmlns="http://www.w3.org/2000/svg" width="54" height="54" viewBox="0 0 24 24">
		<path fill="currentColor" d="M6 16.363q.33-.29.735-.442q.403-.152.88-.152h.77V4H8q-.817 0-1.409.591Q6 5.183 6 6zm3.385-.594H18V4.616q0-.27-.173-.443T17.385 4h-8zM6 16.363V4.385zM7.616 21q-1.085 0-1.85-.766Q5 19.47 5 18.385V6q0-1.258.871-2.129T8 3h9.385q.67 0 1.143.472q.472.472.472 1.144V16.37q0 .162-.114.286q-.115.124-.309.19q-.523.136-.858.557t-.334.98q0 .541.325.965t.848.574q.215.073.329.21t.113.304v.057q0 .214-.143.36T18.5 21zm0-1h9.363q-.285-.33-.44-.732q-.155-.4-.155-.884q0-.457.152-.87t.443-.745H7.616q-.689 0-1.152.476T6 18.385q0 .688.464 1.151T7.616 20" />
	</svg>
            <div class="acomodar-info-specific">
              <span class="style-label">Rol</span>
              <span>{{ rol }}</span>
            </div>
        </div>
        <!--Edificio-->
        <div class="con-info-specific">
          <svg xmlns="http://www.w3.org/2000/svg" width="54" height="54" viewBox="0 0 32 32">
            <g fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
              <circle cx="16" cy="11" r="4" />
              <path d="M24 15c-3 7-8 15-8 15s-5-8-8-15s2-13 8-13s11 6 8 13" />
            </g>
          </svg>
          <div class="acomodar-info-specific">
            <span class="style-label">Edificio</span>
            <span>{{ edificio }}</span>
          </div>
        </div>
        <!--Cubículo-->
        <div class="con-info-specific">
        <svg xmlns="http://www.w3.org/2000/svg" width="54" height="54" viewBox="0 0 20 20">
		<g fill="currentColor" fill-rule="evenodd" clip-rule="evenodd">
			<path d="M1 10h1.389v7a.5.5 0 0 0 .5.5H16.11a.5.5 0 0 0 .5-.5v-7H18a.5.5 0 0 0 .33-.875l-8.5-7.5a.5.5 0 0 0-.66 0l-8.5 7.5A.5.5 0 0 0 1 10m1.889-1h-.567L9.5 2.667L16.678 9h-.567a.5.5 0 0 0-.5.5v7H3.39v-7a.5.5 0 0 0-.5-.5" />
			<path d="M10.708 11.5h-2.5a1 1 0 0 0-1 1v4a1 1 0 0 0 1 1h2.5a1 1 0 0 0 1-1v-4a1 1 0 0 0-1-1m-2.5 5v-4h2.5v4z" />
		</g>
	</svg>
        <div class="acomodar-info-specific">
          <span class="style-label">Cubículo</span>
          <span>{{ cubiculo }}</span>
        </div>
        </div>
        <!--Idioma-->
        <div class="con-info-specific">
          <svg xmlns="http://www.w3.org/2000/svg" width="54" height="54" viewBox="0 0 24 24">
            <path fill="currentColor" d="M15.327 22q-2.288 0-4.037-1.338T8.929 17.23q.76.011 1.437-.11q.676-.121 1.33-.39h5.177q.044-.381.067-.745q.022-.363.022-.744q0-.311-.022-.653q-.023-.342-.067-.666h-1.436q.188-.239.353-.479t.324-.521h4.332q-.5-1.039-1.354-1.813q-.853-.774-1.96-1.15q.049-.25.077-.506t.022-.525q2.092.611 3.43 2.36Q22 13.04 22 15.327q0 2.78-1.946 4.727Q18.107 22 15.327 22m-1.221-1.204q-.425-.75-.716-1.513q-.292-.764-.454-1.552h-2.709q.54 1.182 1.555 2t2.324 1.065m1.221.139q.53-.743.859-1.548q.328-.806.516-1.656h-2.75q.189.85.51 1.655q.32.806.865 1.548m1.221-.138q1.31-.248 2.334-1.065t1.564-2h-2.733q-.182.788-.461 1.552q-.279.763-.704 1.513m1.325-4.065h2.95q.088-.327.133-.68q.044-.353.044-.724t-.044-.724t-.133-.68h-2.95q.044.323.066.665q.022.34.022.664q0 .378-.022.74q-.022.36-.066.739m-9.199-1.385q-2.78 0-4.727-1.946T2 8.674t1.946-4.727T8.672 2t4.727 1.946t1.947 4.726t-1.946 4.727t-4.726 1.947m.003-1q2.348 0 4.009-1.664q1.66-1.664 1.66-4.013q0-2.348-1.664-4.008Q11.017 3 8.669 3T4.66 4.664Q3 6.33 3 8.677t1.664 4.009q1.665 1.66 4.013 1.66m-2.37-6.365q.291 0 .511-.21t.22-.52t-.22-.521t-.52-.21t-.51.21q-.211.209-.211.52t.21.52t.52.21m2.376 4.093q.979 0 1.791-.454t1.315-1.196l-6.231-.02q.502.763 1.324 1.216q.822.454 1.8.454m2.376-4.092q.29 0 .51-.21t.22-.52t-.22-.521t-.52-.21t-.51.21t-.211.52t.21.52t.52.21m-2.384.693" />
          </svg>
          <div class="acomodar-info-specific">
            <span class="style-label">Idioma</span>
            <span>{{ idioma }}</span>
          </div>
        </div>
      </div>
      <div class="justo">
        <svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" viewBox="0 0 24 24">
          <g fill="none" stroke="currentColor" stroke-width="1">
            <path d="M8.5 18.396V15.5h-2a1 1 0 0 1-1-1v-7a1 1 0 0 1 1-1h11a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1H12l-3.073 3.073a.25.25 0 0 1-.427-.177Z" />
            <path stroke-linecap="round" d="M8.5 12.5h7m-7-3h7" />
          </g>
        </svg>
      </div>
      <div class="derecha">
        <div class="acomodar-info-specific">
          <span class="style-label">Descripción</span>
          <span class="style-text">{{ descripcion }}</span>
        </div>
      </div>
      <div class="justo">
        <!-- Botón para editar el perfil -->
        <button class="button-pen">
          <svg xmlns="http://www.w3.org/2000/svg" width="54" height="54" viewBox="0 0 24 24">
            <path fill="currentColor" d="m13.948 12.533l-2.327-2.352l4.083-4.077l-.36-.36q-.154-.154-.461-.154t-.462.154l-4.879 4.879l-.707-.67l4.911-4.916q.485-.485 1.153-.485t1.153.485l.36.36l.827-.828q.242-.242.568-.242t.568.242l1.267 1.268q.243.242.224.53q-.02.287-.262.53zM6.462 20H4.135v-2.327l6.754-6.76l2.352 2.328z" />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PerfilView',
  data() {
    return {
      nombre: '',
      correo: '',
      rol: '',
      edificio: '',
      cubiculo: '',
      idioma: '',
      descripcion: ''
    }
  },
  //Esto es en lo que se obtienen los datos del perfil
  mounted() {
    this.nombre = 'Eren Jaeger López Cruz'
    this.correo = 'esto_es_un_ejemplo@gmail.com'
    this.rol = 'Profesor'
    this.edificio = 'Instituto de Informática'
    this.cubiculo = '17'
    this.idioma = 'Español'
    this.descripcion = 'Doctor en Ciencias de la Computación especializado en Inteligencia Artificial. Imparto cursos de Algoritmos y Minería de Datos. 15 años de experiencia docente. Disponible para asesorías en proyectos de investigación y tesis relacionadas con IA y análisis de datos.'
  }
}
</script>

<style scoped>

/* Estilos para la imagen de perfil */
.imagen-perfil img {
  width: 200px;
  height: 200px;
  object-fit: cover;
  border-radius: 50%;
  border: 2px solid #98A2C3;
}

/*Para todo el contenedor*/
.con-info-general {
  background-color: #DDE5FF;
  padding: 20px;
  border-radius: 30px;
  width: 80%;
  height: 50%;
  margin: 20px auto;
  flex-direction: row;
  display: flex;
}

.arriba {
  background-color: #fff;
  padding: 20px;
  border-radius: 30px;
  width: 80%;
  height: 50%;
  margin: 20px auto;
  flex-direction: row;
  display: flex;
}

/*Estilo para la información específica*/
.con-info-specific {
  flex-grow: 1;
  width: 100% ;
  display: flex;           
  flex-direction: row;     
  align-items: center;     
  gap: 12px;               
}

/* Estilos para texto de la info específica */
.acomodar-info-specific {
  flex-direction: column;
  display: flex;
  padding-left: 2%;
}

.centro {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.izquierda {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.derecha {
  flex: 1;
  background-color: #DDE5FF;
  padding: 20px;
  border-radius: 15px;
}

.justo {
  width: auto;
  height: auto;
  align-items: center;
}

.principal {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

/*Estilo para label*/
.style-label {
  font-size: 110%;
  font-weight: bolder;
}

.style-text {
  font-size: 120%;
  text-align: justify;
}

/* Estilo para el botón de editar */
.button-pen {
  border-radius: 50%;
  background-color: #B7C3E8;
  cursor: pointer;
  width: 80px;
  height: 80px;
}

/*Estilo para el botón de cambiar contraseña */
.button-cambiar-contrasena {
  border-radius: 50%;
  background-color: #B7C3E8;
  cursor: pointer;
  width: 80px;
  height: 80px;
}

</style>