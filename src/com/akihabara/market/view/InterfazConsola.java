package com.akihabara.market.view;

import java.util.Scanner;

public class InterfazConsola {
    private Scanner scanner;

    public InterfazConsola() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Añadir producto");
        System.out.println("2. Consultar producto por ID");
        System.out.println("3. Listar todos los productos");
        System.out.println("4. Listar productos por nombre");
        System.out.println("5. Listar productos por categoría");
        System.out.println("6. Actualizar producto");
        System.out.println("7. Eliminar producto");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public int leerOpcion() {
        int opcion = -1;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠ Entrada inválida. Ingrese un número.");
        }
        return opcion;
    }

    public String pedirTexto(String mensaje) {
        System.out.print(mensaje + ": ");
        return scanner.nextLine();
    }

    public double pedirPrecio() {
        System.out.print("Ingrese el precio: ");
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠ Precio inválido.");
            return -1;
        }
    }

    public int pedirStock() {
        System.out.print("Ingrese el stock: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠ Stock inválido.");
            return -1;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarProducto(String producto) {
        System.out.println(">> " + producto);
    }
}
