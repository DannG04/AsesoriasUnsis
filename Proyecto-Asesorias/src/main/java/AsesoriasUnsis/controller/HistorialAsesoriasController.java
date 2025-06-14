package AsesoriasUnsis.controller;

import AsesoriasUnsis.model.HistorialAsesorias;
import AsesoriasUnsis.service.HistorialAsesoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/api/asesorias")
public class HistorialAsesoriasController {

    @Autowired
    private HistorialAsesoriasService service;

    @GetMapping("/historial")
    public List<HistorialAsesorias> getHistorialAsesorias(
            @RequestParam("idProf") String idProf,
            @RequestParam("ciclo") String cicloValido) {
        System.out.println("ID del profesor: " + idProf);
        System.out.println("Ciclo académico: " + cicloValido);
        return service.buscarAsesorias(idProf, cicloValido);
    }
}
