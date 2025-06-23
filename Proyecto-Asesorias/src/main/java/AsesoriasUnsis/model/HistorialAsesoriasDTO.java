package AsesoriasUnsis.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HistorialAsesoriasDTO {
    private String idest;
    private String nombreest;
    private String carreraest;
    private Integer semestreest;
    private String matest;
    private LocalDate fechaest;
    private String obsest;

    // Constructor
    public HistorialAsesoriasDTO(String idest, String nombreest, String carreraest,
                              Integer semestreest, String matest, LocalDate fechaest, String obsest) {
        this.idest = idest;
        this.nombreest = nombreest;
        this.carreraest = carreraest;
        this.semestreest = semestreest;
        this.matest = matest;
        this.fechaest = fechaest;
        this.obsest = obsest;
    }
}
