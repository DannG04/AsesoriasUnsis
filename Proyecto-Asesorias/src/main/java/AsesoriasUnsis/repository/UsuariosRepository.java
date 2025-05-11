package AsesoriasUnsis.repository;

import AsesoriasUnsis.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Usuarios.
 * Proporciona métodos para interactuar con la base de datos.
 */
@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param usuario Nombre de usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra.
     */
    Optional<Usuarios> findByUsuario(String usuario);

    /**
     * Verifica si existe un usuario con el nombre de usuario proporcionado.
     * 
     * @param usuario Nombre de usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    Boolean existsByUsuario(String usuario);
}