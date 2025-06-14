package AsesoriasUnsis.model;

/**
 * DTO para datos del perfil del profesor que combina información de diferentes entidades
 */
public class PerfilProfesorDTO {
    private String imail;
    private String rol;
    private String edifisio;
    private Integer qbiqlo;
    private String descripsion;
    
    // Constructor vacío
    public PerfilProfesorDTO() {}
    
    // Constructor con todos los parámetros
    public PerfilProfesorDTO(String imail, String rol, String edifisio, Integer qbiqlo, String descripsion) {
        this.imail = imail;
        this.rol = rol;
        this.edifisio = edifisio;
        this.qbiqlo = qbiqlo;
        this.descripsion = descripsion;
    }
    
    // Getters y Setters
    public String getImail() {
        return imail;
    }
    
    public void setImail(String imail) {
        this.imail = imail;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public String getEdifisio() {
        return edifisio;
    }
    
    public void setEdifisio(String edifisio) {
        this.edifisio = edifisio;
    }
    
    public Integer getQbiqlo() {
        return qbiqlo;
    }
    
    public void setQbiqlo(Integer qbiqlo) {
        this.qbiqlo = qbiqlo;
    }
    
    public String getDescripsion() {
        return descripsion;
    }
    
    public void setDescripsion(String descripsion) {
        this.descripsion = descripsion;
    }
    
    @Override
    public String toString() {
        return "PerfilProfesorDTO{" +
                "imail='" + imail + '\'' +
                ", rol='" + rol + '\'' +
                ", edifisio='" + edifisio + '\'' +
                ", qbiqlo=" + qbiqlo +
                ", descripsion='" + descripsion + '\'' +
                '}';
    }
}
