<template>
    
    <div class="container">
        <div class="stats-grid">
            <!-- Total de asesorías impartidas -->
            <div class="stat-card">
                <h3 class="stat-title">Total de asesorías impartidas</h3>
                <div class="stat-value">12</div>
                <div><IconifyIcon icon="material-symbols-light:calendar-month-outline" width="80" height="80" /></div>
            </div>

            <!-- Promedio de duración por sesión -->
            <div class="stat-card">
                <h3 class="stat-title">Promedio de duración por sesión</h3>
                <div class="stat-value">43.2 min</div>
                <div><IconifyIcon icon="material-symbols-light:nest-clock-farsight-analog-outline" width="80" height="80" /></div>
            </div>

            <!-- Total de alumnos únicos atendidos -->
            <div class="stat-card">
                <h3 class="stat-title">Total de alumnos únicos atendidos</h3>
                <div class="stat-value">8</div>
                <div><IconifyIcon icon="material-symbols-light:perm-contact-calendar-outline" width="80" height="80" /></div>
            </div>

            <!-- Materias más solicitadas -->
            <div class="stat-card">
                <h3 class="stat-title">Materias más solicitadas</h3>
                <div class="stat-value">Estadística</div>
                <div><IconifyIcon icon="material-symbols-light:book-2-outline" width="80" height="80" /></div>
            </div>

            <!-- Licenciatura de alumnos más recurrentes -->
            <div class="stat-card">
                <h3 class="stat-title">Licenciatura de alumnos más recurrentes</h3>
                <div class="stat-value">Informática</div>
                <div><IconifyIcon icon="material-symbols-light:school-outline" width="80" height="80" /></div>
            </div>

            <!-- Porcentaje de alumnos recurrentes -->
            <div class="stat-card">
                <h3 class="stat-title">Porcentaje de alumnos recurrentes</h3>
                <div class="stat-value">10%</div>
                <div><IconifyIcon icon="material-symbols-light:humidity-percentage-outline" width="80" height="80" /></div>
            </div>
        </div>
      </div>

</template>
<script>
export default {
  name: 'EstadisticasView'
}
</script>
<style scoped>
  * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        .stat-card {
            background: #DDE5FF;
            border-radius: 20px;
            padding: 25px 25px;
            text-align: center;
            transition: all 0.2s ease;
            border: none;
            position: relative;
        }

        .stat-title {
            font-size: 28px;
            font-weight: 600;
            color: #000000;
            margin-bottom: 16px;
            line-height: 1.3;
        }

        .stat-value {
            font-size: 20px;
            font-weight: 700;
            color: #000000;
            margin: 12px 0 8px 0;
        }

        /* Animaciones */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .stat-card {
            animation: fadeInUp 0.6s ease forwards;
        }

        .stat-card:nth-child(1) { animation-delay: 0.1s; }
        .stat-card:nth-child(2) { animation-delay: 0.2s; }
        .stat-card:nth-child(3) { animation-delay: 0.3s; }
        .stat-card:nth-child(4) { animation-delay: 0.4s; }
        .stat-card:nth-child(5) { animation-delay: 0.5s; }
        .stat-card:nth-child(6) { animation-delay: 0.6s; }

        /* Responsive */
        @media (max-width: 768px) {
            .dashboard-container {
                padding: 24px;
                margin: 10px;
            }
            
            .stats-grid {
                grid-template-columns: 1fr;
                gap: 20px;
            }
            
            .stat-card {
                padding: 24px 20px;
            }
            
            .stat-value {
                font-size: 78px;
            }
        }
</style>