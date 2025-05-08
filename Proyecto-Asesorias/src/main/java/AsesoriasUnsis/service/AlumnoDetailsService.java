package AsesoriasUnsis.service;

import AsesoriasUnsis.model.Alumno;
import AsesoriasUnsis.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AlumnoDetailsService implements UserDetailsService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Alumno alumno = alumnoRepository.findByUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("Alumno no encontrado: " + username));
        
        return new User(alumno.getUser(), alumno.getPassword(), new ArrayList<>());
    }
}