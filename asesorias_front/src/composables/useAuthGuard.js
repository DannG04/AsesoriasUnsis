import { onMounted, onUnmounted } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { useToast } from "vue-toastification";

export function useAuthGuard() {
  const store = useStore();
  const router = useRouter();
  const toast = useToast();
  
  let connectionCheckInterval = null;
    // Función para verificar la conexión con el backend
  const checkBackendConnection = async () => {
    try {
      // Hacer una petición simple al backend para verificar conectividad
      const controller = new AbortController();
      const timeoutId = setTimeout(() => controller.abort(), 5000);
      
      const response = await fetch('http://localhost:8080/api/health', {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
          'Content-Type': 'application/json'
        },
        signal: controller.signal
      });
      
      clearTimeout(timeoutId);
      
      if (!response.ok) {
        throw new Error(`Backend responded with status: ${response.status}`);
      }
    } catch (error) {
      console.warn('Backend connection lost:', error.message);
      
      // Solo manejar como pérdida de conexión si el usuario está logueado
      if (store.getters.isLoggedIn) {
        handleConnectionLost();
      }
    }
  };
    // Función para manejar la pérdida de conexión
  const handleConnectionLost = () => {
    // Mostrar toast de conexión perdida
    toast.error('Se perdió la conexión con el servidor. Redirigiendo al login...', {
      timeout: 5000,
      position: 'top-center'
    });
    
    // Limpiar datos de autenticación
    store.dispatch('forceLogout');
    
    // Redirigir al login si no estamos ya ahí
    setTimeout(() => {
      if (router.currentRoute.value.name !== 'Login') {
        router.push('/login');
      }
    }, 1000); // Delay para que se vea el toast
  };
  
  // Detectar cuando la ventana pierde el foco (útil para detectar reinicios)
  const handleVisibilityChange = () => {
    if (document.visibilityState === 'visible' && store.getters.isLoggedIn) {
      // Verificar conexión cuando la ventana vuelve a tener foco
      checkBackendConnection();
    }
  };
  
  // Detectar errores de red globales
  const handleOnlineStatus = () => {
    if (navigator.onLine && store.getters.isLoggedIn) {
      // Verificar conexión cuando volvemos a estar online
      setTimeout(checkBackendConnection, 1000);
    }
  };
  
  onMounted(() => {
    // Verificar conexión cada 30 segundos si el usuario está logueado
    connectionCheckInterval = setInterval(() => {
      if (store.getters.isLoggedIn) {
        checkBackendConnection();
      }
    }, 30000);
    
    // Escuchar cambios de visibilidad
    document.addEventListener('visibilitychange', handleVisibilityChange);
    
    // Escuchar cambios de estado online/offline
    window.addEventListener('online', handleOnlineStatus);
  });
  
  onUnmounted(() => {
    if (connectionCheckInterval) {
      clearInterval(connectionCheckInterval);
    }
    
    document.removeEventListener('visibilitychange', handleVisibilityChange);
    window.removeEventListener('online', handleOnlineStatus);
  });
  
  return {
    checkBackendConnection,
    handleConnectionLost
  };
}
