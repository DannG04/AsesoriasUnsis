package AsesoriasUnsis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import AsesoriasUnsis.model.Carrera;
import AsesoriasUnsis.repository.CarreraRepository;

@Service
public class CarreraService {
    
    @Autowired
    private CarreraRepository carreraRepository;

    public Carrera obtenerCarreraPorId(int idCarrera) {
        return carreraRepository.findByIdCarrera(idCarrera)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID: " + idCarrera));
    }

    public Carrera guardarCarrera(Carrera carrera) {
        return carreraRepository.save(carrera);
    }
}
