package Control;

import Modelo.Solicitud_aplicacion;
import Database.ConexionOracle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudAplicacionDAO {
    /*
    private Connection connection;

    public SolicitudAplicacionDAO() {
        try {
            connection = ConexionOracle.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Solicitud_aplicacion> obtenerSolicitudesPendientes() {
        List<Solicitud_aplicacion> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitud_aplicacion WHERE estado = 'PENDIENTE'";
        SalaDAO salaDAO = new SalaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Solicitud_aplicacion solicitud = new Solicitud_aplicacion(
                    rs.getString("id"),
                    usuarioDAO.obtenerUsuarioPorId(rs.getString("cedula_usuario")),
                    salaDAO.obtenerSalaPorId(rs.getString("id_sala")),///Funcion para obtener sala por id_sala
                    rs.getString("nombre"),
                    rs.getString("detalles"),
                    rs.getString("estado")
                );
                solicitudes.add(solicitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }
*/
}