<template>
  <div v-if="showSessionInfo" class="session-info">
    <div class="session-timer">
      <small class="text-muted">
        <i class="mdi mdi-clock-outline"></i>
        Sesión: {{ formattedTimeRemaining }}
      </small>
    </div>
  </div>
</template>

<script>
import { useSessionManager } from '@/composables/useSessionManager'

export default {
  name: 'SessionIndicator',
  props: {
    showSessionInfo: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      timeRemaining: 0,
      updateInterval: null,
      sessionManager: null
    }
  },
  computed: {
    formattedTimeRemaining() {
      const minutes = Math.floor(this.timeRemaining / 60000)
      const seconds = Math.floor((this.timeRemaining % 60000) / 1000)
      return `${minutes}:${seconds.toString().padStart(2, '0')}`
    }
  },
  mounted() {
    // Inicializar sessionManager con store y router del componente
    this.sessionManager = useSessionManager(this.$store, this.$router)
    this.startTimer()
  },
  beforeUnmount() {
    if (this.updateInterval) {
      clearInterval(this.updateInterval)
    }
  },
  methods: {
    startTimer() {
      this.updateTimeRemaining()
      this.updateInterval = setInterval(() => {
        this.updateTimeRemaining()
      }, 1000)
    },
    updateTimeRemaining() {
      try {
        const loginTime = localStorage.getItem('loginTime')
        if (loginTime) {
          const currentTime = Date.now()
          const elapsedTime = currentTime - parseInt(loginTime)
          const sessionTimeout = 15 * 60 * 1000 // 15 minutos (debe coincidir con useSessionManager)
          this.timeRemaining = Math.max(0, sessionTimeout - elapsedTime)
          
          if (this.timeRemaining <= 0) {
            clearInterval(this.updateInterval)
          }
        }
      } catch (error) {
        console.warn('Error actualizando tiempo restante:', error)
      }
    }
  }
}
</script>

<style scoped>
.session-info {
  position: fixed;
  top: 10px;
  right: 10px;
  background: rgba(255, 255, 255, 0.9);
  padding: 5px 10px;
  border-radius: 15px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
  z-index: 1000;
}

.session-timer {
  font-size: 12px;
}
</style>
