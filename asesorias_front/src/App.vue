<template>
  <div id="app">
    <nav v-if="isLoggedIn" class="navbar">
      <div class="brand">Sistema de Alumnos</div>
      <div class="nav-links">
        <router-link to="/home">Inicio</router-link>
        <a href="#" @click.prevent="logout">Cerrar Sesión</a>
        
      </div>

    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  
  name: 'App',
  computed: {
    isLoggedIn() {
      return this.$store.getters.isLoggedIn
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('logout')
        .then(() => {
          this.$router.push('/login')
        })
    }
  }

}

</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: Arial, sans-serif;
  line-height: 1.6;
  color: #333;
  background-color: #f5f5f5;
}

#app {
  width: 100%;
  min-height: 100vh;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #3498db;
  color: white;
  padding: 1rem 2rem;
}

.brand {
  font-size: 1.5rem;
  font-weight: bold;
}

.nav-links a {
  color: white;
  text-decoration: none;
  margin-left: 1rem;
}

.nav-links a:hover {
  text-decoration: underline;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}
</style>
