package AsesoriasUnsis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name = "profesores")
public class Profesor {
// Clase que representa la entidad Profesor en la base de datos

    /**
     * Identificador único del profesor el cual esta compuesto por 4 digitos(clave primaria).
     */
    @Id
    @Column(name = "idprofesor", unique = true, nullable = false)
    private String idProfesor;
   
    @Column(name = "nomprof", nullable = false)
    private String nomProf;


    @Column(name = "apellidopprof", nullable = false)
    private String apellidoProf;
    /*
     *Apellido materno del profesor, este puede ser nulo ya que hay personas que puede que no tengan los 2 apellidos. 
     */
    @Column(name = "apellidomprof")
    private String apellidoMProf;

    @Column (name = "numtelprof", nullable = false)
    private String numTelProf;

    @Column(name = "correoprof", nullable = false)
    private String correoProf;

    @Column (name = "especialidad", nullable = false)
    private String especialidad;

    @Column (name = "cargoactual", nullable = false)
    private String cargoActual;

    @Column(name = "estatusprof", nullable = false)
    private String estado;

    @Column (name = "idcubo", nullable = false)
    private String idCubo;
}

