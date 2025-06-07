package AsesoriasUnsis.repository;
import AsesoriasUnsis.model.Profesor;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    
    /**
     * Busca las materias de un profesor por su ID.
     * 
     * @param idProfesor El ID del profesor cuyas materias se buscan.
     * @return Un Optional que contiene el profesor con sus materias si se encuentra.
     */
    @Query(value = "SELECT * FROM materias_profesor(:idProfesor)",
           nativeQuery = true)
    List<Object[] >obtenerMaterias(@Param("idProfesor") String idProfesor);

}
