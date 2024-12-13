package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // URL de la base de datos (ajústala a tu configuración)
    private static final String url = "jdbc:mysql://localhost:3306/carro?useTimezone=true&serverTimezone=UTC";
    private static final String username = "root";  // Reemplázalo con tu usuario
    private static final String password = "";  // Reemplázalo con tu contraseña

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);

    }
}

