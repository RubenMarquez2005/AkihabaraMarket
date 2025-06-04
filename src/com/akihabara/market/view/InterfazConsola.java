package com.akihabara.market.view;

import java.util.Scanner;

// Esta clase es la vista: pide datos al usuario y muestra información
public class InterfazConsola {
    private Scanner sc;

    public InterfazConsola() {
        sc = new Scanner(System.in);
    }

    // Muestra el menú principal
    public void mostrarMenu() {
        System.out.println("\n--- Menú Akihabara Otaku Market ---");
        System.out.println("1. Añadir producto");
        System.out.println("2. Consultar producto por ID");
        System.out.println("3. Listar todos los productos");
        System.out.println("4. Buscar productos por nombre");
        System.out.println("5. Buscar productos por categoría");
        System.out.println("6. Actualizar producto");
        System.out.println("7. Eliminar producto");
        System.out.println("8. Salir");
        System.out.print("Elige una opción: ");
    }

    public int leerOpcion() {
        int opcion = -1;
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(" Por favor, escribe un número válido.");
        }
        return opcion;
    }

    public int pedirId() {
        System.out.print("Introduce el ID del producto: ");
        return Integer.parseInt(sc.nextLine());
    }

    public String pedirTexto(String campo) {
        System.out.print("Introduce " + campo + ": ");
        return sc.nextLine();
    }

    public double pedirPrecio() {
        System.out.print("Introduce el precio: ");
        return Double.parseDouble(sc.nextLine());
    }

    public int pedirStock() {
        System.out.print("Introduce el stock: ");
        return Integer.parseInt(sc.nextLine());
    }

    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }
}
