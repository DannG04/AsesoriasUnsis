package AsesoriasUnsis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la tabla "usuarios" en la base de datos.
 * Contiene información sobre los usuarios del sistema, como credenciales y
 * roles.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuarios {

    /**
     * Identificador único del usuario (clave primaria).
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long idUsuario;

    /**
     * Nombre de usuario único.
     * Este campo es obligatorio y no puede repetirse.
     */
    @Column(nullable = false, unique = true)
    private String usuario;

    /**
     * Contraseña del usuario.
     * Este campo es obligatorio y se almacena de forma segura (encriptada).
     */
    @Column(name = "userpassword", nullable = false)
    private String userPassword;

    /**
     * Rol del usuario (e.g., "admin", "user").
     * Este campo es obligatorio.
     */
    @Column(nullable = false)
    private String rol;

    /**
     * Identificador del profesor asociado al usuario (si aplica).
     * Puede ser null si el usuario no está asociado a un profesor.
     */
    @Column(name = "idprofesor")
    private Long idProfesor;
}