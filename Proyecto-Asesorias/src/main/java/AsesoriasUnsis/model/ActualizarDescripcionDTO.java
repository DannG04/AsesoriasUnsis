package AsesoriasUnsis.model;

public class ActualizarDescripcionDTO {
    private String idProfesor;
    private String descripcion;

    // Constructor vacío
    public ActualizarDescripcionDTO() {
    }

    // Constructor con parámetros
    public ActualizarDescripcionDTO(String idProfesor, String descripcion) {
        this.idProfesor = idProfesor;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ActualizarDescripcionDTO{" +
                "idProfesor='" + idProfesor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
