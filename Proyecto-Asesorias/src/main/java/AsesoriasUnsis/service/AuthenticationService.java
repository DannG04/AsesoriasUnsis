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

    public Usuarios registrarAlumno(String usuario, String userPassword, String rol, Long idProfesor) {
        if (alumnoRepository.existsByUsuario(usuario)) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        Usuarios alumno = new Usuarios();
        alumno.setUsuario(usuario);
        alumno.setUserPassword(passwordEncoder.encode(userPassword));
        alumno.setRol(rol);
        alumno.setIdProfesor(idProfesor);

        return alumnoRepository.save(alumno);
    }

    public Authentication autenticarAlumno(String usuario, String userPassword) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario, userPassword));
    }
}
