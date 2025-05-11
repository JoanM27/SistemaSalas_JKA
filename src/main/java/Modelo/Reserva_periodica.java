// Reserva_periodica.java
package Modelo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reserva_periodica extends Reserva {
    private String fechaInicio;
    private String fechaFin;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private boolean quincenal;

    public Reserva_periodica(String id, Usuario usuario, Sala sala, String estado, 
                            String fechaInicio, String fechaFin, String diaSemana, 
                            String horaInicio, String horaFin, boolean quincenal) {
        super(id, usuario, sala, estado);
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.quincenal = quincenal;
    }

    // Getters y Setters
    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public LocalTime getHoraInicio() { return convertirStringAHora(horaInicio); }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return convertirStringAHora(horaFin); }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
    public boolean isQuincenal() { return quincenal; }
    public void setQuincenal(boolean quincenal) { this.quincenal = quincenal; }
    
    public LocalTime convertirStringAHora(String horaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(horaStr, formatter);
    } 
}