package AsesoriasUnsis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AsesoriasUnsis.model.Profesor;
import AsesoriasUnsis.service.ProfesorService;

import java.util.List;
import java.util.Arrays;

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

    @GetMapping("/{idProfesor}/materias")
    public ResponseEntity<?> obtenerMateriasPorProfesor(@PathVariable String idProfesor) {
        try {
            List<Object[]> materias = profesorService.obtenerMateriasPorProfesor(idProfesor);
            for (Object[] fila : materias) {
                System.out.println(Arrays.toString(fila));
            }
            return ResponseEntity.ok(materias);
        } catch (Exception e) {
            System.err.println("Error al obtener materias del profesor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

}
