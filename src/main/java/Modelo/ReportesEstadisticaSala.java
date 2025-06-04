/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class ReportesEstadisticaSala {
 private String nombreSala;
    private int cantidadReservas;

    public ReportesEstadisticaSala(String nombreSala, int cantidadReservas) {
        this.nombreSala = nombreSala;
        this.cantidadReservas = cantidadReservas;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public int getCantidadReservas() {
        return cantidadReservas;
    }
}
