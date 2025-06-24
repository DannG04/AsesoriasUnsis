package AsesoriasUnsis.service;

import AsesoriasUnsis.model.HistorialAsesorias;
import AsesoriasUnsis.model.AsesoriasPdfDTO;
import AsesoriasUnsis.repository.HistorialAsesoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistorialAsesoriasService {
    @Autowired
    private HistorialAsesoriasRepository repository;

    public List<HistorialAsesorias> buscarAsesorias(String idProf, String cicloValido) {
        // Llamar al nuevo método que devuelve datos en crudo
        List<Object[]> rawResults = repository.findAllAsesoriasRaw(idProf, cicloValido);

        // Mapear manualmente los resultados a la entidad HistorialAsesorias
        List<HistorialAsesorias> resultados = new ArrayList<>();

        for (Object[] row : rawResults) {
            HistorialAsesorias asesoria = new HistorialAsesorias();
            // El ID es generado automáticamente, no lo seteamos
            asesoria.setIdest((String) row[1]);
            asesoria.setNombreest((String) row[2]);
            asesoria.setCarreraest((String) row[3]);
            asesoria.setSemestreest((Integer) row[4]);
            asesoria.setMatest((String) row[5]);

            // Manejar la fecha que puede venir como Date o LocalDate
            Object fechaObj = row[6];
            if (fechaObj instanceof Date) {
                asesoria.setFechaest(((Date) fechaObj).toLocalDate());
            } else if (fechaObj instanceof LocalDate) {
                asesoria.setFechaest((LocalDate) fechaObj);
            } else if (fechaObj != null) {
                // Intentar parsear como string si es necesario
                try {
                    asesoria.setFechaest(LocalDate.parse(fechaObj.toString()));
                } catch (Exception e) {
                    System.err.println("Error al parsear fecha: " + fechaObj);
                }
            }

            asesoria.setObsest((String) row[7]);

            // Agregar un ID único para cada registro
            asesoria.setId(Long.valueOf(resultados.size() + 1));

            resultados.add(asesoria);
        }

        return resultados;
    }

    public List<AsesoriasPdfDTO> getAsesoriasPdf(String idProf, String periodo) {
        List<Object[]> rawResults = repository.findAsesoriasPdf(idProf, periodo);
        List<AsesoriasPdfDTO> resultados = new ArrayList<>();

        for (Object[] row : rawResults) {
            AsesoriasPdfDTO asesoria = new AsesoriasPdfDTO();
            
            // Mapear los datos según la estructura de la función asesorias_pdf
            // Basándome en la imagen: fechaases, nombrealumn, grupoalumn, ensustitcion, nombremat, horastart, horaend, obs
            
            // row[0] = fechaases (date)
            if (row[0] instanceof Date) {
                asesoria.setFechaAses(((Date) row[0]).toLocalDate());
            } else if (row[0] instanceof LocalDate) {
                asesoria.setFechaAses((LocalDate) row[0]);
            }
            
            // row[1] = nombrealumn (character varying)
            asesoria.setNombreAlumn((String) row[1]);
            
            // row[2] = grupoalumn (character varying)
            asesoria.setGrupoAlumn((String) row[2]);
            
            // row[3] = ensustitcion (character varying)
            asesoria.setEnSustitcion((String) row[3]);
            
            // row[4] = nombremat (character varying)
            asesoria.setNombreMat((String) row[4]);
            
            // row[5] = horastart (time without time zone)
            if (row[5] instanceof Time) {
                asesoria.setHoraStart(((Time) row[5]).toLocalTime());
            } else if (row[5] instanceof LocalTime) {
                asesoria.setHoraStart((LocalTime) row[5]);
            }
            
            // row[6] = horaend (time without time zone)
            if (row[6] instanceof Time) {
                asesoria.setHoraEnd(((Time) row[6]).toLocalTime());
            } else if (row[6] instanceof LocalTime) {
                asesoria.setHoraEnd((LocalTime) row[6]);
            }
            
            // row[7] = obs (character varying)
            asesoria.setObs((String) row[7]);
            
            resultados.add(asesoria);
        }

        return resultados;
    }

    public String obtenerCicloActual() {
        List<Object[]> rawResults = repository.obtenerCiclo();
        if (!rawResults.isEmpty() && rawResults.get(0) != null) {
            return (String) rawResults.get(0)[0]; // Devolver el primer resultado (obtener_ciclo)
        }
        return "2024-2025B"; // Valor por defecto en caso de error
    }
}
