package Control;

import Modelo.Sala;
import Modelo.Ubicacion;
import Database.ConexionOracle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {
    private Connection connection;

    public SalaDAO() {
        try {
            connection = ConexionOracle.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Sala> obtenerTodasSalas() {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT s.*, u.nombre_bloque, u.piso FROM sala s "
                   + "LEFT JOIN ubicacion u ON s.id_ubicacion = u.id";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ubicacion ubicacion = new Ubicacion(
                    rs.getString("id_ubicacion"),
                    rs.getString("nombre_bloque"),
                    rs.getInt("piso")
                );
                
                Sala sala = new Sala(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getInt("maximo_equipos"),
                    rs.getString("normas"),
                    ubicacion
                );
                
                salas.add(sala);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salas;
    }
    ////////
    public Sala obtenerSalaPorId(String idSala) {
    String sql = "SELECT s.*, u.nombre_bloque, u.piso "
               + "FROM sala s "
               + "LEFT JOIN ubicacion u ON s.id_ubicacion = u.id "
               + "WHERE s.id = ?";
    
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, idSala);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            // Construir objeto Ubicación
            Ubicacion ubicacion = new Ubicacion(
                rs.getString("id_ubicacion"),
                rs.getString("nombre_bloque"),
                rs.getInt("piso")
            );

            // Construir y retornar objeto Sala
            return new Sala(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getInt("maximo_equipos"),
                rs.getString("normas"),
                ubicacion
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
}
