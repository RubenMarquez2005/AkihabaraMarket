package com.akihabara.market.model;

import com.akihabara.market.model.ProductoOtaku;
import com.akihabara.market.dao.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        // Crear un producto de prueba
        ProductoOtaku producto = new ProductoOtaku(1, "Figura de Luffy", "Figura", 34.99, 20);

        // Mostrar la información del producto
        System.out.println("--- Información del Producto ---");
        System.out.println(producto);

        // Probar conexión a la base de datos
        DatabaseConnection db = new DatabaseConnection();
        db.cerrarConexion();
    }
}
