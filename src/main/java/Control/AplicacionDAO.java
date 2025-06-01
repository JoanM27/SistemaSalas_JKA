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

    // 1. Crear aplicación
    public boolean crearAplicacion(Aplicacion aplicacion) {
        String sql = "INSERT INTO aplicacion (id, nombre, descripcion) VALUES (agregar_id_aplicacion, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, aplicacion.getNombre());
            pstmt.setString(2, aplicacion.getDescripcion());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Obtener todas las aplicaciones
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

    // 3. Obtener aplicación por ID
    public Aplicacion obtenerAplicacionPorId(String id) {
        String sql = "SELECT * FROM aplicacion WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Aplicacion(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4. Modificar aplicación
    public boolean modificarAplicacion(Aplicacion aplicacion) {
        Aplicacion actual = obtenerAplicacionPorId(aplicacion.getId());
        if (actual == null) return false;

        String nuevoNombre = aplicacion.getNombre() != null ? aplicacion.getNombre() : actual.getNombre();
        String nuevaDescripcion = aplicacion.getDescripcion() != null ? aplicacion.getDescripcion() : actual.getDescripcion();

        String sql = "UPDATE aplicacion SET nombre = ?, descripcion = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nuevaDescripcion);
            pstmt.setString(3, aplicacion.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Eliminar aplicación por ID
    public boolean eliminarAplicacionPorId(String id) {
        String sql = "DELETE FROM aplicacion WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // 6. Obtener ID de aplicación por nombre
    public String obtenerIdPorNombre(String nombre) {
        String sql = "SELECT id FROM aplicacion WHERE nombre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 7. Obtener lista de nombres de aplicaciones
    public List<String> obtenerListaDeNombresAplicaciones() {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nombre FROM aplicacion ORDER BY nombre";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombres;
}
    // 8. Obtener aplicación por nombre
public Aplicacion obtenerAplicacionPorNombre(String nombre) {
    String sql = "SELECT * FROM aplicacion WHERE nombre = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, nombre);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new Aplicacion(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getString("descripcion")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
}

