package com.akihabara.market.dao;
import java.sql.*;

public class DatabaseConnection {
    // Constantes con los datos de conexión
    private static final String DB_URL = "jdbc:mysql://localhost:3306/akihabara_db";
    private static final String USER = "userAkihabara";
    private static final String PASSWORD = "curso";

    // Propiedad para la conexión
    private Connection conexion;

    // Constructor: abre la conexión
    public DatabaseConnection() {
        try {
            // Conexión directa sin cargar driver 
            conexion = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Se ha establecido con éxito la conexión a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Método para obtener la conexión
    public Connection getConexion() {
        return conexion;
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Se ha cerrado la conexión con la base de datos.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
