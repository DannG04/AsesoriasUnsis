<template>
  <div id="app">
    <!-- Login page (uses full screen) -->
    <div v-if="$route.path === '/login'" class="login-fullscreen">
      <router-view />
    </div>

    <!-- App with sidebar (for all other routes) -->
    <div v-else class="app-with-sidebar">
      <!-- Sidebar menu (always visible when logged in) -->
      <div v-if="isLoggedIn" class="sidebar">
        <div class="sidebar-header">
          <img src="@/assets/logo-unsis.png" alt="Logo UNSIS" class="logo">
        </div>

        <div class="sidebar-menu">
          <router-link to="/home" class="menu-item">
            <IconifyIcon icon="mdi-light:home" class="icon-menu" width="24" height="24" />
            <span>Inicio</span>
          </router-link>
          <router-link to="/registro-asesorias" class="menu-item">
            <IconifyIcon icon="mdi-light:file-plus" class="icon-menu" width="24" height="24" />
            <span>Registro de Asesorías</span>
          </router-link>

          <router-link to="/historial-asesorias" class="menu-item">
            <IconifyIcon icon="mdi-light:clipboard-text" class="icon-menu" width="24" height="24" />
            <span>Historial de Asesorías</span>
          </router-link>

          <router-link to="/estadisticas" class="menu-item">
            <IconifyIcon icon="mdi-light:chart-histogram" class="icon-menu" width="24" height="24" />
            <span>Estadísticas</span>
          </router-link>

          <router-link to="/perfil" class="menu-item">
            <IconifyIcon icon="mdi-light:account" class="icon-menu" width="24" height="24" />
            <span>Perfil</span>
          </router-link>

          <div class="sub-section">
            <div class="section-title">Opciones</div>
            <a href="#" @click.prevent="logout" class="menu-item">
            <IconifyIcon icon="mdi-light:logout" class="icon-menu" width="24" height="24" />
            <span>Cerrar sesión</span>
            </a>
          </div>
        </div>
      </div>

      <!-- Main content area -->
      <div class="content-wrapper">
        <header v-if="pageTitle">
          <h1 class="page-title">{{ pageTitle }}</h1>
        </header>

        <main class="main-content">
          <div class="content-box">
            <!-- Router view para cargar el contenido de las diferentes pantallas -->
            <router-view />
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      pageTitles: {
        '/home': 'INICIO',
        '/registro-asesorias': 'REGISTRO DE ASESORÍAS',
        '/historial-asesorias': 'HISTORIAL DE ASESORÍAS',
        '/estadisticas': 'ESTADÍSTICAS',
        '/perfil': 'PERFIL'
      }
    }
  },
  computed: {
    isLoggedIn() {
      return this.$store.getters.isLoggedIn
    },
    pageTitle() {
      return this.pageTitles[this.$route.path]
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('logout')
        .then(() => {
          this.$router.push('/login')
        })
        .catch(error => {
          console.error('Error durante el cierre de sesión:', error)
        })
    }
  },
  watch: {
    $route(to) {
      // Para actualizar el título según la ruta actual
      document.title = this.pageTitles[to.path] || 'Sistema UNSIS'
    }
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Lato:wght@400;700&display=swap');
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

.app-with-sidebar {
  width: 100%;
  min-height: 100vh;
  display: flex;
}

/* Login page styles */
.login-fullscreen {
  width: 100%;
  min-height: 100vh;
  background-color: #9AD7A7;
}

/* Sidebar styles based on the screenshot */
.sidebar {
  width: 234px;
  min-height: 100vh;
  background-color: white;
  border-right: 1px solid #e0e0e0;
}

.sidebar-header {
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid #e0e0e0;
}

.logo {
  max-width: 100px;
  height: auto;
}

.sidebar-menu {
  padding: 10px 0;
}

.menu-item {
  display: block;
  padding: 12px 15px;
  text-decoration: none;
  color: #333;
  border-left: 3px solid transparent;
  font-size: 14px;
  transition: background-color 0.2s ease;
}

.menu-item:hover {
  background-color: #f0f0f0;
}

.menu-item.active,
.menu-item.router-link-active {
  background-color: #9AD7A7;
  border-left: 3px solid #0e734f;
  color: #0e734f;
  font-weight: bold;
}

.menu-item i {
  margin-right: 10px;
  width: 16px;
  text-align: center;
}

.sub-section {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #e0e0e0;
}

.section-title {
  padding: 5px 15px;
  font-size: 12px;
  color: #777;
  font-weight: bold;
}

/* Content area */
.content-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #9AD7A7;
}

header {
  padding-top: 15px;
  padding-bottom: 10px;
  text-align: center;
}

.page-title {
  font-size: 40px;
  color: #000;
  font-weight: bold;
  margin: 0;
  text-transform: uppercase;
  font-family: 'Lato', sans-serif;
}

.main-content {
  padding: 20px;
  padding-top: 5px;
  flex: 1;
}

.content-box {
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  padding: 20px;
  height: 100%;
  overflow: hidden;
  border: 1px solid #86c194;
}

/* Icons */
.icon-doc:before {
  content: "📋";
}

.icon-history:before {
  content: "📜";
}

.icon-chart:before {
  content: "📊";
}

.icon-user:before {
  content: "👤";
}

.icon-logout:before {
  content: "🚪";
}

/* Estilos generales para iconos */
.icon-menu {
  vertical-align: middle;
  margin-right: 10px;
  width: 24px;
  height: 24px;
  display: inline-block;
}

/* Asegurar que el texto también esté alineado */
.menu-item span {
  vertical-align: middle;
}
</style>