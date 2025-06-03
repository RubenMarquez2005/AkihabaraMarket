package com.akihabara.market.model;

import com.akihabara.market.dao.ProductoDAO;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductoDAO productoDAO = new ProductoDAO();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Menú AkihabaraMarket ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Buscar producto por ID");
            System.out.println("4. Actualizar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Buscar productos por nombre");
            System.out.println("7. Buscar productos por categoría");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            int opcion = 0;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingresa un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del producto: ");
                    String nombre = sc.nextLine();

                    System.out.print("Categoría del producto: ");
                    String categoria = sc.nextLine();

                    System.out.print("Precio: ");
                    double precio = 0;
                    try {
                        precio = Double.parseDouble(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Precio inválido.");
                        break;
                    }

                    System.out.print("Stock: ");
                    int stock = 0;
                    try {
                        stock = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Stock inválido.");
                        break;
                    }

                    ProductoOtaku nuevoProducto = new ProductoOtaku(0, nombre, categoria, precio, stock);
                    boolean agregado = productoDAO.agregarProducto(nuevoProducto);
                    if (agregado) {
                        System.out.println("Producto agregado correctamente.");
                    } else {
                        System.out.println("Error al agregar el producto.");
                    }
                    break;

                case 2:
                    List<ProductoOtaku> lista = productoDAO.obtenerTodosLosProductos();
                    System.out.println("\n=== Lista de productos ===");
                    for (ProductoOtaku p : lista) {
                        System.out.println(p);
                    }
                    break;

                case 3:
                    System.out.print("ID del producto: ");
                    int idBuscar = 0;
                    try {
                        idBuscar = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido.");
                        break;
                    }
                    ProductoOtaku productoBuscado = productoDAO.obtenerProductoPorId(idBuscar);
                    if (productoBuscado != null) {
                        System.out.println(productoBuscado);
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 4:
                    System.out.print("ID del producto a actualizar: ");
                    int idActualizar = 0;
                    try {
                        idActualizar = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido.");
                        break;
                    }
                    ProductoOtaku productoActualizar = productoDAO.obtenerProductoPorId(idActualizar);
                    if (productoActualizar == null) {
                        System.out.println("Producto no encontrado.");
                        break;
                    }

                    System.out.print("Nuevo nombre (" + productoActualizar.getNombre() + "): ");
                    String nuevoNombre = sc.nextLine();
                    if (!nuevoNombre.trim().isEmpty()) {
                        productoActualizar.setNombre(nuevoNombre);
                    }

                    System.out.print("Nueva categoría (" + productoActualizar.getCategoria() + "): ");
                    String nuevaCategoria = sc.nextLine();
                    if (!nuevaCategoria.trim().isEmpty()) {
                        productoActualizar.setCategoria(nuevaCategoria);
                    }

                    System.out.print("Nuevo precio (" + productoActualizar.getPrecio() + "): ");
                    String precioStr = sc.nextLine();
                    if (!precioStr.trim().isEmpty()) {
                        try {
                            productoActualizar.setPrecio(Double.parseDouble(precioStr));
                        } catch (NumberFormatException e) {
                            System.out.println("Precio inválido, se mantiene el anterior.");
                        }
                    }

                    System.out.print("Nuevo stock (" + productoActualizar.getStock() + "): ");
                    String stockStr = sc.nextLine();
                    if (!stockStr.trim().isEmpty()) {
                        try {
                            productoActualizar.setStock(Integer.parseInt(stockStr));
                        } catch (NumberFormatException e) {
                            System.out.println("Stock inválido, se mantiene el anterior.");
                        }
                    }

                    boolean actualizado = productoDAO.actualizarProducto(productoActualizar);
                    if (actualizado) {
                        System.out.println("Producto actualizado correctamente.");
                    } else {
                        System.out.println("Error al actualizar el producto.");
                    }
                    break;

                case 5:
                    System.out.print("ID del producto a eliminar: ");
                    int idEliminar = 0;
                    try {
                        idEliminar = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido.");
                        break;
                    }
                    boolean eliminado = productoDAO.eliminarProducto(idEliminar);
                    if (eliminado) {
                        System.out.println("Producto eliminado correctamente.");
                    } else {
                        System.out.println("Error al eliminar el producto.");
                    }
                    break;

                case 6:
                    System.out.print("Buscar productos por nombre (texto): ");
                    String textoNombre = sc.nextLine();
                    List<ProductoOtaku> encontradosNombre = productoDAO.buscarProductosPorNombre(textoNombre);
                    if (encontradosNombre.isEmpty()) {
                        System.out.println("No se encontraron productos con ese nombre.");
                    } else {
                        for (ProductoOtaku p : encontradosNombre) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 7:
                    System.out.print("Buscar productos por categoría (texto): ");
                    String textoCategoria = sc.nextLine();
                    List<ProductoOtaku> encontradosCategoria = productoDAO.buscarProductoPorCategoria(textoCategoria);
                    if (encontradosCategoria.isEmpty()) {
                        System.out.println("No se encontraron productos en esa categoría.");
                    } else {
                        for (ProductoOtaku p : encontradosCategoria) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 0:
                    salir = true;
                    System.out.println("Gracias por usar AkihabaraMarket. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        sc.close();
    }
}
