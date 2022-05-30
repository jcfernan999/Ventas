package com.example.ventas.model;

public class Detalle {
    private int id;
    private int cantidad;
    private double precio;
    private int productoId;
    private int ventaId;
    private Producto producto;
    private Venta venta;

    public Detalle() {
    }

    public Detalle(int id, int cantidad, double precio,
                   int productoId, int ventaId,
                   Producto producto, Venta venta) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.productoId = productoId;
        this.ventaId = ventaId;
        this.producto = producto;
        this.venta = venta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
