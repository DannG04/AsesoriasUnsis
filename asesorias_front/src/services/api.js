import axios from 'axios';
import store from '@/store';
import router from '@/router';
import { useToast } from "vue-toastification";

const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000, // Timeout de 10 segundos
  headers: {
    'Content-Type': 'application/json'
  }
});

// Crear instancia de toast
const toast = useToast();

// Variable para evitar múltiples redirects simultáneos
let isRedirecting = false;

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
    // Reset del flag de redirección en respuestas exitosas
    isRedirecting = false;
    return response;
  },
  error => {
    // Evitar múltiples procesamientos simultáneos
    if (isRedirecting) {
      return Promise.reject(error);
    }

    let shouldLogout = false;
    let toastMessage = '';
    
    console.error('Error de API interceptado:', error);
    
    // Categorizar errores
    if (error.response) {
      // El servidor respondió con un código de error
      const status = error.response.status;
      
      switch (status) {
        case 401:
          shouldLogout = true;
          toastMessage = 'Sesión expirada. Por favor, inicia sesión nuevamente.';
          break;
        case 403:
          shouldLogout = true;
          toastMessage = 'No tienes permisos para realizar esta acción.';
          break;
        case 404:
          toastMessage = 'Recurso no encontrado.';
          break;
        case 500:
          shouldLogout = true;
          toastMessage = 'Error interno del servidor. Reiniciando sesión...';
          break;
        case 502:
        case 503:
        case 504:
          shouldLogout = true;
          toastMessage = 'Servidor no disponible. Verificando estado de sesión...';
          break;
        default:
          if (status >= 500) {
            shouldLogout = true;
            toastMessage = 'Error del servidor. Verificando estado de sesión...';
          }
      }
    } else if (error.request) {
      // La petición se hizo pero no hubo respuesta
      shouldLogout = true;
      toastMessage = 'No se pudo conectar con el servidor. Verificando estado de sesión...';
    } else {
      // Error en la configuración de la petición
      console.error('Error de configuración:', error.message);
      toastMessage = 'Error en la solicitud. Intenta de nuevo.';
    }
    
    // Procesar logout si es necesario
    if (shouldLogout && !isRedirecting) {
      handleLogout(toastMessage);
    } else if (toastMessage && !shouldLogout) {
      // Mostrar toast para errores que no requieren logout
      try {
        toast.error(toastMessage, {
          timeout: 5000,
          position: 'top-center'
        });
      } catch (toastError) {
        console.warn('Error mostrando toast:', toastError);
      }
    }
    
    return Promise.reject(error);
  }
);

/**
 * Maneja el proceso de logout forzado
 */
function handleLogout(message) {
  if (isRedirecting) return;
  
  isRedirecting = true;
  
  try {
    toast.error(message, {
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
  localStorage.removeItem('loginTime');
  
  // Limpiar store
  store.commit('logout');
  
  // Redirigir al login
  setTimeout(() => {
    if (router.currentRoute.value.name !== 'Login') {
      router.push('/login').finally(() => {
        isRedirecting = false;
      });
    } else {
      isRedirecting = false;
    }
  }, 1000);
}

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