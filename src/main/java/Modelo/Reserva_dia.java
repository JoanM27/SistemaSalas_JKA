// Reserva_dia.java
package Modelo;



public class Reserva_dia extends Reserva {
    private String fecha;
    private String horaInicio;
    private String horaFin;

    public Reserva_dia(String id, Usuario usuario, Sala sala, String estado, 
                      String fecha, String horaInicio, String horaFin) {
        super(id, usuario, sala, estado);
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y Setters
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
    
    
    
}