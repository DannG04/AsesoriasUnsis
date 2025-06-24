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
        <button class="button-cambiar-contrasena" @click="irACambioContrasena">
          <div><IconifyIcon icon="uis:lock" width="50" height="50" /></div>
        </button>
        <span>Seguridad</span>
      </div>
    </div>    <div class="con-info-general">
       <!-- Información general del usuario -->      <div class="izquierda">
        <!--Correo-->
        <div class="con-info-specific">
          <div><IconifyIcon icon="guidance:mailbox" width="50" height="50" /></div>
          <div class="acomodar-info-specific">
            <span class="style-label">Dirección Email</span>
            <span>{{ correo }}</span>
          </div>
        </div>
        <!-- Rol del profesor -->
        <div class="con-info-specific">
            <div><IconifyIcon icon="material-symbols-light:book-2-outline" width="50" height="50" /></div>
            <div class="acomodar-info-specific">
              <span class="style-label">Rol</span>
              <span>{{ rol }}</span>
            </div>
        </div>
        <!--Edificio-->
        <div class="con-info-specific">
          <div><IconifyIcon icon="weui:location-outlined" width="50" height="50" /></div>
          <div class="acomodar-info-specific">
            <span class="style-label">Edificio</span>
            <span>{{ edificio }}</span>
          </div>
        </div>
        <!--Cubículo-->
        <div class="con-info-specific">
        <div><IconifyIcon icon="mdi-light:home" width="50" height="50" /></div>
        <div class="acomodar-info-specific">
          <span class="style-label">Cubículo</span>
          <span>{{ cubiculo }}</span>
        </div>
        </div>
      </div>
      <!--Descripción-->
      <div class="descripcion-columna">
        <div class="descripcion-header">
          <div><IconifyIcon icon="system-uicons:message-writing" width="50" height="50" /></div>
          <div class="descripcion-info">
            <span class="style-label">Descripción</span>
          </div>
          <div class="justo">
            <!-- Botón para editar la descripción -->
            <button class="button-pen" @click="irAModifiarPerfil">
              <div><IconifyIcon icon="material-symbols-light:ink-pen-outline-sharp" width="50" height="50" /></div>
            </button>
          </div>
        </div>
        <div class="descripcion-content">
          <span class="style-text">{{ descripcion }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '@/services/api'
import { mapGetters } from 'vuex'

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
      descripcion: '',
      datos: [] // Para almacenar los datos de la API
    }
  },
  computed: {
    ...mapGetters(['currentProfesor'])
  },
  //Esto es en lo que se obtienen los datos del perfil
  async mounted() {    
    // Primero obtener el nombre del store si está disponible
    this.obtenerNombreDelStore();
    // Luego llamar a la API para los demás datos
    await this.obtenerDatosPerfil();
  },  
  methods: {
    obtenerNombreDelStore() {
      // Obtener el nombre del profesor desde el store de Vuex
      if (this.currentProfesor?.nomProf) {
        const nombreCompleto = [
          this.currentProfesor.nomProf,
          this.currentProfesor.apellidoProf,
          this.currentProfesor.apellidoMProf
        ].filter(Boolean).join(' ');
        
        this.nombre = nombreCompleto;
        console.log('Nombre obtenido del store:', this.nombre);
      } else {
        console.log('No hay datos del profesor en el store');
      }
    },
    async obtenerDatosPerfil() {
      try {
        const response = await api.getDatosPerfilProfesor();        
        // Los datos vienen en response.data como array
        const datos = response.data || response;
        console.log('Datos procesados:', datos);
        
        // Almacenar los datos completos
        this.datos = datos;
          // Los datos vienen como array, tomar el primer elemento
        if (Array.isArray(datos) && datos.length > 0) {
          const perfilData = datos[0];
          console.log('Datos del perfil:', perfilData);          
          
          // Solo sobrescribir el nombre si no lo tenemos del store
          if (!this.nombre) {
            this.nombre = perfilData.nombre || perfilData.name || '';
          }
          
          this.correo = perfilData.correo || perfilData.email || perfilData.imail || '';
          this.rol = perfilData.rol || perfilData.role || '';
          this.edificio = perfilData.edificio || perfilData.edifisio || '';
          this.cubiculo = perfilData.cubiculo || perfilData.qbiqlo || '';
          this.idioma = perfilData.idioma || perfilData.language || '';
          this.descripcion = perfilData.descripcion || perfilData.descripsion || '';
          
          console.log('Datos asignados:', {
            nombre: this.nombre,
            correo: this.correo,
            rol: this.rol,
            edificio: this.edificio,
            cubiculo: this.cubiculo,
            idioma: this.idioma,
            descripcion: this.descripcion
          });
        } else {
          console.warn('Los datos no vienen como array o están vacíos:', datos);        }
      } catch (error) {
        console.error('Error al obtener los datos del perfil:', error);
      }
    },
    irACambioContrasena() {
      // Navegar al componente de cambio de contraseña
      this.$router.push('/cambio-contrasena');
    },
    irAModifiarPerfil() {
      // Navegar al componente de cambio de contraseña
      this.$router.push('/Datos-Usuario');
    }
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
  padding: 15px;
  border-radius: 30px;
  width: 90%;
  max-width: 1000px;
  margin: 15px auto;
  display: flex;
  flex-direction: row;
  gap: 15px;
  min-height: 320px;
  align-items: flex-start;
  justify-content: flex-start;
}

.arriba {
  background-color: #fff;
  width: 90%;
  max-width: 1200px;
  height: auto;
  margin: 10px auto;
  flex-direction: row;
  display: flex;
  border-radius: 20px;
  padding: 20px;
}

/*Estilo para la información específica*/
.con-info-specific {
  flex-grow: 1;
  width: 100% ;
  display: flex;           
  flex-direction: row;     
  align-items: center;     
  gap: 12px;
  padding: 12px;
  background-color: #DDE5FF;
  border-radius: 10px;
  margin-bottom: 8px;
}

/* Estilos para texto de la info específica */
.acomodar-info-specific {
  flex-direction: column;
  display: flex;
  padding-left: 2%;
  flex: 1;
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
  gap: 8px;
  padding-right: 10px;
}

.derecha {
  flex: 1;
  background-color: #DDE5FF;
  padding: 0;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
}

.justo {
  display: flex;
  width: auto;
  height: auto;
  align-items: center;
  flex-direction: column;
}

.principal {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
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

/* Responsive Design */
@media (max-width: 768px) {
  .con-info-general {
    flex-direction: column;
    width: 95%;
    padding: 15px;
    gap: 15px;
  }

  .arriba {
    width: 95%;
    flex-direction: column;
    text-align: center;
    gap: 15px;
    padding: 15px;
  }

  .centro {
    gap: 10px;
  }

  .izquierda {
    width: 100%;
  }

  .derecha {
    width: 100%;
    margin-top: 10px;
  }

  .con-info-specific {
    flex-direction: row;
    gap: 15px;
    padding: 10px;
    background-color: rgba(255, 255, 255, 0.1);
    border-radius: 10px;
    margin-bottom: 8px;
  }

  .acomodar-info-specific {
    padding-left: 0;
  }

  .justo {
    margin-top: 15px;
    justify-content: center;
  }

  .button-pen {
    width: 60px;
    height: 60px;
  }

  .button-cambiar-contrasena {
    width: 60px;
    height: 60px;
  }

  .imagen-perfil img {
    width: 150px;
    height: 150px;
  }
}

@media (max-width: 480px) {
  .con-info-general {
    width: 98%;
    padding: 10px;
    margin: 10px auto;
  }

  .arriba {
    width: 98%;
    padding: 10px;
    margin: 5px auto;
  }

  .con-info-specific {
    flex-direction: column;
    text-align: center;
    gap: 8px;
  }

  .style-label {
    font-size: 100%;
  }

  .style-text {
    font-size: 110%;
  }

  .imagen-perfil img {
    width: 120px;
    height: 120px;
  }

  .button-pen {
    width: 50px;
    height: 50px;
  }

  .button-cambiar-contrasena {
    width: 50px;
    height: 50px;
  }
}

/* Alineación específica para la descripción */
.descripcion-container {
  margin-top: 0;
  padding-top: 0;
  padding: 20px;
  height: 100%;
  background-color: #DDE5FF;
  border-radius: 15px;
}

/* Estilos específicos para la descripción alineada */
.descripcion-columna {
  flex: 1;
  background-color: #DDE5FF;
  padding: 15px;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 0;
  margin-left: 10px;
  margin-top: -15px;
  max-width: 400px;
  min-width: 300px;
  width: 400px;
}

.descripcion-header {
  display: flex;
  align-items: center;
  gap: 12px;
  width: 100%;
  margin-bottom: 15px;
}

.descripcion-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding-left: 2%;
}

.descripcion-content {
  width: 100%;
  padding-left: 0px;
  flex: 1;
  line-height: 1.6;
  text-align: justify;
  max-height: 200px;
  overflow-y: auto;
  word-wrap: break-word;
  word-break: break-word;
}

</style>