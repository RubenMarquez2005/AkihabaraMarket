package com.akihabara.market.dao;

import com.akihabara.market.model.ProductoOtaku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Connection conexion;

    public ProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // 1. Agregar producto
    public void agregarProducto(ProductoOtaku producto) {
        String sql = "INSERT INTO producto (nombre, categoria, precio) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                producto.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar producto: " + e.getMessage());
        }
    }

    // 2. Obtener producto por ID
    public ProductoOtaku obtenerPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener producto por ID: " + e.getMessage());
        }
        return null;
    }

    // 3. Listar todos los productos
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
                        rs.getDouble("precio")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
        return productos;
    }

    // 4. Buscar por nombre
    public List<ProductoOtaku> buscarPorNombre(String nombre) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE nombre LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productos.add(new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar producto por nombre: " + e.getMessage());
        }
        return productos;
    }

    // 5. Buscar por categoría
    public List<ProductoOtaku> buscarPorCategoria(String categoria) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE categoria LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + categoria + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productos.add(new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar producto por categoría: " + e.getMessage());
        }
        return productos;
    }

    // 6. Actualizar producto
    public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getId());
            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
        }
        return false;
    }

    // 7. Eliminar producto
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
        }
        return false;
    }
}

