// Reserva.java
package Modelo;

public abstract class Reserva {
    private String id;
    private Usuario usuario;
    private Sala sala;
    private String estado;

    public Reserva(String id, Usuario usuario, Sala sala, String estado) {
        this.id = id;
        this.usuario = usuario;
        this.sala = sala;
        this.estado = estado;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}