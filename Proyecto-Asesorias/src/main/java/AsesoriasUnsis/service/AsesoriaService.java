package AsesoriasUnsis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import AsesoriasUnsis.model.Asesoria;
import AsesoriasUnsis.repository.AsesoriaRepository;

@Service
public class AsesoriaService {

    @Autowired
    private AsesoriaRepository asesoriaRepository;

    public List<Asesoria> obtenerAsesoriasPorPeriodo(int periodoId) {
        return asesoriaRepository.findByIdCiclo(periodoId);
    }

    public Asesoria obtenerAsesoriaPorId(int idAsesoria) {
        return asesoriaRepository.findByIdAsesoria(idAsesoria)
                .orElseThrow(() -> new RuntimeException("Asesoría no encontrada con ID: " + idAsesoria));
    }

    public Asesoria guardarAsesoria(Asesoria asesoria) {
        return asesoriaRepository.save(asesoria);
    }

    @Transactional
    public void registrarAsesoria(String comentario, String lugarAsesoria, String idProfesor,
            String matricula, String materiaSeleccionada, String fecha,
            String horaInicio, String horaFinal) {
        // Validar formato de fecha y hora antes de enviar
        try {
            // Verificar que la fecha tenga formato correcto (YYYY-MM-DD)
            // Verificar que las horas tengan formato correcto (HH:MM:SS)

            asesoriaRepository.registrarAsesoria(comentario, lugarAsesoria, idProfesor,
                    matricula, materiaSeleccionada, fecha,
                    horaInicio, horaFinal);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar asesoría: " + e.getMessage());
        }
    }

}
