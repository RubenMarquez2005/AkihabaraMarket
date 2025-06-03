package com.akihabara.market.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Datos de conexión a la base de datos MySQL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/akihabara_db";
    private static final String USER = "userAkihabara";
    private static final String PASSWORD = "curso";

    // Propiedad que guarda la conexión
    private Connection conexion;

    // Constructor que abre la conexión a la base de datos
    public DatabaseConnection() {
        try {
            conexion = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
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
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}

