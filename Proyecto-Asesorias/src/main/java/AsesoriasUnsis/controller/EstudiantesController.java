package AsesoriasUnsis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import AsesoriasUnsis.model.Estudiantes;
import AsesoriasUnsis.service.EstudiantesService;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/estudiantes")
public class EstudiantesController {

    @Autowired
    EstudiantesService estudiantesService;


    @GetMapping("/{matricula}")
    public ResponseEntity<?> obtenerEstudiante(@PathVariable String matricula) {
        try {
            Estudiantes estudiante = estudiantesService.obtenerEstudiante(matricula);
            return ResponseEntity.ok(estudiante);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estudiante no encontrado con matrícula: " + matricula);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }
}
