package com.akihabara.market.dao;

import com.akihabara.market.model.ProductoOtaku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    // Conexión a la base de datos
    private Connection conexion;

    // Constructor: pedimos la conexión cuando se crea el objeto DAO
    public ProductoDAO() {
        DatabaseConnection db = new DatabaseConnection();
        conexion = db.getConexion();
    }

    // MÉTODO Nº1: Agregamos un producto a la base de datos
    public void agregarProducto(ProductoOtaku producto) {
        String sql = "INSERT INTO producto (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.executeUpdate(); // Ejecutamos el INSERT
            System.out.println("✅ Producto añadido correctamente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al agregar el producto: " + e.getMessage());
        }
    }

    // MÉTODO Nº2: Obtenemos un producto por su ID
    public ProductoOtaku obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id); // Establecemos el ID a buscar
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Creamos un objeto con los datos encontrados
                return new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al obtener el producto: " + e.getMessage());
        }
        return null; // Si no encuentra nada, devuelve null
    }

    // MÉTODO Nº3: Obtenemos todos los productos de la tabla
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                productos.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }

    // MÉTODO Nº4: Actualizamos un producto existente
    public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());
            int filas = stmt.executeUpdate();

            return filas > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    // MÉTODO Nº5: Eliminamos un producto por ID
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar el producto: " + e.getMessage());
            return false;
        }
    }

    // MÉTODO Nº6: Buscamos productos por nombre
    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE nombre LIKE ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%"); // Búsqueda parcial con LIKE
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                productos.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar productos por nombre: " + e.getMessage());
        }
        return productos;
    }

    // MÉTODO Nº7: Buscamos los productos por categoría
    public List<ProductoOtaku> buscarProductoPorCategoria(String categoria) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE categoria = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, categoria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                productos.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar productos por categoría: " + e.getMessage());
        }
        return productos;
    }
}
