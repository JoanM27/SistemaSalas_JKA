package Modelo;

public class Solicitud_aplicacion {
    private String id;
    private Usuario usuario;
    private Sala sala;
    private String aplicacion;
    private String estado;
    private String detalles;

    public Solicitud_aplicacion(String id, Usuario usuario, Sala sala, 
                                String aplicacion, String estado, 
                               String detalles) {
        this.id = id;
        this.usuario = usuario;
        this.sala = sala;
        this.aplicacion = aplicacion;
        this.estado = estado;
        this.detalles = detalles;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public String getAplicacion() { return aplicacion; }
    public void setAplicacion(String aplicacion) { this.aplicacion = aplicacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getDetalles() { return detalles; }
    public void setDetalles(String detalles) { this.detalles = detalles; }
}