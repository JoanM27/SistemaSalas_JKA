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
    public boolean eliminarUsuarioPorCedula(String cedula) throws SQLException {
    // Primero obtenemos el usuario para verificar su tipo
    Usuario usuario = obtenerUsuarioPorId(cedula);
    if (usuario == null) {
        return false; // El usuario no existe
    }
    connection.setAutoCommit(false);
    // Si es administrador, eliminamos primero la solicitud de administración
    if ("administrador".equalsIgnoreCase(usuario.getTipoUsuario())) {
        Solicitud_adminDAO solicitudAdminDAO = new Solicitud_adminDAO();
        if (!solicitudAdminDAO.eliminarSolicitudPorCedula(cedula)) {
            System.err.println("No se pudo eliminar la solicitud de administración para el usuario: " + cedula);
            return false;
        }
    }

    // Procedemos con la eliminación del usuario
    String sql = "DELETE FROM usuario WHERE cedula = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cedula);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                connection.commit(); // Confirmamos la transacción
                return true;
            } else {
                connection.rollback(); // No se eliminó ningún usuario
                return false;
            }
        } catch (SQLException e) {
            connection.rollback(); // Revertimos en caso de error
            
            // Manejo específico de errores
            if (e.getErrorCode() == 2292) {
                System.err.println("Error de integridad referencial al eliminar usuario " + cedula + 
                                 ". Hay registros en otras tablas que dependen de este usuario.");
                // Aquí podrías lanzar una excepción personalizada
            }
            
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true); // Restauramos auto-commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
     
}
    public boolean actualizarUsuario(Usuario usuario) {
    String sql = "UPDATE usuario SET correo_institucional = ?, nombre = ?, apellido = ?, telefono = ?, clave = ?, tipo_usuario = ? WHERE cedula = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, usuario.getCorreo());
        stmt.setString(2, usuario.getNombre());
        stmt.setString(3, usuario.getApellido());
        stmt.setString(4, usuario.getTelefono());
        stmt.setString(5, usuario.getPassword());
        stmt.setString(6, usuario.getTipoUsuario());
        stmt.setString(7, usuario.getCedula());

        int filasAfectadas = stmt.executeUpdate();
        return filasAfectadas > 0;
    } catch (SQLException e) {
        System.out.println("Error al actualizar usuario: " + e.getMessage());
        return false;
    }
}
}
