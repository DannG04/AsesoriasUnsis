package AsesoriasUnsis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import AsesoriasUnsis.model.Profesor;
import AsesoriasUnsis.model.PerfilProfesorDTO;
import AsesoriasUnsis.repository.ProfesorRepository;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    public Profesor obtenerProfesor() {
        // Obtiene el ID del profesor desde el contexto de seguridad
        String idProfesor = UsuariosDetailsService.idProfesor;

        if (idProfesor == null || idProfesor.isEmpty()) {
            throw new RuntimeException("No se ha iniciado sesión o no se ha encontrado el ID del profesor");
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

    /* Obtener las estadisticas del profesor */
    public List<Object[]> obtenerEstadisticasPorProfesor(String idProfesor) {
        try {
            return profesorRepository.obtenerEstadisticas(idProfesor);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las estadísticas del profesor: " + e.getMessage());
        }
    }

    @Transactional
    public void actualizarDescripcion(String idProfesor, String descripcion) {
        try {
            profesorRepository.actualizarDescripcion(idProfesor, descripcion);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la descripción del profesor: " + e.getMessage());
        }
    }

    /**
     * Obtiene los datos del perfil del profesor.
     * 
     * @return Un objeto PerfilProfesorDTO con los datos del perfil del profesor.
     */
    /**
     * Obtiene los datos del perfil del profesor de diferentes entidades usando la
     * función de BD.
     * 
     * @return Lista de DTOs con los datos del perfil del profesor.
     */
    public List<PerfilProfesorDTO> obtenerDatosPerfilProfesor() {
        // Obtiene el ID del profesor desde el contexto de seguridad
        String idProfesor = UsuariosDetailsService.idProfesor;

        if (idProfesor == null || idProfesor.isEmpty()) {
            throw new RuntimeException("No se ha iniciado sesión o no se ha encontrado el ID del profesor");
        }
        try {
            List<Object[]> resultados = profesorRepository.obtenerDatosProfesor(idProfesor);
            List<PerfilProfesorDTO> datosDTO = new ArrayList<>();

            for (Object[] fila : resultados) {
                PerfilProfesorDTO dto = new PerfilProfesorDTO(
                        fila[0] != null ? fila[0].toString() : "",
                        fila[1] != null ? fila[1].toString() : "",
                        fila[2] != null ? fila[2].toString() : "",
                        fila[3] != null ? (Integer) fila[3] : 0,
                        fila[4] != null ? fila[4].toString() : "");
                datosDTO.add(dto);
            }

            return datosDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener datos del perfil del profesor: " + e.getMessage(), e);
        }
    }
}
