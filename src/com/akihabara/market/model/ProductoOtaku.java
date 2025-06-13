package com.akihabara.market.model;

public class ProductoOtaku {
    private int id;  // NUEVO campo
    private String nombre;
    private String categoria;
    private double precio;

    // Constructor vacío
    public ProductoOtaku() {
    }

    // Constructor con ID
    public ProductoOtaku(int id, String nombre, String categoria, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    // Constructor sin ID (para agregar productos nuevos)
    public ProductoOtaku(String nombre, String categoria, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método toString
    @Override
    public String toString() {
        return "ID: " + id + " | Producto: " + nombre + " | Categoría: " + categoria + " | Precio: $" + precio;
    }
}


