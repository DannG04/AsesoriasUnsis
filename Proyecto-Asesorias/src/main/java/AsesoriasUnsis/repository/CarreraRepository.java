package AsesoriasUnsis.repository;

import AsesoriasUnsis.model.Carrera;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CarreraRepository  extends JpaRepository<Carrera, Integer> {

    /**
     * Busca una carrera por su ID.
     * 
     * @param idCarrera El ID de la carrera a buscar.
     * @return Un Optional que contiene la carrera si se encuentra.
     */
    Optional<Carrera> findByIdCarrera(int idCarrera);

    /**
     * Verifica si existe una carrera con el ID proporcionado.
     * 
     * @param idCarrera El ID de la carrera a verificar.
     * @return true si la carrera existe, false en caso contrario.
     */
    Boolean existsByIdCarrera(int idCarrera);
    
    
}
