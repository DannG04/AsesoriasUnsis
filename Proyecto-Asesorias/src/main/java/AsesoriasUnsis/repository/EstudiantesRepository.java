package AsesoriasUnsis.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import AsesoriasUnsis.model.Estudiantes;

@Repository
public interface EstudiantesRepository extends JpaRepository<Estudiantes, String> {
    
    /**
     * Busca un estudiante por su matrícula.
     * 
     * @param matricula La matrícula del estudiante a buscar.
     * @return Un Optional que contiene el estudiante si se encuentra.
     */
    Optional<Estudiantes> findByMatricula(String matricula);
    
    /**
     * Verifica si existe un estudiante con la matrícula proporcionada.
     * 
     * @param matricula La matrícula del estudiante a verificar.
     * @return true si el estudiante existe, false en caso contrario.
     */
    Boolean existsByMatricula(String matricula);
    
}
