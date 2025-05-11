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

    // Ruta pública para autenticación
    private static final String[] PUBLIC_ROUTES = { "/api/auth/**" };

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
                .cors().and().csrf().disable() // Desactiva CSRF y habilita CORS
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura sesiones sin
                                                                                            // estado
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ROUTES).permitAll() // Permite acceso a rutas públicas
                        .anyRequest().authenticated()); // Requiere autenticación para las demás rutas

        return http.build();
    }
}