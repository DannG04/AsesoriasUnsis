<template>
  <v-app>
    <div id="app">
      <!-- Login page (uses full screen) -->
      <div v-if="$route.path === '/login'" class="login-fullscreen">
        <router-view />
      </div>

      <!-- App with sidebar (for all other routes) -->
      <div v-else class="app-with-sidebar">
        <!-- Sidebar menu -->
        <div v-if="isLoggedIn" :class="['sidebar', { 'active': menuVisible }]">
          <div class="sidebar-header">
            <img src="@/assets/logo-unsis.png" alt="Logo UNSIS" class="logo">
            <v-icon @click="toggleMenu" class="close-menu d-md-none">mdi-close</v-icon>
          </div>

          <!-- Sidebar menu items -->
          <div class="sidebar-menu">
            <router-link to="/home" class="menu-item" @click="isMobile && toggleMenu()">
              <IconifyIcon icon="mdi-light:home" class="icon-menu" width="24" height="24" />
              <span>Inicio</span>
            </router-link>
            <router-link to="/registro-asesorias" class="menu-item" @click="isMobile && toggleMenu()">
              <IconifyIcon icon="mdi-light:file-plus" class="icon-menu" width="24" height="24" />
              <span>Registro de Asesorías</span>
            </router-link>

            <router-link to="/historial-asesorias" class="menu-item" @click="isMobile && toggleMenu()">
              <IconifyIcon icon="mdi-light:clipboard-text" class="icon-menu" width="24" height="24" />
              <span>Historial de Asesorías</span>
            </router-link>

            <router-link to="/estadisticas" class="menu-item" @click="isMobile && toggleMenu()">
              <IconifyIcon icon="mdi-light:chart-histogram" class="icon-menu" width="24" height="24" />
              <span>Estadísticas</span>
            </router-link>

            <router-link to="/perfil" class="menu-item" @click="isMobile && toggleMenu()">
              <IconifyIcon icon="mdi-light:account" class="icon-menu" width="24" height="24" />
              <span>Perfil</span>
            </router-link>

            <router-link to="/register" class="menu-item" @click="isMobile && toggleMenu()">
              <IconifyIcon icon="mdi-light:plus-box" class="icon-menu" width="24" height="24" />
              <span>Registro de Usuario</span>
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
              <router-view />
            </div>
          </main>

          <!-- Botón flotante para menú móvil -->
          <div v-if="isLoggedIn && !menuVisible" class="floating-menu-btn d-md-none" @click="toggleMenu">
            <v-icon color="white" size="32">mdi-menu</v-icon>
          </div>

          <!-- Overlay para cuando el menú está abierto en móvil -->
          <div v-if="menuVisible" class="menu-overlay d-md-none" @click="toggleMenu"></div>
        </div>
      </div>
    </div>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      menuVisible: false,
      pageTitles: {
        '/home': 'INICIO',
        '/registro-asesorias': 'REGISTRO DE ASESORÍAS',
        '/historial-asesorias': 'HISTORIAL DE ASESORÍAS',
        '/estadisticas': 'ESTADÍSTICAS',
        '/perfil': 'PERFIL',
        '/register': 'Registro de Usuario',
      }
    };
  },
  computed: {
    isLoggedIn() {
      return this.$store.getters.isLoggedIn;
    },
    pageTitle() {
      return this.pageTitles[this.$route.path];
    },
    isMobile() {
      return window.innerWidth <= 768;
    }
  },
  methods: {
    toggleMenu() {
      this.menuVisible = !this.menuVisible;
      // Prevenir el desplazamiento cuando el menú está abierto
      document.body.style.overflow = this.menuVisible ? 'hidden' : 'auto';
    },
    logout() {
      this.$store.dispatch('logout')
        .then(() => {
          this.$router.push('/login');
        })
        .catch(error => {
          console.error('Error durante el cierre de sesión:', error);
        });
    }
  },
  watch: {
    $route(to) {
      // Para actualizar el título según la ruta actual
      document.title = this.pageTitles[to.path] || 'Sistema UNSIS';
      // Cerrar el menú cuando se cambia de ruta en móvil
      if (this.isMobile && this.menuVisible) {
        this.toggleMenu();
      }
    }
  },
  mounted() {
    // Detectar cambios en el tamaño de la ventana
    window.addEventListener('resize', () => {
      if (window.innerWidth > 768 && this.menuVisible) {
        this.menuVisible = false;
        document.body.style.overflow = 'auto';
      }
    });
  },
  beforeUnmount() {
    // Limpieza del evento
    window.removeEventListener('resize');
  }
};
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

/* Sidebar styles */
.sidebar {
  width: 234px;
  min-height: 100vh;
  background-color: white;
  border-right: 1px solid #e0e0e0;
  transition: all 0.3s ease;
  z-index: 1001;
}

.sidebar-header {
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid #e0e0e0;
  position: relative;
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
  position: relative;
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
  padding: 0px;
  height: 100%;
  overflow: hidden;
  border: none;
}

/* Estilos para iconos */
.icon-menu {
  vertical-align: middle;
  margin-right: 10px;
  width: 24px;
  height: 24px;
  display: inline-block;
}

.menu-item span {
  vertical-align: middle;
}

/* Botón flotante y menú móvil */
.floating-menu-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background-color: #0e734f;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.3);
  z-index: 1000;
  cursor: pointer;
  transition: all 0.3s ease;
}

.floating-menu-btn:hover {
  background-color: #075a3b;
  transform: scale(1.05);
}

.close-menu {
  position: absolute;
  top: 15px;
  right: 15px;
  color: #333;
  cursor: pointer;
  background-color: #f0f0f0;
  border-radius: 50%;
  padding: 8px;
}

.menu-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
}

/* Media queries */
@media (max-width: 768px) {
  .sidebar {
    position: fixed;
    top: 0;
    left: -240px;
    z-index: 1001;
    height: 100vh;
    transition: left 0.3s ease;
  }

  .sidebar.active {
    left: 0;
  }

  .sidebar-header .logo {
    display: none;
  }

  .content-wrapper {
    margin-left: 0;
    width: 100%;
  }

  .page-title {
    font-size: 30px;
  }
}

@media (min-width: 769px) {
  .close-menu {
    display: none;
  }
}
</style>