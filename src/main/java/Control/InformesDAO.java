/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Database.ConexionOracle;
import Modelo.ReportesEstadisticaSala;
import Modelo.Sala;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InformesDAO {
    private Connection con  ;

    public int obtenerCantidadTotalReservas() {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM RESERVA_POR_HORA";

        try (Connection con = ConexionOracle.getConnection();//copiar en todas las partes iguales
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar reservas: " + e.getMessage());
        }

        return total;
    }

    public List<ReportesEstadisticaSala> obtenerReservasPorSala() {
        List<ReportesEstadisticaSala> lista = new ArrayList<>();
        String sql = "SELECT s.nombre, COUNT(r.id) AS cantidad " +
                     "FROM SALA s LEFT JOIN RESERVA_POR_HORA r ON s.id = r.id_sala " +
                     "GROUP BY s.nombre";

        try (Connection con = ConexionOracle.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new ReportesEstadisticaSala(
                    rs.getString("nombre"),
                    rs.getInt("cantidad")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reservas por sala: " + e.getMessage());
        }

        return lista;
    }
    ///
    public List<Sala> obtenerEstadoSalas() {
        List<Sala> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM SALA";

        try (Connection con = ConexionOracle.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sala sala = new Sala();
                sala.setId(String.valueOf(rs.getInt("id")));
                sala.setNombre(rs.getString("nombre"));
                lista.add(sala);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener estados de salas: " + e.getMessage());
        }

        return lista;
    }
    ///
}

