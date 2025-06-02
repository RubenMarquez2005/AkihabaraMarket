package com.akihabara.market.dao;

import com.akihabara.market.model.ProductoOtaku;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    // Creamos una instancia de conexión a la BBDD
    private DatabaseConnection dbConnection;

    // Constructor: al crear un ProductoDAO, se abre conexión
    public ProductoDAO() {
        dbConnection = new DatabaseConnection();
    }

    // Método para añadir un nuevo producto
    public void agregarProducto(ProductoOtaku producto) {
        String sql = "INSERT INTO producto (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());

            stmt.executeUpdate();
            System.out.println("Producto agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
        }
    }

    // Método para obtener un producto por su ID
    public ProductoOtaku obtenerProductoPorId(int id) {
        ProductoOtaku producto = null;
        String sql = "SELECT * FROM producto WHERE id = ?";

        try (Connection conn = dbConnection.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                producto = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener producto: " + e.getMessage());
        }
        return producto;
    }

    // Método para obtener todos los productos
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";

        try (Connection conn = dbConnection.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProductoOtaku producto = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }

    // Método para actualizar un producto existente
    public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        boolean actualizado = false;

        try (Connection conn = dbConnection.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());

            int filasAfectadas = stmt.executeUpdate();
            actualizado = filasAfectadas > 0;

            if (actualizado) {
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("No se encontró un producto con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
        return actualizado;
    }

    // Método para eliminar un producto por su ID
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        boolean eliminado = false;

        try (Connection conn = dbConnection.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            eliminado = filasAfectadas > 0;

            if (eliminado) {
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("No se encontró un producto con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
        return eliminado;
    }

    // Método para buscar productos por nombre (usa LIKE para buscar si contiene letras)
    public List<ProductoOtaku> buscarProductosPorNombre(String nombreParcial) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE nombre LIKE ?";

        try (Connection conn = dbConnection.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // % antes y después para buscar por cualquier parte del nombre
            stmt.setString(1, "%" + nombreParcial + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductoOtaku producto = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar productos por nombre: " + e.getMessage());
        }
        return productos;
    }

    // Método para buscar productos por categoría (también con LIKE para que sea flexible)
    public List<ProductoOtaku> buscarProductoPorCategoria(String categoriaParcial) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE categoria LIKE ?";

        try (Connection conn = dbConnection.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + categoriaParcial + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductoOtaku producto = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar productos por categoría: " + e.getMessage());
        }
        return productos;
    }
}

