<template>
    <div class="container">
        <div class="welcome-card">
            <div class="header">
                <v-icon color="green" size="large">mdi-hand-wave</v-icon>
                <div class="header-text">
                    <h1>Bienvenido, Profesor</h1>
                    <p class="subtitle">Sistema de Registro de Asesorías Académicas</p>
                </div>
            </div>

            <h2 class="section-title">Acciones rápidas</h2>
            <div class="actions-container">
                <v-card class="action-card" elevation="1" @click="navigateTo('registrar')">
                    <div class="action-icon green lighten-4">
                        <v-icon color="green">mdi-file-document-edit-outline</v-icon>
                    </div>
                    <div class="action-text">
                        <h3>Registrar Asesoría</h3>
                        <p>Registre una nueva asesoría académica</p>
                    </div>
                </v-card>

                <v-card class="action-card" elevation="1" @click="navigateTo('historial')">
                    <div class="action-icon blue lighten-4">
                        <v-icon color="blue">mdi-history</v-icon>
                    </div>
                    <div class="action-text">
                        <h3>Historial de Asesorías</h3>
                        <p>Consulte asesorías previas</p>
                    </div>
                </v-card>

                <v-card class="action-card" elevation="1" @click="navigateTo('estadisticas')">
                    <div class="action-icon amber lighten-4">
                        <v-icon color="amber darken-2">mdi-chart-bar</v-icon>
                    </div>
                    <div class="action-text">
                        <h3>Estadísticas</h3>
                        <p>Visualice métricas y reportes</p>
                    </div>
                </v-card>
            </div>

            <h2 class="section-title">Resumen del periodo actual</h2>
            <div class="summary-container">
                <v-card class="summary-card" elevation="1">
                    <div class="summary-content">
                        <h3 class="summary-label">Total de asesorías</h3>
                        <p class="summary-value">28</p>
                    </div>
                    <div class="summary-icon">
                        <v-icon color="green">mdi-file-document-multiple-outline</v-icon>
                    </div>
                </v-card>

                <v-card class="summary-card" elevation="1">
                    <div class="summary-content">
                        <h3 class="summary-label">Alumnos atendidos</h3>
                        <p class="summary-value">15</p>
                    </div>
                    <div class="summary-icon">
                        <v-icon color="blue">mdi-account-group</v-icon>
                    </div>
                </v-card>

                <v-card class="summary-card" elevation="1">
                    <div class="summary-content">
                        <h3 class="summary-label">Horas impartidas</h3>
                        <p class="summary-value">42</p>
                    </div>
                    <div class="summary-icon">
                        <v-icon color="amber darken-2">mdi-clock-outline</v-icon>
                    </div>
                </v-card>

                <v-card class="summary-card" elevation="1">
                    <div class="summary-content">
                        <h3 class="summary-label">Periodo actual</h3>
                        <p class="summary-value">25-26A</p>
                    </div>
                    <div class="summary-icon">
                        <v-icon color="purple">mdi-calendar</v-icon>
                    </div>
                </v-card>
            </div>

            <h2 class="section-title">Asesorías recientes</h2>
            <v-table class="recent-table">
                <thead>
                    <tr>
                        <th>Fecha</th>
                        <th>Alumno</th>
                        <th>Materia</th>
                        <th>Duración</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="asesoria in asesoriasRecientes" :key="asesoria.id">
                        <td>{{ asesoria.fecha }}</td>
                        <td>{{ asesoria.alumno }}</td>
                        <td>{{ asesoria.materia }}</td>
                        <td>{{ asesoria.duracion }}</td>
                        <td>
                            <v-icon @click="verAsesoria(asesoria.id)" color="green">mdi-eye</v-icon>
                        </td>
                    </tr>
                </tbody>
            </v-table>

            <div class="ver-mas">
                <v-btn color="green" variant="text" @click="verHistorialCompleto">
                    VER HISTORIAL COMPLETO
                    <v-icon right>mdi-arrow-right</v-icon>
                </v-btn>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'HomeView',
    data() {
        return {
            asesoriasRecientes: [
                { id: 1, fecha: '10/05/2025', alumno: 'Daniel González Ruiz', materia: 'Programación Web', duracion: '1h 30m' },
                { id: 2, fecha: '09/05/2025', alumno: 'Elisa Sánchez Martínez', materia: 'Bases de Datos', duracion: '45m' },
                { id: 3, fecha: '08/05/2025', alumno: 'Gael García Pinacho', materia: 'Redes de Computadoras', duracion: '2h' },
                { id: 4, fecha: '07/05/2025', alumno: 'Mayra Castellanos Pacheco', materia: 'Sistemas Operativos', duracion: '1h' },
            ]
        }
    },
    computed: {
        currentUser() {
            return this.$store.getters.currentUser
        }
    },
    methods: {
        navigateTo(section) {
            // Implementar la navegación hacia las diferentes secciones
            console.log(`Navegando a ${section}`);
            if (section === 'registrar') {
                this.$router.push('/registro-asesorias');
            } else if (section === 'historial') {
                this.$router.push('/historial');
            } else if (section === 'estadisticas') {
                this.$router.push('/estadisticas');
            }
        },
        verAsesoria(id) {
            console.log(`Ver asesoría con ID: ${id}`);
            // Implementar visualización de detalles de asesoría
        },
        verHistorialCompleto() {
            this.$router.push('/historial');
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
}

.header {
    display: flex;
    align-items: center;
    margin-bottom: 1.5rem;
    border-bottom: 1px solid #eaeaea;
    padding-bottom: 1rem;
}

.header-text {
    margin-left: 1rem;
    text-align: left;
}

h1 {
    color: #2e7d32;
    margin-bottom: 0.25rem;
    font-size: 1.8rem;
}

.subtitle {
    color: #666;
    font-size: 1rem;
    margin: 0;
}

.section-title {
    color: #555;
    font-size: 1.2rem;
    margin: 2rem 0 1rem 0;
    text-align: left;
    font-weight: 500;
}

.actions-container {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
    margin-bottom: 2rem;
}

.action-card {
    display: flex;
    padding: 1rem;
    cursor: pointer;
    transition: transform 0.2s;
}

.action-card:hover {
    transform: translateY(-5px);
}

.action-icon {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 1rem;
}

.action-text {
    text-align: left;
}

.action-text h3 {
    margin: 0;
    font-size: 1rem;
    color: #333;
}

.action-text p {
    margin: 0.25rem 0 0 0;
    font-size: 0.8rem;
    color: #777;
}

.summary-container {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    margin-bottom: 2rem;
}

.summary-card {
    display: flex;
    justify-content: space-between;
    padding: 1rem;
}

.summary-content {
    text-align: left;
}

.summary-label {
    margin: 0;
    font-size: 0.8rem;
    color: #777;
    font-weight: normal;
}

.summary-value {
    font-size: 1.8rem;
    font-weight: bold;
    margin: 0.25rem 0 0 0;
}

.recent-table {
    width: 100%;
    margin-bottom: 1rem;
    border-radius: 8px;
    overflow: hidden;
}

.ver-mas {
    text-align: right;
    margin-top: 1rem;
}

@media (max-width: 960px) {
    .actions-container {
        grid-template-columns: repeat(2, 1fr);
    }

    .summary-container {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 600px) {
    .actions-container {
        grid-template-columns: 1fr;
    }

    .summary-container {
        grid-template-columns: 1fr;
    }
}
</style>