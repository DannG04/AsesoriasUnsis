<template>
    <div class="container">
        <div class="welcome-card">
            <h1>Componentes Vuetify</h1>
            <p v-if="currentUser">Hola, {{ currentUser.nombre }}!</p>
            <p>Has iniciado sesión correctamente.</p>
            <v-number-input control-variant="hidden"></v-number-input>

            <v-date-input label="Select a date" prepend-icon="" prepend-inner-icon="$calendar"
                variant="solo"></v-date-input>
            <v-col cols="11" sm="5">
                <v-text-field v-model="time" label="Picker in menu" prepend-icon="mdi-clock-time-four-outline" readonly>
                    <v-menu v-model="menu2" :close-on-content-click="false" activator="parent"
                        transition="scale-transition">
                        <v-time-picker v-if="menu2" v-model="time" full-width></v-time-picker>
                    </v-menu>
                </v-text-field>
            </v-col>
            <v-form disabled>
                <v-text-field v-model="firstName" label="First name"></v-text-field>
                <v-text-field v-model="lastName" label="Last name"></v-text-field>
            </v-form>
            <v-radio-group v-model="inline" inline>
                <v-radio label="Option 1" value="radio-1"></v-radio>
                <v-radio label="Option 2" value="radio-2"></v-radio>
            </v-radio-group>
            <v-combobox label="Combobox"
                :items="['California', 'Colorado', 'Florida', 'Georgia', 'Texas', 'Wyoming']"></v-combobox>
        </div>
    </div>
</template>

<script>
export default {
    name: 'HomeU',
    components: {

    },
    data() {
        return {
            selectedTime: null,
            time: '',
            menu2: false,
            inline: null,
            firstName: '',
            lastName: ''
        }
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