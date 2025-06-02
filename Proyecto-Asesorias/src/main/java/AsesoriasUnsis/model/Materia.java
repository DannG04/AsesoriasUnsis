package AsesoriasUnsis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "materia")
public class Materia {

    @Id
    @Column(name = "idmateria", unique = true, nullable = false)
    private int idMateria;

    @Column(name = "nombremateria", nullable = false)
    private String nombreMateria;

    @Column(name = "creditos", nullable = false)
    private int creditos;

    @Column(name = "horasaula", nullable = false)
    private int horasAula;

    @Column(name = "horasindep", nullable = false)
    private int horasIndep;

    @Column(name = "optativa", nullable = false)
    private boolean optativa;

    public Materia() {
    }

    public Materia(int idMateria, String nombreMateria, int creditos, int horasAula, int horasIndep, boolean optativa) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.creditos = creditos;
        this.horasAula = horasAula;
        this.horasIndep = horasIndep;
        this.optativa = optativa;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getHorasAula() {
        return horasAula;
    }

    public void setHorasAula(int horasAula) {
        this.horasAula = horasAula;
    }

    public int getHorasIndep() {
        return horasIndep;
    }

    public void setHorasIndep(int horasIndep) {
        this.horasIndep = horasIndep;
    }

    public boolean getOptativa() {
        return optativa;
    }

    public void setOptativa(boolean optativa) {
        this.optativa = optativa;
    }
}
