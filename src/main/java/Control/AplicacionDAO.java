package Control;

import Modelo.Aplicacion;
import Database.ConexionOracle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AplicacionDAO {
    private Connection connection;

    public AplicacionDAO() {
        try {
            connection = ConexionOracle.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aplicacion> obtenerTodasAplicaciones() {
        List<Aplicacion> aplicaciones = new ArrayList<>();
        String sql = "SELECT * FROM aplicacion";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Aplicacion app = new Aplicacion(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                aplicaciones.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aplicaciones;
    }

    public static class Autenticar {

        public Autenticar() {
        }
    }
}
