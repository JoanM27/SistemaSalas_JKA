package Modelo;

public class Solicitud_admin {
    private int id;
    private Usuario usuario;
    private String estado;

    public Solicitud_admin(int id, Usuario usuario, String estado) {
        this.id = id;
        this.usuario = usuario;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}