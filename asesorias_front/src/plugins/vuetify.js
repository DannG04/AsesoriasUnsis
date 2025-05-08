// src/plugins/vuetify.js
import { createVuetify } from 'vuetify'
import 'vuetify/styles'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { aliases, mdi } from 'vuetify/iconsets/mdi'
// Importar estilos
import 'vuetify/styles'
import { VDateInput } from 'vuetify/labs/VDateInput'
import { VTimePicker } from 'vuetify/labs/VTimePicker'



export default createVuetify({
    components: {
        ...components,
        VDateInput, 
        VTimePicker,
    },
    directives,
    icons: {
        defaultSet: 'mdi',
        aliases,
        sets: {
            mdi,
        },
    },
    theme: {
        defaultTheme: 'light'
    }
})