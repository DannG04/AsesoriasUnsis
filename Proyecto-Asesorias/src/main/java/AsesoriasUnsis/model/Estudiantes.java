package AsesoriasUnsis.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiantes")
public class Estudiantes {
    @Id
    @Column(name = "matricula", unique = true, nullable = false)
    private String matricula;

    @Column(name = "nomest", nullable = false)
    private String nomEst;

    @Column(name = "apellidopest", nullable = false)
    private String apellidoPEst;

    @Column(name = "apellidomest")
    private String apellidoMEst;

    @Column(name = "numtelest", nullable = false)
    private String numTelEst;

    @Column(name = "correoest", nullable = false)
    private String correoEst;

    @Column(name = "idcarrera", nullable = false)
    private int idCarrera;
    @Column(name = "idsemestre", nullable = false)
    private int idSemestre;

    // Constructores
    public Estudiantes() {
    }

    public Estudiantes(String matricula, String nomEst, String apellidoPEst, String apellidoMEst,
            String numTelEst, String correoEst, int idCarrera, int idSemestre) {
        this.matricula = matricula;
        this.nomEst = nomEst;
        this.apellidoPEst = apellidoPEst;
        this.apellidoMEst = apellidoMEst;
        this.numTelEst = numTelEst;
        this.correoEst = correoEst;
        this.idCarrera = idCarrera;
        this.idSemestre = idSemestre;
    }

    // Getters y Setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNomEst() {
        return nomEst;
    }

    public void setNomEst(String nomEst) {
        this.nomEst = nomEst;
    }

    public String getApellidoPEst() {
        return apellidoPEst;
    }

    public void setApellidoPEst(String apellidoPEst) {
        this.apellidoPEst = apellidoPEst;
    }

    public String getApellidoMEst() {
        return apellidoMEst;
    }

    public void setApellidoMEst(String apellidoMEst) {
        this.apellidoMEst = apellidoMEst;
    }

    public String getNumTelEst() {
        return numTelEst;
    }

    public void setNumTelEst(String numTelEst) {
        this.numTelEst = numTelEst;
    }

    public String getCorreoEst() {
        return correoEst;
    }

    public void setCorreoEst(String correoEst) {
        this.correoEst = correoEst;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public int getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(int idSemestre) {
        this.idSemestre = idSemestre;
    }

}
