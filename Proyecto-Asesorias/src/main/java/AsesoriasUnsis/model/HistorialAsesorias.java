package AsesoriasUnsis.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Entity
@Table(name = "historial_asesorias")
@Immutable //Indica que es solo de lectura
public class HistorialAsesorias {

    @Id
    @Column(name ="idest")
    private String idest;

    @Column(name = "nombreest")
    private String nombreest;

    @Column(name = "carreraest")
    private String carreraest;

    @Column(name = "semestreest")
    private Integer semestreest;

    @Column(name = "matest")
    private String matest;

    @Column(name = "fechaest")
    private Date fechaest;

    @Column(name = "obsest")
    private String obsest;


    public void setIdest(String idest) {
        this.idest = idest;
    }
    public String getIdest() {
        return idest;
    }

    public void setNombreest(String nombreest) {
        this.nombreest = nombreest;
    }
    public String getNombreest() {
        return nombreest;
    }

    public void setCarreraest(String carreraest) {
        this.carreraest = carreraest;
    }
    public String getCarreraest() {
        return carreraest;
    }

    public void setSemestreest(Integer semestreest) {
        this.semestreest = semestreest;
    }
    public Integer getSemestreest() {
        return semestreest;
    }

    public void setMatest(String matest) {
        this.matest = matest;
    }
    public String getMatest() {
        return matest;
    }

    public void setFechaest(Date fechaest) {
        this.fechaest = fechaest;
    }
    public Date getFechaest() {
        return fechaest;
    }

    public void setObsest(String obsest) {
        this.obsest = obsest;
    }
    public String getObsest() {
        return obsest;
    }


}
