package Control;

import Modelo.Sala;
import Modelo.Ubicacion;
import Database.ConexionOracle;
import Modelo.Aplicacion;
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
        // 1. Crear nueva sala
    public boolean crearSala(Sala sala) {
        String sql = "INSERT INTO sala (id, nombre, maximo_equipos, normas, id_ubicacion) VALUES (agregar_id_sala, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, sala.getNombre());
            pstmt.setInt(2, sala.getMaxComputadoras());
            pstmt.setString(3, sala.getNormas());
            if (sala.getUbicacion() != null) {
                pstmt.setString(4, sala.getUbicacion().getId());
            } else {
                pstmt.setNull(4, Types.VARCHAR);
            }

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Eliminar sala por ID
    public boolean eliminarSalaPorId(String id) {
        String sql = "DELETE FROM sala WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Modificar sala (sin modificar los campos que vienen null)
    public boolean modificarSala(Sala sala) {
        // Primero obtener la sala actual
        Sala salaExistente = obtenerSalaPorId(sala.getId());
        if (salaExistente == null) return false;

        // Usar valores actuales si el nuevo valor es null
        String nuevoNombre = sala.getNombre() != null ? sala.getNombre() : salaExistente.getNombre();
        int nuevoMaximoEquipos = sala.getMaxComputadoras() > 0 ? sala.getMaxComputadoras() : salaExistente.getMaxComputadoras();
        String nuevasNormas = sala.getNormas() != null ? sala.getNormas() : salaExistente.getNormas();
        String nuevaUbicacionId = (sala.getUbicacion() != null && sala.getUbicacion().getId() != null) ?
                                    sala.getUbicacion().getId() :
                                    (salaExistente.getUbicacion() != null ? salaExistente.getUbicacion().getId() : null);

        String sql = "UPDATE sala SET nombre = ?, maximo_equipos = ?, normas = ?, id_ubicacion = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, nuevoMaximoEquipos);
            pstmt.setString(3, nuevasNormas);
            if (nuevaUbicacionId != null) {
                pstmt.setString(4, nuevaUbicacionId);
            } else {
                pstmt.setNull(4, Types.VARCHAR);
            }
            pstmt.setString(5, sala.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
    public List<Sala> obtenerSalasPorUbicacionId(String idUbicacion) {
    List<Sala> salas = new ArrayList<>();
    String sql = "SELECT s.*, u.nombre_bloque, u.piso FROM sala s " +
                 "LEFT JOIN ubicacion u ON s.id_ubicacion = u.id " +
                 "WHERE s.id_ubicacion = ?";//se puede cambiar por una funcion

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, idUbicacion);
        try (ResultSet rs = pstmt.executeQuery()) {
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
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return salas;
}
    ///////
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
    
     public boolean agregarAplicacionASala(String idSala, String idAplicacion) {
        String sql = "INSERT INTO sala_aplicacion (id, id_sala, id_aplicacion) VALUES (agregar_id_sala_aplicacion, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, idSala);
            pstmt.setString(2, idAplicacion);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     public boolean eliminarAplicacionesDeSala(String idSala) {
        String sql = "DELETE FROM sala_aplicacion WHERE id_sala = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, idSala);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     public List<Aplicacion> obtenerAplicacionesDeSala(String idSala) {
        List<Aplicacion> aplicaciones = new ArrayList<>();
        String sql = "SELECT a.id, a.nombre, a.descripcion FROM aplicacion a "
                   + "JOIN sala_aplicacion sa ON a.id = sa.id_aplicacion "
                   + "WHERE sa.id_sala = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, idSala);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                aplicaciones.add(new Aplicacion(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aplicaciones;
    }

    public boolean eliminarAplicacionDeSala(String idSala, String idAplicacion) {
        String sql = "DELETE FROM sala_aplicacion WHERE id_sala = ? AND id_aplicacion = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, idSala);
            pstmt.setString(2, idAplicacion);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
