package AsesoriasUnsis.repository;

import AsesoriasUnsis.model.HistorialAsesorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialAsesoriasRepository extends JpaRepository<HistorialAsesorias, Integer> {

    @Query(value = "SELECT * FROM historial_asesorias(:idprof, :cicloValido)", nativeQuery = true)
    List<HistorialAsesorias> findAllByIdProfesorAndCicloValido(@Param("idprof") String idProf, @Param("cicloValido") String cicloValido);
}
