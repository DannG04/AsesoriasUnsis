package AsesoriasUnsis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

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
            
            System.out.println("ID del profesor no encontrado, usando ID fijo: " + idProfesor);
        } else {
            System.out.println("ID del profesor obtenido: " + idProfesor);
            
        }

        // Busca el profesor por su ID
        Optional<Profesor> profesorOptional = profesorRepository.findByIdProfesor(idProfesor);
        // Si se encuentra, devuelve el profesor; de lo contrario, lanza una excepción
        return profesorOptional.orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + idProfesor));
    }

    public Profesor guardarProfesor(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    /**
     * Obtiene las materias de un profesor usando la función de BD.
     * 
     * @param idProfesor El ID del profesor.
     * @return Lista con las materias del profesor.
     */
    public List<Object[]> obtenerMateriasPorProfesor(String idProfesor) {
        try {
            return profesorRepository.obtenerMaterias(idProfesor);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener materias del profesor: " + e.getMessage());
        }
    }
}
