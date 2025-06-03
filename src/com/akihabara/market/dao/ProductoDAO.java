package com.akihabara.market.dao;

import com.akihabara.market.model.ProductoOtaku;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // Método para agregar un producto a la base de datos
    public boolean agregarProducto(ProductoOtaku producto) {
        // Abrimos la conexión
        DatabaseConnection db = new DatabaseConnection();
        Connection conexion = db.getConexion();

        if (conexion == null) {
            System.out.println("Error: no se pudo conectar a la base de datos.");
            return false;
        }

        String sql = "INSERT INTO producto (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Seteamos los valores para la consulta
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            return false;
        } finally {
            db.cerrarConexion();
        }
    }

    // Método para obtener un producto por su ID
    public ProductoOtaku obtenerProductoPorId(int id) {
        DatabaseConnection db = new DatabaseConnection();
        Connection conexion = db.getConexion();

        if (conexion == null) {
            System.out.println("Error: no se pudo conectar a la base de datos.");
            return null;
        }

        String sql = "SELECT * FROM producto WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ProductoOtaku producto = new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );
                return producto;
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener producto: " + e.getMessage());
        } finally {
            db.cerrarConexion();
        }
        return null;
    }

    // Método para obtener todos los productos
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        List<ProductoOtaku> lista = new ArrayList<>();
        DatabaseConnection db = new DatabaseConnection();
        Connection conexion = db.getConexion();

        if (conexion == null) {
            System.out.println("Error: no se pudo conectar a la base de datos.");
            return lista;
        }

        String sql = "SELECT * FROM producto";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

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
            System.out.println("Error al obtener productos: " + e.getMessage());
        } finally {
            db.cerrarConexion();
        }

        return lista;
    }

    // Método para actualizar un producto existente
    public boolean actualizarProducto(ProductoOtaku producto) {
        DatabaseConnection db = new DatabaseConnection();
        Connection conexion = db.getConexion();

        if (conexion == null) {
            System.out.println("Error: no se pudo conectar a la base de datos.");
            return false;
        }

        String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.setInt(5, producto.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        } finally {
            db.cerrarConexion();
        }
    }

    // Método para eliminar un producto por ID
    public boolean eliminarProducto(int id) {
        DatabaseConnection db = new DatabaseConnection();
        Connection conexion = db.getConexion();

        if (conexion == null) {
            System.out.println("Error: no se pudo conectar a la base de datos.");
            return false;
        }

        String sql = "DELETE FROM producto WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        } finally {
            db.cerrarConexion();
        }
    }

    // Método para buscar productos por nombre (contenga el texto)
    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
        List<ProductoOtaku> lista = new ArrayList<>();
        DatabaseConnection db = new DatabaseConnection();
        Connection conexion = db.getConexion();

        if (conexion == null) {
            System.out.println("Error: no se pudo conectar a la base de datos.");
            return lista;
        }

        String sql = "SELECT * FROM producto WHERE nombre LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

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
            System.out.println("Error al buscar productos por nombre: " + e.getMessage());
        } finally {
            db.cerrarConexion();
        }

        return lista;
    }

    // Método para buscar productos por categoría
    public List<ProductoOtaku> buscarProductoPorCategoria(String categoria) {
        List<ProductoOtaku> lista = new ArrayList<>();
        DatabaseConnection db = new DatabaseConnection();
        Connection conexion = db.getConexion();

        if (conexion == null) {
            System.out.println("Error: no se pudo conectar a la base de datos.");
            return lista;
        }

        String sql = "SELECT * FROM producto WHERE categoria LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + categoria + "%");
            ResultSet rs = ps.executeQuery();

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
            System.out.println("Error al buscar productos por categoría: " + e.getMessage());
        } finally {
            db.cerrarConexion();
        }

        return lista;
    }
}
