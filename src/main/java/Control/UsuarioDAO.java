package Control;

import Modelo.Usuario;
import Database.ConexionOracle;
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
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getCedula());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getApellido());
            pstmt.setString(5, usuario.getTelefono());
            pstmt.setString(6, usuario.getPassword());
            pstmt.setString(7, usuario.getTipoUsuario());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
}
