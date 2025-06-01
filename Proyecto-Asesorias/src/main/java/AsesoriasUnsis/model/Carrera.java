package AsesoriasUnsis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "carrera")
public class Carrera {
    
    @Id
    @Column(name = "idcarrera", unique = true, nullable = false)
    private int idCarrera;

    @Column(name = "nombrecarrera", nullable = false)
    private String nombreCarrera;

    @Column(name = "tipocarrera", nullable = false)
    private String tipoCarrera;

    // Constructor por defecto
    public Carrera() {
    }
    // Constructor con parámetros
    public Carrera(int idCarrera, String nombreCarrera, String tipoCarrera) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
        this.tipoCarrera = tipoCarrera;
    }
    // Getters y Setters
    public int getIdCarrera() {
        return idCarrera;
    }
    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }
    public String getNombreCarrera() {
        return nombreCarrera;
    }


}
