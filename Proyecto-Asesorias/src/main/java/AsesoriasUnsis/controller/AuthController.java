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

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication auth = authService.autenticarAlumno(
                    loginRequest.getNombre(), 
                    loginRequest.getContrasenia()
            );
            
            UserDetails userDetails = alumnoDetailsService.loadUserByUsername(loginRequest.getNombre());
            final String token = jwtTokenUtil.generateToken(userDetails);
            
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registerUser(@RequestBody RegistroRequest registroRequest) {
        try {
            Usuarios alumno = authService.registrarAlumno(
                    registroRequest.getNombre(), 
                    registroRequest.getContrasenia()
            );
            return ResponseEntity.ok("Alumno registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String nombre;
        private String contrasenia;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegistroRequest {
        private String nombre;
        private String contrasenia;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JwtResponse {
        private String token;
    }
}