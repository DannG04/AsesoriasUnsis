package AsesoriasUnsis.service;

import AsesoriasUnsis.model.Usuarios;
import AsesoriasUnsis.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UsuariosRepository alumnoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param usuario      Nombre de usuario único.
     * @param userPassword Contraseña del usuario.
     * @param rol          Rol del usuario (e.g., "admin", "profesor").
     * @param idProfesor   ID del profesor asociado.
     * @return El usuario registrado.
     * @throws IllegalArgumentException   Si algún parámetro es inválido.
     * @throws UserAlreadyExistsException Si el nombre de usuario ya existe.
     */
    public Usuarios registrarAlumno(String usuario, String userPassword, String rol, Long idProfesor) {
        if (usuario == null || usuario.isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío.");
        }
        if (userPassword == null || userPassword.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        if (rol == null || rol.isEmpty()) {
            throw new IllegalArgumentException("El rol no puede estar vacío.");
        }

        if (alumnoRepository.existsByUsuario(usuario)) {
            throw new UserAlreadyExistsException("El nombre de usuario ya existe.");
        }

        Usuarios alumno = new Usuarios();
        alumno.setUsuario(usuario);
        alumno.setUserPassword(passwordEncoder.encode(userPassword));
        alumno.setRol(rol);
        alumno.setIdProfesor(idProfesor);

        return alumnoRepository.save(alumno);
    }

    /**
     * Autentica a un usuario en el sistema.
     * 
     * @param usuario      Nombre de usuario.
     * @param userPassword Contraseña del usuario.
     * @return Objeto de autenticación si las credenciales son válidas.
     */
    public Authentication autenticarAlumno(String usuario, String userPassword) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario, userPassword));
    }

    /**
     * Excepción personalizada para manejar usuarios duplicados.
     */
    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }
}
