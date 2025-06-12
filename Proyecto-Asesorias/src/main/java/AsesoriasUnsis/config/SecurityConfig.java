package AsesoriasUnsis.config;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas - sin autenticación
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/profesores/**").permitAll() // Temporal para debugging
                        .requestMatchers("/estudiantes/**").permitAll() // Permitir acceso a endpoints de estudiantes
                        .requestMatchers("/carreras/**").permitAll() // Permitir acceso a endpoints de carreras
                        .requestMatchers("/usuarios/**").permitAll() // Permitir acceso a endpoints de usuarios
                        .requestMatchers("/asesorias/**").permitAll() // Permitir acceso a endpoints de asesorías
                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated());

        return http.build();
    }
}