/**
 * Plugin global para manejar la sesión de usuario
 * Se encarga de inicializar y mantener el control de sesión en toda la aplicación
 */

import { useSessionManager } from '@/composables/useSessionManager'

export default {
  install(app) {
    // Obtener referencias de store y router
    const store = app.config.globalProperties.$store
    const router = app.config.globalProperties.$router
    
    // Crear una instancia global del gestor de sesión
    const sessionManager = useSessionManager(store, router)
    
    // Hacer disponible globalmente
    app.config.globalProperties.$session = sessionManager
    app.provide('sessionManager', sessionManager)
    
    // Función para inicializar sesión de forma segura
    const initSessionSafely = () => {
      try {
        const token = localStorage.getItem('token')
        if (token) {
          // Verificar si la sesión no ha expirado antes de inicializar
          if (!sessionManager.checkSessionExpiry()) {
            sessionManager.initializeSession()
          }
        }
      } catch (error) {
        console.warn('Error inicializando sesión:', error)
      }
    }

    // Inicializar la sesión después de que la app esté montada
    app.mixin({
      mounted() {
        // Solo inicializar una vez en el componente raíz
        if (this.$root === this) {
          initSessionSafely()
        }
      }
    })
    
    // Manejar cuando la pestaña/ventana se cierra o se recarga
    window.addEventListener('beforeunload', () => {
      // No limpiar la sesión en beforeunload, 
      // permitir que persista en localStorage para otras pestañas
    })
    
    // Manejar cuando la pestaña se vuelve visible (usuario regresa)
    document.addEventListener('visibilitychange', () => {
      try {
        const token = localStorage.getItem('token')
        if (!document.hidden && token) {
          // Verificar si la sesión expiró mientras la pestaña estaba oculta
          sessionManager.checkSessionExpiry()
        }
      } catch (error) {
        console.warn('Error en visibilitychange:', error)
      }
    })
    
    // Manejar eventos de storage para sincronizar entre pestañas
    window.addEventListener('storage', (e) => {
      try {
        if (e.key === 'token' && !e.newValue) {
          // Si el token fue eliminado en otra pestaña, cerrar sesión aquí también
          sessionManager.expireSession('Sesión cerrada en otra pestaña.')
        } else if (e.key === 'loginTime' && e.newValue) {
          // Si se inició sesión en otra pestaña, sincronizar
          const token = localStorage.getItem('token')
          if (token) {
            sessionManager.initializeSession()
          }
        }
      } catch (error) {
        console.warn('Error en storage event:', error)
      }
    })
  }
}
