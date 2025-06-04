package com.akihabara.market.dao;

import com.akihabara.market.model.ProductoOtaku;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Esta clase se encarga de todas las operaciones CRUD con la base de datos
public class ProductoDAO {
    private DatabaseConnection db;

    public ProductoDAO() {
        db = new DatabaseConnection(); // Creamos la conexión una sola vez al iniciar
    }

    // Añadir un nuevo producto a la base de datos
    public void agregarProducto(ProductoOtaku producto) {
        String sql = "INSERT INTO producto (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = db.getConexion().prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.executeUpdate();
            System.out.println("Producto añadido correctamente.");
        } catch (SQLException e) {
            System.out.println(" Error al añadir producto: " + e.getMessage());
        }
    }

    // Buscar producto por ID
    public ProductoOtaku buscarPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id = ?";
        try (PreparedStatement stmt = db.getConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            System.out.println(" Error al buscar producto por ID: " + e.getMessage());
        }
        return null;
    }

    // Obtener todos los productos
    public List<ProductoOtaku> listarTodos() {
        List<ProductoOtaku> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (Statement stmt = db.getConexion().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProductoOtaku producto = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                lista.add(producto);
            }
        } catch (SQLException e) {
            System.out.println(" Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    // Buscar por nombre
    public List<ProductoOtaku> buscarPorNombre(String nombre) {
        List<ProductoOtaku> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE nombre LIKE ?";
        try (PreparedStatement stmt = db.getConexion().prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println(" Error al buscar por nombre: " + e.getMessage());
        }
        return lista;
    }

    // Buscar por categoría
    public List<ProductoOtaku> buscarPorCategoria(String categoria) {
        List<ProductoOtaku> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE categoria LIKE ?";
        try (PreparedStatement stmt = db.getConexion().prepareStatement(sql)) {
            stmt.setString(1, "%" + categoria + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println(" Error al buscar por categoría: " + e.getMessage());
        }
        return lista;
    }

    // Actualizar producto
    public void actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        try (PreparedStatement stmt = db.getConexion().prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());
            stmt.executeUpdate();
            System.out.println(" Producto actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println(" Error al actualizar: " + e.getMessage());
        }
    }

    // Eliminar producto
    public void eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (PreparedStatement stmt = db.getConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println(" Producto eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println(" Error al eliminar producto: " + e.getMessage());
        }
    }
}

