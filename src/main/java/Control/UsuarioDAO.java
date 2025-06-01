package Control;

import Modelo.Usuario;
import Database.ConexionOracle;
import Modelo.Solicitud_admin;
import java.sql.*;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO() {
        try {
            connection = ConexionOracle.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario autenticar(String cedula, String clave) {
        String sql = "SELECT * FROM usuario WHERE cedula = ? AND clave = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cedula);
            pstmt.setString(2, clave);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo_institucional"),
                    rs.getString("telefono"),
                    rs.getString("clave"),
                    rs.getString("tipo_usuario")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registrarUsuario(Usuario usuario) {
    String sql = "INSERT INTO usuario VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try {
        connection.setAutoCommit(false); // Desactiva auto-commit

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getCedula());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getApellido());
            pstmt.setString(5, usuario.getTelefono());
            pstmt.setString(6, usuario.getPassword());
            pstmt.setString(7, usuario.getTipoUsuario());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                connection.commit(); // Commit manual
                return true;
            } else {
                connection.rollback(); // En caso de fallo
                return false;
            }
        }

    } catch (SQLException e) {
        try {
            if (connection != null) {
                connection.rollback(); // Rollback si hay error
            }
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
        e.printStackTrace();
        return false;
    } finally {
        try {
            if (connection != null) {
                connection.setAutoCommit(true); // Restaura auto-commit
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
    public boolean registrarUsuarioYCrearSolicitud(Usuario usuario, Solicitud_admin solicitud) {
    Connection conn = null;
    try {
        conn = ConexionOracle.getConnection();
        conn.setAutoCommit(false);

        // Insertar usuario
        String sqlUsuario = "INSERT INTO usuario VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUsuario)) {
            pstmt.setString(1, usuario.getCedula());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getApellido());
            pstmt.setString(5, usuario.getTelefono());
            pstmt.setString(6, usuario.getPassword());
            pstmt.setString(7, usuario.getTipoUsuario());
            pstmt.executeUpdate();
        }
        conn.commit();

        // Insertar solicitud
        String sqlSolicitud = "INSERT INTO solicitud_admin (id, estado, cedula_usuario) VALUES (agregar_id_soli_admin, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSolicitud)) {
            pstmt.setString(1, solicitud.getEstado());
            pstmt.setString(2, solicitud.getUsuario().getCedula());
            pstmt.executeUpdate();
        }
        conn.commit();
        
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
    public Usuario obtenerUsuarioPorId(String cedula) {
    String sql = "SELECT * FROM usuario WHERE cedula = ?";
    
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, cedula);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return new Usuario(
                rs.getString("cedula"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo_institucional"),
                rs.getString("telefono"),
                rs.getString("clave"),
                rs.getString("tipo_usuario")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    
    public boolean existeUsuarioPorCedula(String cedula) {
    String sql = "SELECT 1 FROM usuario WHERE cedula = ?";
    
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, cedula);
        ResultSet rs = pstmt.executeQuery();
        
        return rs.next(); // Si hay resultado, el usuario existe
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Asumimos que no existe si hay error
    }
}
    public boolean eliminarUsuarioPorCedula(String cedula) {
    String sql = "DELETE FROM usuario WHERE cedula = ?";
    
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, cedula);
        int filasAfectadas = pstmt.executeUpdate();
        return filasAfectadas > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
