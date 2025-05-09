package AsesoriasUnsis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario") // Update if your DB uses this naming
    private Long idUsuario;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(name = "userpassword", nullable = false) // Match the actual column name
    private String userPassword;

    @Column(nullable = false)
    private String rol;

    @Column(name = "idprofesor", nullable = false) // Match the actual column name
    private Long idProfesor;
}