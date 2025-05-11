package Control;

import Modelo.Reserva;
import Modelo.Reserva_dia;
import Modelo.Reserva_periodica;
import Database.ConexionOracle;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservarDAO {
    private Connection connection;
    /*
    public ReservarDAO() {
        try {
            connection = ConexionOracle.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//////
    public boolean crearReservaDia(Reserva_dia reserva) {
        String sql = "INSERT INTO reserva_por_hora VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, reserva.getId());
            pstmt.setString(2, reserva.getUsuario().getCedula());
            pstmt.setString(3, reserva.getSala().getId());
            pstmt.setDate(4, Date.valueOf(reserva.getFecha()));
            pstmt.setTimestamp(5, Timestamp.valueOf(reserva.getHoraInicio()));
            pstmt.setTimestamp(6, Timestamp.valueOf(reserva.getHoraFin()));
            pstmt.setString(7, reserva.getEstado());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    ////
    public boolean crearReservaPeriodica(Reserva_periodica reserva) {
        Connection conn = null;
        try {
            conn = ConexionOracle.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Insertar en RESERVA_POR_PERIODO
            String sqlPeriodica = "INSERT INTO reserva_por_periodo (id, cedula_usuario, id_sala, "
                    + "fecha_inicio, fecha_fin, dia_semana, hora_inicio, hora_fin, quincenal) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sqlPeriodica)) {
                pstmt.setString(1, reserva.getId());
                pstmt.setString(2, reserva.getUsuario().getCedula());
                pstmt.setString(3, reserva.getSala().getId());
                pstmt.setDate(4, Date.valueOf(reserva.getFechaInicio()));
                pstmt.setDate(5, Date.valueOf(reserva.getFechaFin()));
                pstmt.setString(6, reserva.getDiaSemana());
                
                // Convertir tiempos a Timestamp con fecha dummy
                LocalDate fechaDummy = LocalDate.of(1970, 1, 1);
                pstmt.setTimestamp(7, Timestamp.valueOf(fechaDummy.atTime(reserva.getHoraInicio())));
                    pstmt.setTimestamp(8, Timestamp.valueOf(fechaDummy.atTime(reserva.getHoraFin())));
                pstmt.setInt(9, reserva.isQuincenal() ? 1 : 0);
                
                pstmt.executeUpdate();
            }

            // 2. Generar todas las fechas válidas
            List<LocalDate> fechasReserva;
            fechasReserva = generarFechasReserva(
                    reserva.getFechaInicio(),
                    reserva.getFechaFin(),
                    reserva.getDiaSemana(),
                    reserva.isQuincenal()
            );

            // 3. Insertar cada reserva individual en RESERVA_POR_HORA
            String sqlHora = "INSERT INTO reserva_por_hora (id, cedula_usuario, id_sala, "
                    + "fecha, hora_inicio, hora_fin, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, 'ACTIVA')";
            
            for (LocalDate fecha : fechasReserva) {
                try (PreparedStatement pstmt = conn.prepareStatement(sqlHora)) {
                    pstmt.setString(1, generarIdUnico());
                    pstmt.setString(2, reserva.getUsuario().getCedula());
                    pstmt.setString(3, reserva.getSala().getId());
                    pstmt.setDate(4, Date.valueOf(fecha));
                    pstmt.setTimestamp(5, Timestamp.valueOf(fecha.atTime(reserva.getHoraInicio())));
                    pstmt.setTimestamp(6, Timestamp.valueOf(fecha.atTime(reserva.getHoraFin())));
                    
                    pstmt.executeUpdate();
                }
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /////////////////////////////////////////////
    private List<LocalDate> generarFechasReserva(LocalDate inicio, LocalDate fin, String diaSemana, boolean quincenal) {
        List<LocalDate> fechas = new ArrayList<>();
        DayOfWeek dia = convertirDia(diaSemana);
        int semanas = quincenal ? 2 : 1;
        
        LocalDate fechaActual = inicio.with(TemporalAdjusters.nextOrSame(dia));
        
        while (!fechaActual.isAfter(fin)) {
            fechas.add(fechaActual);
            fechaActual = fechaActual.plusWeeks(semanas);
        }
        
        return fechas;
    }

    private DayOfWeek convertirDia(String diaSemana) {
        switch (diaSemana.toUpperCase()) {
            case "LUNES": return DayOfWeek.MONDAY;
            case "MARTES": return DayOfWeek.TUESDAY;
            case "MIERCOLES": return DayOfWeek.WEDNESDAY;
            case "JUEVES": return DayOfWeek.THURSDAY;
            case "VIERNES": return DayOfWeek.FRIDAY;
            case "SABADO": return DayOfWeek.SATURDAY;
            case "DOMINGO": return DayOfWeek.SUNDAY;
            default: throw new IllegalArgumentException("Día no válido: " + diaSemana);
        }
    }

    private String generarIdUnico() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    // Método para verificar disponibilidad (karol)
    private boolean verificarDisponibilidad(String idSala, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        // Lógica de verificación contra la base de datos(karol)
        return true;
    }
*/
}