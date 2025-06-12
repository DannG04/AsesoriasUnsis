import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json'
  }
});

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

  // Método para mandar los datos de la asesoria al backend
  enviarAsesoria(asesoriaData) {
    return apiClient.post('/asesorias/datos', asesoriaData);
  },
  
}