package com.example.ventas.model;

public class Carrito {
    private int id;
    private double precio;
    private int cantidad;
    private int usuarioId;
    private int productoId;
    private Usuario usuario;
    private Producto producto;

    public Carrito() {
    }

    public Carrito(int id, double precio, int cantidad, int usuarioId, int productoId, Usuario usuario, Producto producto) {
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
        this.usuarioId = usuarioId;
        this.productoId = productoId;
        this.usuario = usuario;
        this.producto = producto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
