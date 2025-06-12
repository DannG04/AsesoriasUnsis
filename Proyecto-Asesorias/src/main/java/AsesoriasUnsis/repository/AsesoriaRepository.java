package AsesoriasUnsis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import AsesoriasUnsis.model.Asesoria;
import java.util.List;

@Repository
public interface AsesoriaRepository extends JpaRepository<Asesoria, Integer> {
    
    /**
     * Busca una asesoría por su ID.
     * 
     * @param idAsesoria El ID de la asesoría a buscar.
     * @return Un Optional que contiene la asesoría si se encuentra.
     */
    Optional<Asesoria> findByIdAsesoria(int idAsesoria);
    
    /**
     * Verifica si existe una asesoría con el ID proporcionado.
     * 
     * @param idAsesoria El ID de la asesoría a verificar.
     * @return true si la asesoría existe, false en caso contrario.
     */
    Boolean existsByIdAsesoria(int idAsesoria);    /**
     * Busca todas la asesorias de un periodo.
     * 
     * @param idCiclo El ID del ciclo de la asesoría a buscar.
     * @return Retorna una lista de asesorías asociadas al ciclo.
     */
    List<Asesoria> findByIdCiclo(int idCiclo);
    
    /**
     * Inserta una nueva asesoría en la base de datos.
     * @param comentario Comentario de la asesoría.
     * @param lugarAsesoria Lugar donde se realiza la asesoría.
     * @param idProfesor ID del profesor que realiza la asesoría.
     * @param matricula Matrícula del estudiante que recibe la asesoría.
     * @param materiaSeleccionada Materia para la cual se realiza la asesoría.
     * @param fecha Fecha de la asesoría.
     * @param horaInicio Hora de inicio de la asesoría.
     * @param horaFinal Hora final de la asesoría.
     */

    @Query(value = "SELECT registrar_asesoria(:comentario, :lugarAsesoria, :idProfesor, :matricula, :materiaSeleccionada, :fecha, :horaInicio, :horaFinal)", 
        nativeQuery = true)
    void registrarAsesoria(@Param ("comentario") String comentario, 
                           @Param("lugarAsesoria") String lugarAsesoria, 
                           @Param("idProfesor") String idProfesor, 
                           @Param("matricula") String matricula, 
                           @Param("materiaSeleccionada") String materiaSeleccionada, 
                           @Param("fecha") String fecha, 
                           @Param("horaInicio") String horaInicio, 
                           @Param("horaFinal") String horaFinal);

}
