package AsesoriasUnsis.model;

import jakarta.persistence.*;


@Entity
@Table(name = "asesorias")
public class Asesoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idasesoria", unique = true, nullable = false)
    private int idAsesoria;

    @Column(name = "observaciones", nullable = false)
    private String observaciones;

    @Column(name = "sustitucion", nullable = false)
    private String sustitucion;

    @Column(name = "idprofesor", nullable = false)
    private String idProfesor;
      @Column(name = "idestudiante", nullable = false)
    private String idEstudiante;

    @Column(name = "idciclo", nullable = false)
    private int idCiclo;

    @Column(name = "idmateria", nullable = false)
    private int idMateria;

    @Column(name = "idhorario", nullable = false)
    private int idHorario;
        // Constructores
    public Asesoria() {
    }

    public Asesoria(int idAsesoria, String observaciones, String sustitucion, String idProfesor, String idEstudiante,
            int idCiclo, int idMateria, int idHorario) {
        this.idAsesoria = idAsesoria;
        this.observaciones = observaciones;
        this.sustitucion = sustitucion;
        this.idProfesor = idProfesor;
        this.idEstudiante = idEstudiante;
        this.idCiclo = idCiclo;
        this.idMateria = idMateria;
        this.idHorario = idHorario;
    }
    // Getters y Setters
    public int getIdAsesoria() {
        return idAsesoria;
    }
    public void setIdAsesoria(int idAsesoria) {
        this.idAsesoria = idAsesoria;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public String getSustitucion() {
        return sustitucion;
    }
    public void setSustitucion(String sustitucion) {
        this.sustitucion = sustitucion;
    }
    public String getIdProfesor() {
        return idProfesor;
    }
    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }
    public String getIdEstudiante() {
        return idEstudiante;
    }    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdCiclo() {
        return idCiclo;
    }
    public void setIdCiclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }
    public int getIdMateria() {
        return idMateria;
    }
    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }
    public int getIdHorario() {
        return idHorario;
    }
    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }
}