package com.akihabara.market.model;
import com.akihabara.market.dao.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        // Creamos un producto de prueba
        ProductoOtaku producto = new ProductoOtaku(
            1, "Figura de Luffy", "Figura", 34.99, 20
        );

        // Mostramos los atributos individualmente
        System.out.println("--- Producto Otaku ---");
        System.out.println("ID: " + producto.getId());
        System.out.println("Nombre: " + producto.getNombre());
        System.out.println("Categoría: " + producto.getCategoria());
        System.out.printf("Precio: %.2f €\n", producto.getPrecio());
        System.out.println("Stock: " + producto.getStock() + " unidades");

        // Comprobamos la conexión a la base de datos
        DatabaseConnection db = new DatabaseConnection();
        db.cerrarConexion();
    }
}
