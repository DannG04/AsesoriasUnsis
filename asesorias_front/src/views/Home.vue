<template>
  <div class="home-container">
    <!-- Indicador de sesión (opcional) -->
    <SessionIndicator :show-session-info="false" />
    
    <!-- Encabezado de bienvenida -->
    <div class="welcome-header">
      <div class="welcome-content">
        <div class="profile-section">
          <div class="profile-image">
            <img src="@/assets/usuario.png" alt="Imagen de perfil">
          </div>
          <div class="welcome-text">
            <h1 class="welcome-title">¡Bienvenido de vuelta!</h1>
            <h2 class="professor-name">{{ professorName }}</h2>
            <p class="professor-info">{{ professorRole }} • {{ professorBuilding }}</p>
          </div>
        </div>
        <div class="current-period">
          <div class="period-info">
            <h3>Periodo Actual</h3>
            <p>{{ currentPeriod }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Estadísticas rápidas -->
    <div class="quick-stats">
      <h3 class="section-title">Resumen de Actividad</h3>
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-icon">
            <IconifyIcon icon="material-symbols-light:calendar-month-outline" width="40" height="40" />
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ totalAsesorias }}</div>
            <div class="stat-label">Asesorías Impartidas</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">
            <IconifyIcon icon="material-symbols-light:group-outline" width="40" height="40" />
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ totalAlumnos }}</div>
            <div class="stat-label">Alumnos Atendidos</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">
            <IconifyIcon icon="material-symbols-light:book-2-outline" width="40" height="40" />
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ materiaMasSolicitada || 'N/A' }}</div>
            <div class="stat-label">Materia Más Solicitada</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">
            <IconifyIcon icon="material-symbols-light:schedule-outline" width="40" height="40" />
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ promedioDuracion }}</div>
            <div class="stat-label">Duración Promedio</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Materias que imparte -->
    <div class="subjects-section">
      <h3 class="section-title">Mis Materias</h3>
      <div class="subjects-grid">
        <div v-for="materia in materias" :key="materia.id" class="subject-card">
          <div class="subject-icon">
            <IconifyIcon icon="material-symbols-light:menu-book-outline" width="42" height="42" />
          </div>
          <div class="subject-name">{{ materia.nombre }}</div>
        </div>
        <div v-if="materias.length === 0" class="no-subjects">
          <IconifyIcon icon="material-symbols-light:info-outline" width="42" height="42" />
          <span>No hay materias asignadas</span>
        </div>
      </div>
    </div>

    <!-- Accesos rápidos -->
    <div class="quick-actions">
      <h3 class="section-title">Accesos Rápidos</h3>
      <div class="actions-grid">
        <div class="action-card" @click="navigateTo('/registro-asesorias')">
          <div class="action-icon primary">
            <IconifyIcon icon="material-symbols-light:add-circle-outline" width="50" height="50" />
          </div>
          <div class="action-content">
            <h4>Registrar Asesoría</h4>
            <p>Registra una nueva sesión de asesoría</p>
          </div>
        </div>
        
        <div class="action-card" @click="navigateTo('/historial-asesorias')">
          <div class="action-icon secondary">
            <IconifyIcon icon="material-symbols-light:history" width="50" height="50" />
          </div>
          <div class="action-content">
            <h4>Historial</h4>
            <p>Consulta el historial de asesorías</p>
          </div>
        </div>
        
        <div class="action-card" @click="navigateTo('/estadisticas')">
          <div class="action-icon tertiary">
            <IconifyIcon icon="material-symbols-light:analytics-outline" width="50" height="50" />
          </div>
          <div class="action-content">
            <h4>Estadísticas</h4>
            <p>Ver análisis detallado de actividad</p>
          </div>
        </div>
        
        <div class="action-card" @click="navigateTo('/perfil')">
          <div class="action-icon quaternary">
            <IconifyIcon icon="material-symbols-light:person-outline" width="50" height="50" />
          </div>
          <div class="action-content">
            <h4>Mi Perfil</h4>
            <p>Gestiona tu información personal</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Información adicional -->
    <div class="info-section">
      <div class="info-card">
        <div class="info-header">
          <IconifyIcon icon="material-symbols-light:lightbulb-outline" width="24" height="24" />
          <h4>Tip del día</h4>
        </div>
        <p>Recuerda registrar tus asesorías inmediatamente después de concluirlas para mantener un historial preciso.</p>
      </div>
      
      <div class="info-card">
        <div class="info-header">
          <IconifyIcon icon="material-symbols-light:schedule" width="24" height="24" />
          <h4>Horarios de Atención</h4>
        </div>
        <p>{{ professorSchedule || 'Consulta tu horario de cubículo en el sistema académico.' }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import api from '@/services/api';
import SessionIndicator from '@/components/SessionIndicator.vue';

export default {
  name: 'HomeView',
  components: {
    SessionIndicator
  },
  setup() {
    // Store y Router
    const store = useStore();
    const router = useRouter();

    // Computed para obtener datos del profesor
    const currentProfesor = computed(() => store.getters.currentProfesor);

    // Variables reactivas para la información del profesor
    const professorName = ref('Cargando...');
    const professorRole = ref('Profesor');
    const professorBuilding = ref('');
    const professorSchedule = ref('');

    // Variables para estadísticas
    const totalAsesorias = ref('0');
    const promedioDuracion = ref('0 min');
    const totalAlumnos = ref('0');
    const materiaMasSolicitada = ref('Sin registro');

    // Variables para materias
    const materias = ref([]);

    // Periodo actual
    const currentPeriod = ref('');

    // Función para calcular el periodo académico actual
    const calcularPeriodoActual = () => {
      const ahora = new Date();
      const mes = ahora.getMonth() + 1;
      const año = ahora.getFullYear();

      const añoActual = año.toString().slice(-2);
      const añoSiguiente = (año + 1).toString().slice(-2);

      if (mes >= 2 && mes <= 7) {
        return `Febrero-Julio ${añoActual}-${añoSiguiente}A`;
      } else if (mes >= 8 && mes <= 12) {
        return `Agosto-Diciembre ${añoActual}-${añoSiguiente}B`;
      } else {
        const añoAnterior = (año - 1).toString().slice(-2);
        return `Agosto-Diciembre ${añoAnterior}-${añoActual}B`;
      }
    };

    // Función para cargar datos del perfil del profesor
    const cargarDatosPerfilProfesor = async () => {
      try {
        const response = await api.getDatosPerfilProfesor();
        const datos = response.data;

        if (Array.isArray(datos) && datos.length > 0) {
          const perfilData = datos[0];
          professorRole.value = perfilData.rol || 'Profesor';
          professorBuilding.value = perfilData.edificio || '';
          professorSchedule.value = perfilData.horarios || '';
        }
      } catch (error) {
        console.error('Error al obtener datos del perfil:', error);
      }
    };

    // Función para cargar estadísticas del profesor
    const cargarEstadisticasProfesor = async (profesorId) => {
      try {
        const response = await api.getEstadisticasPorProfesorId(profesorId);
        
        if (response.data && response.data.length >= 4) {
          totalAsesorias.value = response.data[0]?.[0] ?? '0';
          promedioDuracion.value = response.data[1]?.[0] ?? '0 min';
          totalAlumnos.value = response.data[2]?.[0] ?? '0';
          materiaMasSolicitada.value = response.data[3]?.[0] ?? 'Sin registro';
        }
      } catch (error) {
        console.error('Error al obtener estadísticas:', error);
      }
    };

    // Función para cargar materias del profesor
    const cargarMateriasProfesor = async (profesorId) => {
      try {
        const response = await api.getMateriasPorProfesorId(profesorId);
        materias.value = response.data.map(materia => ({
          id: materia[1],
          nombre: materia[0]
        }));
      } catch (error) {
        console.error('Error al obtener materias:', error);
        materias.value = [];
      }
    };

    // Función para cargar todos los datos del profesor
    const cargarDatosProfesor = async () => {
      try {
        await store.dispatch('dataProfesor');

        // Establecer el nombre del profesor
        if (currentProfesor.value?.nomProf) {
          const nombreCompleto = [
            currentProfesor.value.nomProf,
            currentProfesor.value.apellidoProf,
            currentProfesor.value.apellidoMProf
          ].filter(Boolean).join(' ');
          professorName.value = nombreCompleto;
        }

        // Cargar datos adicionales si tenemos el ID del profesor
        if (currentProfesor.value?.idProfesor) {
          await Promise.all([
            cargarEstadisticasProfesor(currentProfesor.value.idProfesor),
            cargarMateriasProfesor(currentProfesor.value.idProfesor),
            cargarDatosPerfilProfesor()
          ]);
        }
      } catch (error) {
        console.error('Error al cargar datos del profesor:', error);
      }
    };

    // Función para navegar a otras vistas
    const navigateTo = (route) => {
      router.push(route);
    };

    // Cargar datos al montar el componente
    onMounted(async () => {
      currentPeriod.value = calcularPeriodoActual();
      await cargarDatosProfesor();
    });

    return {
      // Datos del profesor
      professorName,
      professorRole,
      professorBuilding,
      professorSchedule,
      
      // Estadísticas
      totalAsesorias,
      promedioDuracion,
      totalAlumnos,
      materiaMasSolicitada,
      
      // Materias
      materias,
      
      // Periodo actual
      currentPeriod,
      
      // Métodos
      navigateTo
    };
  }
};
</script>

<style scoped>
.home-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
  padding-top: 3%;
  min-height: 100vh;
}

/* Encabezado de bienvenida */
.welcome-header {
  background: #ffffff;
  border-top: #378342 solid 5px;
  border-radius: 30px;
  padding: 2rem;
  margin-bottom: 2rem;
  color: #000000;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.welcome-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 1rem;
}

.profile-section {
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.profile-image img {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  border: 2px solid #378342;
  object-fit: cover;
}

.welcome-title {
  font-size: 28px;
  font-weight: 600;
  margin: 0 0 0.5rem 0;
  color: #000000;
}

.professor-name {
  font-size: 20px;
  font-weight: 700;
  margin: 0 0 0.25rem 0;
  color: #000000;
}

.professor-info {
  font-size: 16px;
  margin: 0;
  color: #000000;
}

.current-period {
  text-align: right;
}

.period-info h3 {
  font-size: 18px;
  font-weight: 600;
  margin: 0 0 0.5rem 0;
  color: #000000;
}

.period-info p {
  font-size: 16px;
  font-weight: 700;
  margin: 0;
  color: #000000;
}

/* Secciones */
.section-title {
  font-size: 28px;
  font-weight: 600;
  color: #000000;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

/* Estadísticas rápidas */
.quick-stats {
  margin-bottom: 2rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.stat-card {
  background: #ffffff;
  border-radius: 20px;
  padding: 25px;
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: all 0.2s ease;
  border: none;
  position: relative;
}

.stat-card:hover {
  transform: translateY(-2px);
}

.stat-icon {
  background: #ffffff;
  border: #000000 solid 2px;
  border-radius: 12px;
  padding: 1rem;
  color: #000000;
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 20px;
  font-weight: 700;
  color: #000000;
  margin: 12px 0 8px 0;
}

.stat-label {
  font-size: 16px;
  font-weight: 600;
  color: #000000;
}

/* Sección de materias */
.subjects-section {
  margin-bottom: 2rem;
}

.subjects-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.subject-card {
  background: #ffffff;
  border-radius: 20px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: all 0.2s ease;
}

.subject-card:hover {
  transform: translateY(-2px);
}

.subject-icon {
  background: #ffffff;
  border-radius: 12px;
  padding: 0.75rem;
  color: #1E3D59;
  border: #1E3D59 solid 2px;
}

.subject-name {
  font-weight: 600;
  color: #000000;
  border-bottom: #1E3D59 solid 2px;
  font-size: 16px;
}

.no-subjects {
  grid-column: 1 / -1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  color: #000000;
  font-style: italic;
  padding: 2rem;
  background: #DDE5FF;
  border-radius: 20px;
}

/* Accesos rápidos */
.quick-actions {
  margin-bottom: 2rem;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}

.action-card {
  background: #ffffff;
  border: #F0F0F0 solid 3px;
  border-radius: 20px;
  padding: 25px;
  display: flex;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
  transition: all 0.2s ease;
}

.action-card:hover {
  transform: translateY(-2px);
}

.action-icon {
  border-radius: 12px;
  padding: 1rem;
}

.action-icon.primary {
  background: #ffffff;
    color: #378342;

}

.action-icon.secondary {
  background: #ffffff;
    color: #eacc08;
}

.action-icon.tertiary {
  background: #ffffff;
    color: #f73791;
}

.action-icon.quaternary {
  background: #ffffff;
    color: #0830df;
}

.action-content h4 {
  font-size: 18px;
  font-weight: 700;
  color: #000000;
  margin: 0 0 0.25rem 0;
}

.action-content p {
  font-size: 14px;
  color: #000000;
  margin: 0;
}

/* Información adicional */
.info-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.info-card {
  background: #ffffff;
  border: #F0F0F0 solid 3px;
  border-radius: 20px;
  padding: 25px;
}

.info-header {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 1rem;
  color: #000000;
}

.info-header h4 {
  font-size: 18px;
  font-weight: 700;
  margin: 0;
}

.info-card p {
  color: #000000;
  line-height: 1.6;
  margin: 0;
  font-size: 14px;
}

/* Responsive */
@media (max-width: 768px) {
  .home-container {
    padding: 1rem;
    padding-top: 5%;
  }

  .welcome-content {
    flex-direction: column;
    text-align: center;
  }

  .current-period {
    text-align: center;
  }

  .welcome-title {
    font-size: 24px;
  }

  .professor-name {
    font-size: 18px;
  }

  .stats-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }

  .subjects-grid {
    grid-template-columns: 1fr;
  }

  .actions-grid {
    grid-template-columns: 1fr;
  }

  .info-section {
    grid-template-columns: 1fr;
  }

  .stat-card {
    padding: 20px;
  }

  .section-title {
    font-size: 24px;
  }
}

/* Animaciones */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.stat-card {
  animation: fadeInUp 0.6s ease forwards;
}

.stat-card:nth-child(1) { animation-delay: 0.1s; }
.stat-card:nth-child(2) { animation-delay: 0.2s; }
.stat-card:nth-child(3) { animation-delay: 0.3s; }
.stat-card:nth-child(4) { animation-delay: 0.4s; }

.subject-card {
  animation: fadeInUp 0.6s ease forwards;
}

.action-card {
  animation: fadeInUp 0.6s ease forwards;
}

.info-card {
  animation: fadeInUp 0.6s ease forwards;
}
</style>
