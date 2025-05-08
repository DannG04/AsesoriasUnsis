<template>
    <div class="container">
        <div class="welcome-card">
            <h1>Bienvenido al Sistema</h1>
            <p v-if="currentUser">Hola, {{ currentUser.nombre }}!</p>
            <p>Has iniciado sesión correctamente.</p>
            <v-chip variant="outlined">
                <template v-slot:prepend>
                    <Icon icon="mdi:home" />
                </template>
                Chip
            </v-chip>
        </div>
    </div>
</template>

<script>
import { Icon } from '@iconify/vue';

export default {
    name: 'HomeU',
    components: {
        Icon
    },
    computed: {
        currentUser() {
            return this.$store.getters.currentUser
        }
    }
}
</script>

<style scoped>
.container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 2rem;
}

.welcome-card {
    background-color: white;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
}

h1 {
    color: #3498db;
    margin-bottom: 1rem;
}

p {
    font-size: 1.1rem;
    color: #555;
}
</style>