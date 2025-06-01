package AsesoriasUnsis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import AsesoriasUnsis.model.Profesor;
import AsesoriasUnsis.repository.ProfesorRepository;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    public Profesor obtenerProfesor() {
        // Obtiene el ID del profesor desde el contexto de seguridad
        String idProfesor = UsuariosDetailsService.idProfesor;

        // Para debugging: usar un ID fijo si no hay autenticación
        if (idProfesor == null || idProfesor.isEmpty()) {
            System.out.println("No hay ID de profesor en el contexto, usando ID de prueba");
            // Obtener el primer profesor disponible para testing
            return profesorRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No hay profesores en la base de datos"));
        }

        // Busca el profesor por su ID
        Optional<Profesor> profesorOptional = profesorRepository.findByIdProfesor(idProfesor);
        // Si se encuentra, devuelve el profesor; de lo contrario, lanza una excepción
        return profesorOptional.orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + idProfesor));
    }

    public Profesor guardarProfesor(Profesor profesor) {
        return profesorRepository.save(profesor);
    }
}
