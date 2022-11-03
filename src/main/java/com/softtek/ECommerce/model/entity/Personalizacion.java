package com.softtek.ECommerce.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="personalizacion")
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class Personalizacion extends Persistente{
    private String nombre;
    private Double precio;
    private String contenido;

    @OneToOne
    @JoinColumn(name = "tienda_id",referencedColumnName = "id")
    private Tienda tienda;
}
