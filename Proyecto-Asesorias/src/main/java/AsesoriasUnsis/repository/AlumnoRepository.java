package AsesoriasUnsis.repository;

import AsesoriasUnsis.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    
    Optional<Alumno> findByUser(String user);
    
    Boolean existsByUser(String user);
}