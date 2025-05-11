package Control;

import Modelo.Ubicacion;
import Database.ConexionOracle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO {
    private Connection connection;

    public UbicacionDAO() {
        try {
            connection = ConexionOracle.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ubicacion> obtenerTodasUbicaciones() {
        List<Ubicacion> ubicaciones = new ArrayList<>();
        String sql = "SELECT * FROM ubicacion";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ubicacion ubicacion = new Ubicacion(
                    rs.getString("id"),
                    rs.getString("nombre_bloque"),
                    rs.getInt("piso")
                );
                ubicaciones.add(ubicacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ubicaciones;
    }
    
    
}
