package Control;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.ConexionOracle;
import Modelo.Solicitud_admin;

public class Solicitud_adminDAO {

    private ConexionOracle conexionOracle;

    public Solicitud_adminDAO() {
        conexionOracle = new ConexionOracle();
    }

    // 1. Insertar nueva solicitud de administración
    public boolean solicitar(Solicitud_admin solicitud) {
    String sql = "INSERT INTO solicitud_admin (id, estado, cedula_usuario) VALUES (agregar_id_soli_admin, ?, ?)";

    try (Connection conn = conexionOracle.getConnection()) {
        conn.setAutoCommit(false); // Desactiva auto-commit

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, solicitud.getEstado());
            pstmt.setString(2, solicitud.get_Cedula_Usuario());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                conn.commit(); // Confirmar la transacción
                return true;
            } else {
                conn.rollback(); // Revertir si no se insertó nada
                return false;
            }
        } catch (SQLException e) {
            conn.rollback(); // Revertir si hubo un error
            e.printStackTrace();
            return false;
        } finally {
            conn.setAutoCommit(true); // Restaurar auto-commit
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    // 2. Validar si la solicitud existe (por ID)
    public boolean validar_solicitud(String id) {
        String sql = "SELECT COUNT(*) FROM solicitud_admin WHERE id = ?";
        try (Connection conn = conexionOracle.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Listar todas las solicitudes
    public List<Solicitud_admin> lista_solicitudes() {
        UsuarioDAO usuarioDAO =new UsuarioDAO();
        List<Solicitud_admin> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitud_admin";

        try (Connection conn = conexionOracle.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Solicitud_admin s = new Solicitud_admin();
                s.setId(rs.getString("id"));
                s.setEstado(rs.getString("estado"));
                s.setUsuario(usuarioDAO.obtenerUsuarioPorId(rs.getString("cedula_aplicacion")));
                solicitudes.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

    // 4. Obtener detalles de una solicitud por ID
    public Solicitud_admin detalles_solicitud(String id) {
        UsuarioDAO usuarioDAO =new UsuarioDAO();
        String sql = "SELECT * FROM solicitud_admin WHERE id = ?";
        try (Connection conn = conexionOracle.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Solicitud_admin s = new Solicitud_admin();
                s.setId(rs.getString("id"));
                s.setEstado(rs.getString("estado"));
                s.setUsuario(usuarioDAO.obtenerUsuarioPorId(rs.getString("cedula_aplicacion")));
                return s;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 5. Obtener el estado de la solicitud por cédula del usuario
    public String obtenerEstadoPorCedula(String cedulaUsuario) {
        String sql = "SELECT estado FROM solicitud_admin WHERE cedula_usuario = ?";
    
        try (Connection conn = conexionOracle.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cedulaUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("estado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    return null; // Si no se encuentra o hay error
}
}
