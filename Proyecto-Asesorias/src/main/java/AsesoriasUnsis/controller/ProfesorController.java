package AsesoriasUnsis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AsesoriasUnsis.model.Profesor;
import AsesoriasUnsis.service.ProfesorService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    @Autowired
    ProfesorService profesorService;

    @GetMapping()
    public ResponseEntity<?> obtenProfesor() {
        try {
            Profesor profesor = profesorService.obtenerProfesor();
            return ResponseEntity.ok(profesor);
        } catch (Exception e) {
            System.err.println("Error al obtener profesor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
