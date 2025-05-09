package AsesoriasUnsis.service;

import AsesoriasUnsis.model.Usuarios;
import AsesoriasUnsis.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsuariosDetailsService implements UserDetailsService {

    @Autowired
    private UsuariosRepository alumnoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios alumno = alumnoRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Alumno no encontrado: " + username));

        return new User(alumno.getUsuario(), alumno.getUserPassword(), new ArrayList<>());
    }
}