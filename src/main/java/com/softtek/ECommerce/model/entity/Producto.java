package com.softtek.ECommerce.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class Producto extends Persistente{
    @Column(name="nombre")
    private String nombre;

    @Column(name="precio")
    private Double precio;

    @Column(name="descripcion")
    private String descripcion;

}
