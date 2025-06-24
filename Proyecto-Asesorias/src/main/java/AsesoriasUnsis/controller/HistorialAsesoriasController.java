package AsesoriasUnsis.controller;

import AsesoriasUnsis.model.HistorialAsesorias;
import AsesoriasUnsis.model.AsesoriasPdfDTO;
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

    @GetMapping("/pdf")
    public List<AsesoriasPdfDTO> getAsesoriasPdf(
            @RequestParam("idProf") String idProf,
            @RequestParam("periodo") String periodo) {
        System.out.println("ID del profesor para PDF: " + idProf);
        System.out.println("Periodo: " + periodo);
        return service.getAsesoriasPdf(idProf, periodo);
    }

    @GetMapping("/ciclo-actual")
    public String getCicloActual() {
        System.out.println("Obteniendo ciclo actual");
        return service.obtenerCicloActual();
    }
}
