package AsesoriasUnsis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AsesoriasUnsis.model.PerfilProfesorDTO;
import AsesoriasUnsis.model.Profesor;
import AsesoriasUnsis.model.ActualizarDescripcionDTO;
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

    @GetMapping("/{idProfesor}/estadisticas")
    public ResponseEntity<?> obtenerEstadisticasPorProfesor(@PathVariable String idProfesor) {
        try {
            List<Object[]> estadisticas = profesorService.obtenerEstadisticasPorProfesor(idProfesor);
            for (Object[] fila : estadisticas) {
                System.out.println(Arrays.toString(fila));
            }
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            System.err.println("Error al obtener las estadísticas del profesor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/datos")
    public ResponseEntity<List<PerfilProfesorDTO>> obtenerDatosProfesor() {
        try {
            List<PerfilProfesorDTO> datos = profesorService.obtenerDatosPerfilProfesor();
            System.out.println("Datos del profesor: " + datos);
            return ResponseEntity.ok(datos);
        } catch (Exception e) {
            System.err.println("Error al obtener datos del profesor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/actualizar-descripcion")
    public ResponseEntity<?> actualizarDescripcion(@RequestBody ActualizarDescripcionDTO request) {
        try {
            // Validar que los datos requeridos estén presentes
            if (request.getIdProfesor() == null || request.getIdProfesor().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El ID del profesor es requerido");
            }

            if (request.getDescripcion() == null || request.getDescripcion().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La descripción es requerida");
            }

            // Validar longitud de la descripción
            if (request.getDescripcion().length() > 400) {
                return ResponseEntity.badRequest().body("La descripción no puede exceder 400 caracteres");
            }

            // Llamar al servicio para actualizar la descripción
            profesorService.actualizarDescripcion(request.getIdProfesor(), request.getDescripcion());

            System.out.println("Descripción actualizada para profesor ID: " + request.getIdProfesor());
            return ResponseEntity.ok().body("Descripción actualizada correctamente");

        } catch (Exception e) {
            System.err.println("Error al actualizar la descripción: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la descripción: " + e.getMessage());
        }
    }

}
