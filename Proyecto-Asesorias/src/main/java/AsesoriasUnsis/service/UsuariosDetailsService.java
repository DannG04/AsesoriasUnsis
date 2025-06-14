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

/**
 * Servicio que implementa UserDetailsService para cargar detalles de los
 * usuarios.
 * Es utilizado por Spring Security para autenticar y autorizar usuarios.
 */
@Service
public class UsuariosDetailsService implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuarioRepository;
    
    public static String idProfesor;
    /**
     * Carga un usuario por su nombre de usuario.
     * 
     * @param username Nombre de usuario a buscar.
     * @return Un objeto UserDetails que contiene la información del usuario.
     * @throws UsernameNotFoundException Si el usuario no se encuentra en la base de
     *                                   datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Profesor no encontrado: " + username));
        idProfesor = usuario.getIdProfesor().toString();
        // Devuelve un objeto UserDetails con el nombre de usuario, contraseña y roles
        // vacíos
        return new User(usuario.getUsuario(), usuario.getUserPassword(), new ArrayList<>());
    }

}