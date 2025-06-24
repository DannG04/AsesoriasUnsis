import { ref, onUnmounted } from 'vue'
import { useToast } from 'vue-toastification'

/**
 * Composable para manejar la sesión del usuario
 * - Expiración automática por tiempo
 * - Expiración por inactividad
 * - Manejo de errores de conexión
 */
export function useSessionManager(store = null, router = null) {
  const toast = useToast()

  // Configuración de tiempos (en milisegundos)
  const SESSION_TIMEOUT = 15 * 60 * 1000 // 15 minutos (tiempo total de sesión)
  const INACTIVITY_TIMEOUT = 10 * 60 * 1000 // 10 minutos de inactividad
  const WARNING_TIME = 2 * 60 * 1000 // Mostrar aviso 2 minutos antes

  // Variables reactivas
  const sessionTimer = ref(null)
  const inactivityTimer = ref(null)
  const warningTimer = ref(null)
  const isWarningShown = ref(false)

  /**
   * Verifica si el usuario está logueado
   */
  const isLoggedIn = () => {
    const token = localStorage.getItem('token')
    const user = localStorage.getItem('user')
    return !!(token && user)
  }

  /**
   * Inicializa los temporizadores de sesión
   */
  const initializeSession = () => {
    if (!isLoggedIn()) return

    const loginTime = localStorage.getItem('loginTime')
    if (!loginTime) {
      localStorage.setItem('loginTime', Date.now().toString())
    }

    // Verificar si la sesión ya expiró al inicializar
    if (checkSessionExpiry()) return
    
    // Configurar temporizadores
    startSessionTimer()
    startInactivityTimer()
    
    // Escuchar eventos de actividad del usuario
    addActivityListeners()
  }

  /**
   * Verifica si la sesión ha expirado por tiempo absoluto
   */
  const checkSessionExpiry = () => {
    if (!isLoggedIn()) return true

    const loginTime = localStorage.getItem('loginTime')
    if (loginTime) {
      const currentTime = Date.now()
      const elapsedTime = currentTime - parseInt(loginTime)
      
      if (elapsedTime >= SESSION_TIMEOUT) {
        expireSession('Su sesión ha expirado por tiempo límite.')
        return true
      }
    }
    return false
  }

  /**
   * Inicia el temporizador principal de sesión
   */
  const startSessionTimer = () => {
    clearTimeout(sessionTimer.value)
    
    if (!isLoggedIn()) return

    const loginTime = localStorage.getItem('loginTime')
    if (loginTime) {
      const elapsedTime = Date.now() - parseInt(loginTime)
      const remainingTime = SESSION_TIMEOUT - elapsedTime
      
      if (remainingTime <= 0) {
        expireSession('Su sesión ha expirado.')
        return
      }

      // Configurar aviso antes de expirar
      const warningTime = Math.max(0, remainingTime - WARNING_TIME)
      if (warningTime > 0) {
        warningTimer.value = setTimeout(() => {
          showExpirationWarning()
        }, warningTime)
      }

      // Configurar expiración
      sessionTimer.value = setTimeout(() => {
        expireSession('Su sesión ha expirado por tiempo límite.')
      }, remainingTime)
    }
  }

  /**
   * Inicia el temporizador de inactividad
   */
  const startInactivityTimer = () => {
    clearTimeout(inactivityTimer.value)
    
    if (!isLoggedIn()) return
    
    inactivityTimer.value = setTimeout(() => {
      expireSession('Su sesión ha expirado por inactividad.')
    }, INACTIVITY_TIMEOUT)
  }

  /**
   * Reinicia el temporizador de inactividad cuando hay actividad
   */
  const resetInactivityTimer = () => {
    if (isLoggedIn()) {
      startInactivityTimer()
    }
  }

  /**
   * Muestra una advertencia antes de que expire la sesión
   */
  const showExpirationWarning = () => {
    if (!isWarningShown.value && isLoggedIn()) {
      isWarningShown.value = true
      
      try {
        toast.warning('Su sesión expirará en 2 minutos. Realice alguna acción para mantenerla activa.', {
          timeout: 10000,
          position: 'top-center',
          closeOnClick: false,
          draggable: false,
          showCloseButtonOnHover: true
        })
      } catch (error) {
        console.warn('Error mostrando advertencia de sesión:', error)
      }

      // Reset del flag después de un tiempo
      setTimeout(() => {
        isWarningShown.value = false
      }, 60000) // 1 minuto
    }
  }

  /**
   * Expira la sesión y redirige al login
   */
  const expireSession = (message = 'Su sesión ha expirado.') => {
    // Limpiar todos los temporizadores
    clearAllTimers()
    
    // Mostrar mensaje
    try {
      toast.error(message, {
        timeout: 5000,
        position: 'top-center'
      })
    } catch (error) {
      console.warn('Error mostrando toast de expiración:', error)
    }

    // Limpiar datos de sesión
    localStorage.removeItem('loginTime')
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    localStorage.removeItem('profesor')

    // Limpiar store si está disponible
    if (store && store.commit) {
      try {
        store.commit('logout')
      } catch (error) {
        console.warn('Error limpiando store:', error)
      }
    }

    // Redirigir al login después de un breve delay
    setTimeout(() => {
      if (router && router.currentRoute && router.push) {
        try {
          if (router.currentRoute.value.name !== 'Login') {
            router.push('/login')
          }
        } catch (error) {
          console.warn('Error redirigiendo:', error)
          // Fallback: recargar la página
          window.location.href = '/login'
        }
      } else {
        // Fallback si no hay router disponible
        window.location.href = '/login'
      }
    }, 1000)
  }

  /**
   * Extiende la sesión (útil para actividad del usuario)
   */
  const extendSession = () => {
    if (isLoggedIn()) {
      // Actualizar tiempo de login
      localStorage.setItem('loginTime', Date.now().toString())
      
      // Reiniciar temporizadores
      startSessionTimer()
      resetInactivityTimer()
      
      // Reset del warning
      isWarningShown.value = false
    }
  }

  /**
   * Maneja errores de conexión o autenticación
   */
  const handleConnectionError = (errorType = 'connection') => {
    let message = ''
    
    switch (errorType) {
      case 'unauthorized':
        message = 'Sesión expirada. Por favor, inicie sesión nuevamente.'
        break
      case 'connection':
        message = 'Se perdió la conexión con el servidor. Verificando estado de sesión...'
        break
      case 'forbidden':
        message = 'No tiene permisos para realizar esta acción.'
        break
      default:
        message = 'Error de conexión. Verificando estado de sesión...'
    }
    
    expireSession(message)
  }

  /**
   * Agrega listeners para detectar actividad del usuario
   */
  const addActivityListeners = () => {
    if (!isLoggedIn()) return

    const events = ['mousedown', 'mousemove', 'keypress', 'scroll', 'touchstart', 'click']
    
    const activityHandler = () => {
      try {
        resetInactivityTimer()
      } catch (error) {
        console.warn('Error en activity handler:', error)
      }
    }

    events.forEach(event => {
      try {
        document.addEventListener(event, activityHandler, true)
      } catch (error) {
        console.warn(`Error agregando listener para ${event}:`, error)
      }
    })

    // Guardar referencia para limpieza posterior
    if (!window.sessionActivityCleanup) {
      window.sessionActivityCleanup = () => {
        events.forEach(event => {
          try {
            document.removeEventListener(event, activityHandler, true)
          } catch (error) {
            console.warn(`Error removiendo listener para ${event}:`, error)
          }
        })
      }
    }
  }

  /**
   * Limpia todos los temporizadores
   */
  const clearAllTimers = () => {
    clearTimeout(sessionTimer.value)
    clearTimeout(inactivityTimer.value)
    clearTimeout(warningTimer.value)
    sessionTimer.value = null
    inactivityTimer.value = null
    warningTimer.value = null
  }

  /**
   * Limpia la sesión completamente
   */
  const cleanup = () => {
    clearAllTimers()
    
    // Limpiar listeners de actividad
    if (window.sessionActivityCleanup) {
      try {
        window.sessionActivityCleanup()
        delete window.sessionActivityCleanup
      } catch (error) {
        console.warn('Error limpiando activity listeners:', error)
      }
    }
  }

  // Limpiar al desmontar el componente
  onUnmounted(() => {
    // No limpiar completamente, solo los temporizadores del componente actual
    // La sesión debe continuar funcionando en otros componentes
  })

  return {
    initializeSession,
    extendSession,
    expireSession,
    handleConnectionError,
    checkSessionExpiry,
    cleanup,
    resetInactivityTimer
  }
}
