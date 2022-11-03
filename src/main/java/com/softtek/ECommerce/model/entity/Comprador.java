package com.softtek.ECommerce.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="compradores")
public class Comprador extends Persistente{
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "direccion")
    private String direccion;
}
