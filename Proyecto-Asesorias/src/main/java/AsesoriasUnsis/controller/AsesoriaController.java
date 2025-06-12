package AsesoriasUnsis.controller;

import AsesoriasUnsis.service.AsesoriaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import AsesoriasUnsis.model.Asesoria;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/asesorias")
public class AsesoriaController {
    
    @Autowired
    private AsesoriaService asesoriaService;
    
    @GetMapping("/{periodoId}")
    public ResponseEntity<?> obtenerAsesoriasPorPeriodo(@PathVariable int periodoId) {
        try {
            List<Asesoria> asesorias = asesoriaService.obtenerAsesoriasPorPeriodo(periodoId);
            return ResponseEntity.ok(asesorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener asesorías: " + e.getMessage());
        }
    }

    @PostMapping("/datos")
    public ResponseEntity<?> registrarAsesoria(
            @RequestParam String comentario,
            @RequestParam String lugarAsesoria,
            @RequestParam String idProfesor,
            @RequestParam String matricula,
            @RequestParam String materiaSeleccionada,
            @RequestParam String fecha,
            @RequestParam String horaInicio,
            @RequestParam String horaFinal) {
        try {
            asesoriaService.registrarAsesoria(comentario, lugarAsesoria, idProfesor, matricula, materiaSeleccionada, fecha, horaInicio, horaFinal);
            return ResponseEntity.ok("Asesoría registrada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar asesoría: " + e.getMessage());
        }
    }

    
}
