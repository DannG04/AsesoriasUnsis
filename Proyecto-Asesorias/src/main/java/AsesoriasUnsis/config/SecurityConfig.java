package AsesoriasUnsis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter; // Rutas públicas para autenticación y testing temporal
    private static final String[] PUBLIC_ROUTES = {
            "/api/auth/**",
            "/profesores" // Agregar temporalmente para debugging
    };

    /**
     * Bean para codificar contraseñas usando BCrypt.
     * Esto asegura que las contraseñas se almacenen de forma segura en la base de
     * datos.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para gestionar la autenticación.
     * Proporciona el AuthenticationManager necesario para autenticar usuarios.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configuración de la cadena de filtros de seguridad.
     * - Desactiva CSRF (no necesario para APIs REST).
     * - Configura la política de sesiones como STATELESS (sin estado).
     * - Define rutas públicas y protege las demás.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.and())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas - sin autenticación
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/profesores").permitAll() // Temporal para debugging

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated());
        // Comentar temporalmente el filtro JWT para /profesores
        // .addFilterBefore(jwtRequestFilter,
        // UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}