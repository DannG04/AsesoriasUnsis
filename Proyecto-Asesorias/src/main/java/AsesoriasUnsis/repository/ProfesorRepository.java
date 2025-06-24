package AsesoriasUnsis.repository;

import AsesoriasUnsis.model.Profesor;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, String> {

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
        * @return Un Optional que contiene el profesor con sus materias si se
        *         encuentra.
        */
       @Query(value = "SELECT * FROM materias_profesor(:idProfesor)", nativeQuery = true)
       List<Object[]> obtenerMaterias(@Param("idProfesor") String idProfesor);

       /* Obtener las estadisticas del profesor por su ID */
       @Query(value = "SELECT * FROM estadisticas(:idProfesor)", nativeQuery = true)
       List<Object[]> obtenerEstadisticas(@Param("idProfesor") String idProfesor);

       /**
        * Obtiene los datos del profesor para el perfil del usuario.
        * 
        * @param idProfesor El ID del profesor cuyas datos se buscan.
        * @return Lista con los datos del perfil del profesor de diferentes entidades.
        */
       @Query(value = "SELECT * FROM datos_perfil(:idProfesor)", nativeQuery = true)
       List<Object[]> obtenerDatosProfesor(@Param("idProfesor") String idProfesor);

       @Query(value = "SELECT descripcion_perfil(" +
                     ":idProfesor, " +
                     ":descripcion)", nativeQuery = true)
       void actualizarDescripcion(@Param("idProfesor") String idProfesor, @Param("descripcion") String descripcion);

}
