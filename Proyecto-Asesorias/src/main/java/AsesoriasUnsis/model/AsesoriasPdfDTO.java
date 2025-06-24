package AsesoriasUnsis.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AsesoriasPdfDTO {
    private LocalDate fechaAses;      // fechaases
    private String nombreAlumn;       // nombrealumn  
    private String grupoAlumn;        // grupoalumn
    private String enSustitcion;      // ensustitcion
    private String nombreMat;         // nombremat
    private LocalTime horaStart;      // horastart
    private LocalTime horaEnd;        // horaend
    private String obs;               // obs

    // Constructor vacío
    public AsesoriasPdfDTO() {}

    // Constructor con todos los parámetros
    public AsesoriasPdfDTO(LocalDate fechaAses, String nombreAlumn, String grupoAlumn, String enSustitcion, 
                          String nombreMat, LocalTime horaStart, LocalTime horaEnd, String obs) {
        this.fechaAses = fechaAses;
        this.nombreAlumn = nombreAlumn;
        this.grupoAlumn = grupoAlumn;
        this.enSustitcion = enSustitcion;
        this.nombreMat = nombreMat;
        this.horaStart = horaStart;
        this.horaEnd = horaEnd;
        this.obs = obs;
    }
}
