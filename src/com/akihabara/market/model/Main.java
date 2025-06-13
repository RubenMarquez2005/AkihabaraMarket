package com.akihabara.market.model;

import com.akihabara.market.view.InterfazConsola;
import com.akihabara.market.dao.ProductoDAO;
import com.akihabara.market.dao.DatabaseConnection;
import com.akihabara.market.service.LlmService;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Establecer conexión a la base de datos
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conexion = dbConn.getConexion();

        // Verifica que la conexión no sea nula
        if (conexion == null) {
            System.err.println("No se pudo establecer la conexión con la base de datos.");
            return;
        }

        // Crear DAO, vista y servicio IA
        ProductoDAO dao = new ProductoDAO(conexion);
        InterfazConsola vista = new InterfazConsola();
        LlmService llm = new LlmService();

        boolean salir = false;

        while (!salir) {
            vista.mostrarMenuPrincipal();
            int opcion = vista.leerOpcion();

            switch (opcion) {
                case 1:
                    //  IA para sugerir el nombre
                    String tipo = vista.pedirTexto("Tipo de producto (ej. figura, camiseta, taza)");
                    String franquicia = vista.pedirTexto("Franquicia (ej. Naruto, One Piece)");

                    String nombreSugerido = llm.sugerirNombreProducto(tipo, franquicia);
                    vista.mostrarMensaje(" Nombre sugerido por IA: " + nombreSugerido);

                    String categoria = vista.pedirTexto("Categoría del producto");
                    double precio = vista.pedirPrecio();

                    if (precio >= 0) {
                        ProductoOtaku nuevo = new ProductoOtaku(nombreSugerido, categoria, precio);
                        dao.agregarProducto(nuevo);
                        vista.mostrarMensaje("Producto añadido correctamente.");
                    } else {
                        vista.mostrarMensaje("Precio inválido.");
                    }
                    break;

                case 2:
                    int idConsulta = Integer.parseInt(vista.pedirTexto("Ingrese ID del producto"));
                    ProductoOtaku p = dao.obtenerPorId(idConsulta);
                    if (p != null) vista.mostrarProducto(p.toString());
                    else vista.mostrarMensaje("Producto no encontrado.");
                    break;

                case 3:
                    List<ProductoOtaku> lista = dao.obtenerTodosLosProductos();
                    if (lista.isEmpty()) {
                        vista.mostrarMensaje("No hay productos registrados.");
                    } else {
                        lista.forEach(prod -> vista.mostrarProducto(prod.toString()));
                    }
                    break;

                case 4:
                    String nombreBuscado = vista.pedirTexto("Nombre del producto");
                    List<ProductoOtaku> porNombre = dao.buscarPorNombre(nombreBuscado);
                    porNombre.forEach(prod -> vista.mostrarProducto(prod.toString()));
                    break;

                case 5:
                    String categoriaBuscada = vista.pedirTexto("Categoría");
                    List<ProductoOtaku> porCategoria = dao.buscarPorCategoria(categoriaBuscada);
                    porCategoria.forEach(prod -> vista.mostrarProducto(prod.toString()));
                    break;

                case 6:
                    int idActualizar = Integer.parseInt(vista.pedirTexto("ID del producto a actualizar"));
                    ProductoOtaku existente = dao.obtenerPorId(idActualizar);
                    if (existente != null) {
                        String nuevoNombre = vista.pedirTexto("Nuevo nombre");
                        String nuevaCategoria = vista.pedirTexto("Nueva categoría");
                        double nuevoPrecio = vista.pedirPrecio();

                        if (nuevoPrecio >= 0) {
                            existente.setNombre(nuevoNombre);
                            existente.setCategoria(nuevaCategoria);
                            existente.setPrecio(nuevoPrecio);
                            dao.actualizarProducto(existente);
                            vista.mostrarMensaje("Producto actualizado.");
                        }
                    } else {
                        vista.mostrarMensaje("Producto no encontrado.");
                    }
                    break;

                case 7:
                    int idEliminar = Integer.parseInt(vista.pedirTexto("ID del producto a eliminar"));
                    boolean eliminado = dao.eliminarProducto(idEliminar);
                    if (eliminado) vista.mostrarMensaje("Producto eliminado.");
                    else vista.mostrarMensaje("Producto no encontrado.");
                    break;

                case 8:
                    salir = true;
                    vista.mostrarMensaje("Saliendo del programa...");
                    dbConn.cerrarConexion(); // cerrar conexión 
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }
}

