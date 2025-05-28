package AsesoriasUnsis.repository;
import AsesoriasUnsis.model.Profesor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, String>{

    /**
     * Busca un profesor por su ID.
     * 
     * @param idProfesor El ID del profesor a buscar.
     * @return Un Optional que contiene el profesor si se encuentra.
     */
    Optional<Profesor> findByIdProfesor(String idProfesor);

    /**
     * Verifica si existe un profesor con el ID proporcionado.
     * 
     * @param idProfesor El ID del profesor a verificar.
     * @return true si el profesor existe, false en caso contrario.
     */
    Boolean existsByIdProfesor(String idProfesor);
    
}
