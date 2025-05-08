package AsesoriasUnsis.service;

import AsesoriasUnsis.model.Alumno;
import AsesoriasUnsis.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Alumno registrarAlumno(String nombre, String contrasenia) {
        if (alumnoRepository.existsByUser(nombre)) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        Alumno alumno = new Alumno();
        alumno.setUser(nombre);
        alumno.setPassword(passwordEncoder.encode(contrasenia));
        
        return alumnoRepository.save(alumno);
    }

    public Authentication autenticarAlumno(String nombre, String contrasenia) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(nombre, contrasenia)
        );
    }
}
