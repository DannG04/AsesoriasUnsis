package AsesoriasUnsis.service;

import AsesoriasUnsis.model.HistorialAsesorias;
import AsesoriasUnsis.repository.HistorialAsesoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialAsesoriasService {
    @Autowired
    private HistorialAsesoriasRepository repository;

    public List<HistorialAsesorias> buscarAsesorias(String idProf, String cicloValido) {
        return repository.findAllByIdProfesorAndCicloValido(idProf, cicloValido);
    }
}
