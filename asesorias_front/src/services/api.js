import axios from 'axios';
import store from '@/store';
import router from '@/router';
import { useToast } from "vue-toastification";

const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json'
  }
});

// Crear instancia de toast
const toast = useToast();

// Interceptor de peticiones para agregar el token
apiClient.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// Interceptor de respuestas para manejar errores de autenticación
apiClient.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    let showToast = false;
    let toastMessage = '';
    
    // Si hay error 401 (No autorizado) o 403 (Prohibido)
    if (error.response?.status === 401 || error.response?.status === 403) {
      showToast = true;
      toastMessage = 'Sesión expirada. Por favor, inicia sesión nuevamente.';
    }
    // Si el backend no responde o hay error de red
    else if (error.code === 'NETWORK_ERROR' || 
             error.code === 'ECONNREFUSED' || 
             error.code === 'ERR_NETWORK' ||
             !error.response) {
      showToast = true;
      toastMessage = 'Se perdió la conexión con el servidor. Redirigiendo al login...';
    }
    
    // Mostrar toast si corresponde
    if (showToast) {
      try {
        toast.error(toastMessage, {
          timeout: 5000,
          position: 'top-center'
        });
      } catch (toastError) {
        console.warn('Error mostrando toast:', toastError);
      }
      
      // Limpiar datos de autenticación
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('profesor');
      store.commit('logout');
      
      // Redirigir al login solo si no estamos ya ahí
      setTimeout(() => {
        if (router.currentRoute.value.name !== 'Login') {
          router.push('/login');
        }
      }, 1000); // Delay para que se vea el toast
    }
    
    return Promise.reject(error);
  }
);

export default {
  // Método para probar la conexión
  testApi() {
    return apiClient.get('/api/test');
  },
  
  // Método para obtener datos del estudiante por matrícula
  getEstudiantePorMatricula(matricula) {
    return apiClient.get(`/estudiantes/${matricula}`);
  },

  // Metodo para obtener el nombre de la carrera por Id
  getCarreraPorId(id) {
    return apiClient.get(`/carreras/${id}`);
  },

  // Método para obtener las materias de un profesor por su id
  getMateriasPorProfesorId(profesorId) {
    return apiClient.get(`/profesores/${profesorId}/materias`);
  },

  // Método para obtener las estadisticas de un profesor por su id
  getEstadisticasPorProfesorId(profesorId) {
    return apiClient.get(`/profesores/${profesorId}/estadisticas`);
  },
  getDatosPerfilProfesor() {
    return apiClient.get('/profesores/datos');
  },
  // Método para mandar los datos de la asesoria al backend
  enviarAsesoria(matricula, horaInicio, horaFinal, materiaSeleccionada, lugarAsesoria, observaciones, idProfesor, fecha) {
    console.log("Enviando datos de la asesoría:", {
      matricula,
      horaInicio,
      horaFinal,
      materiaSeleccionada,
      lugarAsesoria,
      observaciones,
      idProfesor,
      fecha
    });
      return apiClient.post('/asesorias/datos', {
    matricula,
    horaInicio,
    horaFinal,
    materiaSeleccionada,
    lugarAsesoria,
    observaciones,
    idProfesor,
    fecha
  });
  },

  // Método para cambiar la contraseña de un usuario
  cambiarContrasena(usuario, contrasenaActual, nuevaContrasena) {
    return apiClient.post('/api/auth/cambiar-contrasena', {
      usuario,
      contrasenaActual,
      nuevaContrasena
    });
  },

  //Metodo para obtener las asesorias por id del profesor y por ciclo
    getAsesoriasPorProfesorYCicloValido(IdProfesor, cicloValido) {
        return apiClient.get(`/api/asesorias/historial`, {
        params: {
          idProf : IdProfesor,
          ciclo : cicloValido }
        });
    },

  // Método para actualizar la descripción del profesor
  actualizarDescripcionProfesor(idProfesor, descripcion) {
    return apiClient.post('/profesores/actualizar-descripcion', {
      idProfesor,
      descripcion
    });
  },

    // Método para obtener las asesorías usando la función asesorias_pdf
    getAsesoriasPdf(idProfesor, periodo) {
        return apiClient.get(`/api/asesorias/pdf`, {
            params: {
                idProf: idProfesor,
                periodo: periodo
            }
        });
    },

    // Método para obtener el ciclo actual usando la función obtener_ciclo
    getCicloActual() {
        return apiClient.get('/api/asesorias/ciclo-actual');
    },
}