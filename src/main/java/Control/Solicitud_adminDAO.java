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
        String sql = "SELECT * FROM solicitud_admin WHERE estado LIKE 'en espera'";

        try (Connection conn = conexionOracle.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Solicitud_admin s = new Solicitud_admin();
                s.setId(rs.getString("id"));
                s.setEstado(rs.getString("estado"));
                s.setUsuario(usuarioDAO.obtenerUsuarioPorId(rs.getString("cedula_usuario")));
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
                s.setUsuario(usuarioDAO.obtenerUsuarioPorId(rs.getString("cedula_usuario")));
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
        // 6. Aceptar solicitud por ID
    public boolean aceptarSolicitud(String id) {
        return actualizarEstadoSolicitud(id, "aprobado");
    }

    // 7. Denegar solicitud por ID
    public boolean denegarSolicitud(String id) {
        return actualizarEstadoSolicitud(id, "denegado");
    }

    // Método privado auxiliar para actualizar estado
    private boolean actualizarEstadoSolicitud(String id, String nuevoEstado) {
        String sql = "UPDATE solicitud_admin SET estado = ? WHERE id = ?";

        try (Connection conn = conexionOracle.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevoEstado);
            pstmt.setString(2, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminarSolicitudPorCedula(String cedulaUsuario) {
    String sql = "DELETE FROM solicitud_admin WHERE cedula_usuario = ?";
    
    try (Connection conn = conexionOracle.getConnection()) {
        conn.setAutoCommit(false); // Desactivar auto-commit para manejar transacción
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cedulaUsuario);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit(); // Confirmar la transacción si fue exitosa
                return true;
            } else {
                conn.rollback(); // No se eliminó ningún registro
                return false;
            }
        } catch (SQLException e) {
            conn.rollback(); // Revertir en caso de error

            if (e.getErrorCode() == 2292) {
                System.err.println("Error de integridad referencial: No se puede eliminar porque hay registros relacionados en otras tablas.");
                
            }
            
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true); // Restaurar auto-commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
