package AsesoriasUnsis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "historial_asesorias")
@Immutable //Indica que es solo de lectura
public class HistorialAsesorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    private LocalDate fechaest;

    @Column(name = "obsest")
    private String obsest;
}
