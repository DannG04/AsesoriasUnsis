package AsesoriasUnsis.repository;

import AsesoriasUnsis.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByUsuario(String usuario);

    Boolean existsByUsuario(String usuario);
}