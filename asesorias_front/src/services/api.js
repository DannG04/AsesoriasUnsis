import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json'
  }
});

export default {
  // Método para probar la conexión
  testApi() {
    return apiClient.get('/api/test');
  },
  
  // Agrega más métodos según tus endpoints
}