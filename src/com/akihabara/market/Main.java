package com.akihabara.market;

import com.akihabara.market.dao.ProductoDAO;
import com.akihabara.market.model.ProductoOtaku;
import com.akihabara.market.view.InterfazConsola;

import java.util.List;

// Esta clase es el controlador del programa (coordina vista + modelo)
public class Main {
    public static void main(String[] args) {
        InterfazConsola vista = new InterfazConsola();
        ProductoDAO dao = new ProductoDAO();

        int opcion;
        do {
            vista.mostrarMenu();
            opcion = vista.leerOpcion();

            switch (opcion) {
                case 1: // Añadir
                    String nombre = vista.pedirTexto("nombre");
                    String categoria = vista.pedirTexto("categoría");
                    double precio = vista.pedirPrecio();
                    int stock = vista.pedirStock();
                    ProductoOtaku nuevo = new ProductoOtaku(stock, nombre, categoria, precio, stock);
                    dao.agregarProducto(nuevo);
                    break;
                case 2: // Consultar por ID
                    int idConsulta = vista.pedirId();
                    ProductoOtaku encontrado = dao.buscarPorId(idConsulta);
                    if (encontrado != null) {
                        vista.mostrarMensaje(encontrado.toString());
                    } else {
                        vista.mostrarMensaje("Producto no encontrado.");
                    }
                    break;
                case 3: // Listar todos
                    List<ProductoOtaku> todos = dao.listarTodos();
                    todos.forEach(p -> vista.mostrarMensaje(p.toString()));
                    break;
                case 4: // Buscar por nombre
                    String nomBuscado = vista.pedirTexto("nombre a buscar");
                    List<ProductoOtaku> porNombre = dao.buscarPorNombre(nomBuscado);
                    porNombre.forEach(p -> vista.mostrarMensaje(p.toString()));
                    break;
                case 5: // Buscar por categoría
                    String catBuscada = vista.pedirTexto("categoría a buscar");
                    List<ProductoOtaku> porCategoria = dao.buscarPorCategoria(catBuscada);
                    porCategoria.forEach(p -> vista.mostrarMensaje(p.toString()));
                    break;
                case 6: // Actualizar
                    int idAct = vista.pedirId();
                    ProductoOtaku productoActual = dao.buscarPorId(idAct);
                    if (productoActual != null) {
                        String nuevoNombre = vista.pedirTexto("nuevo nombre");
                        String nuevaCategoria = vista.pedirTexto("nueva categoría");
                        double nuevoPrecio = vista.pedirPrecio();
                        int nuevoStock = vista.pedirStock();
                        productoActual.setNombre(nuevoNombre);
                        productoActual.setCategoria(nuevaCategoria);
                        productoActual.setPrecio(nuevoPrecio);
                        productoActual.setStock(nuevoStock);
                        dao.actualizarProducto(productoActual);
                    } else {
                        vista.mostrarMensaje("Producto no encontrado.");
                    }
                    break;
                case 7: // Eliminar
                    int idDel = vista.pedirId();
                    dao.eliminarProducto(idDel);
                    break;
                case 8: // Salir
                    vista.mostrarMensaje("¡Hasta luego!");
                    break;
                default:
                    vista.mostrarMensaje("Opción inválida.");
                    break;
            }
        } while (opcion != 8);
    }
}
