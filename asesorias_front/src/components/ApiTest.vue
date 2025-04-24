<template>
    <div>
      <h1>Mi Aplicación</h1>
      <p>Prueba de conexión a Spring Boot</p>
      <button @click="probarAPI">Probar API</button>
      <p v-if="respuesta">{{ respuesta }}</p>
      <p v-if="error" class="error">{{ error }}</p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        respuesta: '',
        error: ''
      }
    },
    methods: {
      async probarAPI() {
        this.respuesta = '';
        this.error = '';
        
        try {
          const response = await axios.get('http://localhost:8080/api/t');
          this.respuesta = response.data;
        } catch (error) {
          console.error('Error al conectar con la API:', error);
          this.error = `Error al conectar con la API: ${error.message}`;
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .error {
    color: red;
  }
  </style>