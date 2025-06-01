package AsesoriasUnsis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import AsesoriasUnsis.service.CarreraService;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    CarreraService carreraService;



    @GetMapping("/{idCarrera}")
    public ResponseEntity<?> obtenerCarrera(@PathVariable int idCarrera) {
        try {
            return ResponseEntity.ok(carreraService.obtenerCarreraPorId(idCarrera));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Carrera no encontrada con ID: " + idCarrera);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
        
    }
}