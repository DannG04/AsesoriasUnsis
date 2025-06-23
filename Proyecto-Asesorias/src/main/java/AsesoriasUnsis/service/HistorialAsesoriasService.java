package AsesoriasUnsis.service;

import AsesoriasUnsis.model.HistorialAsesorias;
import AsesoriasUnsis.repository.HistorialAsesoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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
}
