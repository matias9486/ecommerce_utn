package com.softtek.ECommerce.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="Items")
public class Item extends Persistente{

    @Setter
    @OneToOne
    @JoinColumn(name = "publicacion_Id", referencedColumnName = "id")
    private Publicacion publicacion;

    @Setter
    @Column(name = "cantidad")
    private int cantidad;

    @Setter
    @OneToOne
    @JoinColumn(name = "carroCompra_Id", referencedColumnName = "id")
    private CarroCompras carroCompras;

    public Double calcularPrecioUnitario(){
        return publicacion.getProducto().calcularPrecio();
    }

    public Double calcularTotal(){
        return cantidad*publicacion.getProducto().calcularPrecio();
    }
}
