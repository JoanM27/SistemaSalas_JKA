// Reserva_periodica.java
package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reserva_periodica extends Reserva {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private boolean quincenal;

    public Reserva_periodica(String id, Usuario usuario, Sala sala, String estado) {
        super(id, usuario, sala, estado);
    }

 

    // Getters y Setters
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
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