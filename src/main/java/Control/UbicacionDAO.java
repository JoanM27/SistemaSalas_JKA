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

        // 1. Crear ubicación
        public boolean crearUbicacion(Ubicacion ubicacion) {
            String sql = "INSERT INTO ubicacion (id, nombre_bloque, piso) VALUES (agregar_id_ubicacion, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, ubicacion.getBloque());
                pstmt.setInt(2, ubicacion.getPiso());
                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        // 2. Eliminar ubicación por ID
        public boolean eliminarUbicacionPorId(String id) {
            String sql = "DELETE FROM ubicacion WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, id);
                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        // 3. Modificar ubicación (solo cambia los datos que no son null)
        public boolean modificarUbicacion(Ubicacion ubicacion) {
            // Obtener ubicación actual
            Ubicacion actual = obtenerUbicacionPorId(ubicacion.getId());
            if (actual == null) return false;

            // Usar datos actuales si los nuevos son null
            String nuevoBloque = ubicacion.getBloque() != null ? ubicacion.getBloque() : actual.getBloque();
            int nuevoPiso = ubicacion.getPiso() > 0 ? ubicacion.getPiso() : actual.getPiso();

            String sql = "UPDATE ubicacion SET nombre_bloque = ?, piso = ? WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, nuevoBloque);
                pstmt.setInt(2, nuevoPiso);
                pstmt.setString(3, ubicacion.getId());
                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        // 4. Obtener ubicación por ID
        public Ubicacion obtenerUbicacionPorId(String id) {
            String sql = "SELECT * FROM ubicacion WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new Ubicacion(
                        rs.getString("id"),
                        rs.getString("nombre_bloque"),
                        rs.getInt("piso")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        // 5. Listar todas las ubicaciones
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
        
        public boolean eliminarUbicacionConSalasPorId(String idUbicacion) {
        String eliminarSalasSQL = "DELETE FROM sala WHERE id_ubicacion = ?";
        String eliminarUbicacionSQL = "DELETE FROM ubicacion WHERE id = ?";

        try (PreparedStatement pstmtSalas = connection.prepareStatement(eliminarSalasSQL);
             PreparedStatement pstmtUbicacion = connection.prepareStatement(eliminarUbicacionSQL)) {

            connection.setAutoCommit(false); // Iniciar transacción

            // 1. Eliminar salas asociadas
            pstmtSalas.setString(1, idUbicacion);
            pstmtSalas.executeUpdate();

            // 2. Eliminar la ubicación
            pstmtUbicacion.setString(1, idUbicacion);
            int ubicacionEliminada = pstmtUbicacion.executeUpdate();

            connection.commit(); // Confirmar cambios
            return ubicacionEliminada > 0;

        } catch (SQLException e) {
            try {
                connection.rollback(); // Revertir si hay error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true); // Restaurar auto-commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       }
        public boolean actualizarNombreBloque(String bloqueActual, String nuevoNombre) {
    String sql = "UPDATE ubicacion SET nombre_bloque = ? WHERE nombre_bloque = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, nuevoNombre);
        pstmt.setString(2, bloqueActual);
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
        public boolean eliminarBloqueConTodo(String bloque) {
    String obtenerIdsUbicacionesSQL = "SELECT id FROM ubicacion WHERE LOWER(nombre_bloque) = LOWER(?)";
    String eliminarSalasSQL = "DELETE FROM sala WHERE id_ubicacion = ?";
    String eliminarUbicacionSQL = "DELETE FROM ubicacion WHERE id = ?";

    try (
        PreparedStatement stmtIds = connection.prepareStatement(obtenerIdsUbicacionesSQL);
        PreparedStatement stmtEliminarSalas = connection.prepareStatement(eliminarSalasSQL);
        PreparedStatement stmtEliminarUbicaciones = connection.prepareStatement(eliminarUbicacionSQL)
    ) {
        connection.setAutoCommit(false);

        // Buscar todas las ubicaciones del bloque
        stmtIds.setString(1, bloque);
        ResultSet rs = stmtIds.executeQuery();

        List<String> idsUbicaciones = new ArrayList<>();
        while (rs.next()) {
            idsUbicaciones.add(rs.getString("id"));
        }

        // Eliminar salas y luego ubicaciones
        for (String id : idsUbicaciones) {
            stmtEliminarSalas.setString(1, id);
            stmtEliminarSalas.executeUpdate();

            stmtEliminarUbicaciones.setString(1, id);
            stmtEliminarUbicaciones.executeUpdate();
        }

        connection.commit();
        return true;

    } catch (SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        return false;
    } finally {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
        public Ubicacion obtenerUbicacionPorBloqueYPiso(String bloque, int piso) {
            String sql = "SELECT * FROM ubicacion WHERE LOWER(nombre_bloque) = LOWER(?) AND piso = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, bloque);
                pstmt.setInt(2, piso);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return new Ubicacion(
                        rs.getString("id"),
                        rs.getString("nombre_bloque"),
                        rs.getInt("piso")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

