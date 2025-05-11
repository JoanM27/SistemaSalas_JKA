/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    // Datos de conexión (idealmente cargados desde un archivo .properties)
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";//jdbc:oracle:thin:@//192.168.254.215:1521/orcl  <--Conexion en sala de informatica
    private static final String USER = "sistema_salas_jka";
    private static final String PASSWORD = "sistema_salas_jka";

    // Método estático para obtener la conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método opcional para cerrar la conexión (si no usas try-with-resources)
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}