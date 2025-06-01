package AsesoriasUnsis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import AsesoriasUnsis.model.Estudiantes;
import AsesoriasUnsis.repository.EstudiantesRepository;

@Service
public class EstudiantesService{

    @Autowired
    private EstudiantesRepository estudiantesRepository;

    public Estudiantes obtenerEstudiante(String matricula) {
        return estudiantesRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + matricula));
    }
    public Estudiantes guardarEstudiante(Estudiantes estudiante) {
        return estudiantesRepository.save(estudiante);
    }
    
}
