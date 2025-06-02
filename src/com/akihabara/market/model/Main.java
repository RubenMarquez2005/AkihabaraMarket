package com.akihabara.market.model;

import com.akihabara.market.model.ProductoOtaku;
import com.akihabara.market.dao.DatabaseConnection;
import com.akihabara.market.dao.ProductoDAO;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) {
        // Creamos el objeto DAO que usaremos para todas las operaciones en la BBDD
        ProductoDAO productoDAO = new ProductoDAO();

        // Scanner para leer datos por consola
        Scanner sc = new Scanner(System.in);

        // Variable para controlar el menú
        boolean salir = false;

        // Bucle del menú principal
        while (!salir) {
            System.out.println("\n=== Menú AkihabaraMarket ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Mostrar producto por ID");
            System.out.println("3. Mostrar todos los productos");
            System.out.println("4. Actualizar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Buscar productos por nombre");
            System.out.println("7. Buscar productos por categoría");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();  // Consumir salto de línea

            switch (opcion) {
                case 1:
                    // Pedimos datos para agregar producto nuevo
                    System.out.println("Agregar nuevo producto");
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Categoría: ");
                    String categoria = sc.nextLine();
                    System.out.print("Precio: ");
                    double precio = sc.nextDouble();
                    System.out.print("Stock: ");
                    int stock = sc.nextInt();
                    sc.nextLine();

                    // Creamos objeto producto con id 0 porque es autoincremental en BBDD
                    ProductoOtaku nuevoProducto = new ProductoOtaku(0, nombre, categoria, precio, stock);

                    // Llamamos al método DAO para agregarlo
                    productoDAO.agregarProducto(nuevoProducto);
                    System.out.println("Producto agregado correctamente.");
                    break;

                case 2:
                    // Mostrar producto por id
                    System.out.print("Introduce el ID del producto: ");
                    int idBuscar = sc.nextInt();
                    sc.nextLine();

                    ProductoOtaku prodBuscado = productoDAO.obtenerProductoPorId(idBuscar);
                    if (prodBuscado != null) {
                        System.out.println("Producto encontrado:");
                        System.out.println(prodBuscado);
                    } else {
                        System.out.println("No existe producto con ese ID.");
                    }
                    break;

                case 3:
                    // Mostrar todos los productos
                    List<ProductoOtaku> listaProductos = productoDAO.obtenerTodosLosProductos();
                    System.out.println("Lista de todos los productos:");
                    for (ProductoOtaku p : listaProductos) {
                        System.out.println(p);
                    }
                    break;

                case 4:
                    // Actualizar producto
                    System.out.print("Introduce el ID del producto a actualizar: ");
                    int idActualizar = sc.nextInt();
                    sc.nextLine();

                    ProductoOtaku productoActualizar = productoDAO.obtenerProductoPorId(idActualizar);
                    if (productoActualizar != null) {
                        // Pedimos nuevos datos
                        System.out.print("Nuevo nombre (" + productoActualizar.getNombre() + "): ");
                        String nuevoNombre = sc.nextLine();
                        System.out.print("Nueva categoría (" + productoActualizar.getCategoria() + "): ");
                        String nuevaCategoria = sc.nextLine();
                        System.out.print("Nuevo precio (" + productoActualizar.getPrecio() + "): ");
                        double nuevoPrecio = sc.nextDouble();
                        System.out.print("Nuevo stock (" + productoActualizar.getStock() + "): ");
                        int nuevoStock = sc.nextInt();
                        sc.nextLine();

                        // Actualizamos el objeto
                        productoActualizar.setNombre(nuevoNombre);
                        productoActualizar.setCategoria(nuevaCategoria);
                        productoActualizar.setPrecio(nuevoPrecio);
                        productoActualizar.setStock(nuevoStock);

                        // Guardamos cambios en BBDD
                        boolean actualizado = productoDAO.actualizarProducto(productoActualizar);
                        if (actualizado) {
                            System.out.println("Producto actualizado con éxito.");
                        } else {
                            System.out.println("Error al actualizar producto.");
                        }
                    } else {
                        System.out.println("No existe producto con ese ID.");
                    }
                    break;

                case 5:
                    // Eliminar producto
                    System.out.print("Introduce el ID del producto a eliminar: ");
                    int idEliminar = sc.nextInt();
                    sc.nextLine();

                    boolean eliminado = productoDAO.eliminarProducto(idEliminar);
                    if (eliminado) {
                        System.out.println("Producto eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar el producto (quizá no existe).");
                    }
                    break;

                case 6:
                    // Buscar productos por nombre
                    System.out.print("Introduce nombre para buscar: ");
                    String nombreBuscar = sc.nextLine();

                    List<ProductoOtaku> encontradosPorNombre = productoDAO.buscarProductosPorNombre(nombreBuscar);
                    if (encontradosPorNombre.isEmpty()) {
                        System.out.println("No se encontraron productos con ese nombre.");
                    } else {
                        System.out.println("Productos encontrados:");
                        for (ProductoOtaku p : encontradosPorNombre) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 7:
                    // Buscar productos por categoría
                    System.out.print("Introduce categoría para buscar: ");
                    String categoriaBuscar = sc.nextLine();

                    List<ProductoOtaku> encontradosPorCategoria = productoDAO.buscarProductoPorCategoria(categoriaBuscar);
                    if (encontradosPorCategoria.isEmpty()) {
                        System.out.println("No se encontraron productos en esa categoría.");
                    } else {
                        System.out.println("Productos encontrados:");
                        for (ProductoOtaku p : encontradosPorCategoria) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 0:
                    // Salir del programa
                    System.out.println("Saliendo del programa... ¡Gracias por usar AkihabaraMarket!");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida, prueba otra vez.");
            }
        }

        sc.close();  // Cerramos el scanner al final
    }
}
