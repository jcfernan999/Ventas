package com.example.ventas.model;

import java.util.Date;
import java.util.List;

public class Venta {
    private int id;
    private double total;
    private Date fecha;
    private int pagoId;
    private int usuarioId;
    private Pago pago;
    private Usuario usuario;
    private List<Detalle> Detalle;
    public Venta() {
    }

    public Venta(int id, double total, Date fecha,
                 int pagoId, int usuarioId, Pago pago,
                 Usuario usuario, List<Detalle> detalle) {
        this.id = id;
        this.total = total;
        this.fecha = fecha;
        this.pagoId = pagoId;
        this.usuarioId = usuarioId;
        this.pago = pago;
        this.usuario = usuario;
        Detalle = detalle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getPagoId() {
        return pagoId;
    }

    public void setPagoId(int pagoId) {
        this.pagoId = pagoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Detalle> getDetalle() {
        return Detalle;
    }

    public void setDetalle(List<Detalle> detalle) {
        Detalle = detalle;
    }
}
