package AsesoriasUnsis.controller;

import AsesoriasUnsis.config.JwtTokenUtil;
import AsesoriasUnsis.model.Usuarios;
import AsesoriasUnsis.service.UsuariosDetailsService;
import AsesoriasUnsis.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsuariosDetailsService alumnoDetailsService;

    /**
     * Endpoint para autenticar a un usuario.
     * 
     * @param loginRequest Objeto que contiene el nombre de usuario y contraseña.
     * @return Respuesta con el token JWT si la autenticación es exitosa.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication auth = authService.autenticarAlumno(
                    loginRequest.getNombre(),
                    loginRequest.getContrasenia());

            UserDetails userDetails = alumnoDetailsService.loadUserByUsername(loginRequest.getNombre());
            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     * 
     * @param registroRequest Objeto que contiene los datos del usuario a registrar.
     * @return Respuesta indicando el éxito o error del registro.
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registerUser(@RequestBody RegistroRequest registroRequest) {
        try {
            Usuarios alumno = authService.registrarAlumno(
                    registroRequest.getNombre(),
                    registroRequest.getContrasenia(),
                    registroRequest.getRol(),
                    registroRequest.getIdProfesor());
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (RuntimeException e) {
            // Manejo específico de excepciones de negocio
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Manejo genérico de excepciones
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }

    /**
     * Clase interna para representar la solicitud de inicio de sesión.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String nombre;
        private String contrasenia;
    }

    /**
     * Clase interna para representar la solicitud de registro de usuario.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegistroRequest {
        private String nombre;
        private String contrasenia;
        private String rol;
        private Long idProfesor;
    }

    /**
     * Clase interna para encapsular la respuesta con el token JWT.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JwtResponse {
        private String token;
    }
}