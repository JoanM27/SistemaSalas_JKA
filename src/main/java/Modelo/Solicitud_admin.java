package Modelo;

public class Solicitud_admin {
    private String id;
    private Usuario usuario;
    private String estado;


    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String get_Cedula_Usuario() { return usuario.getCedula(); }
}