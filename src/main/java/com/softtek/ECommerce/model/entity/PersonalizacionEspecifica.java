package com.softtek.ECommerce.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "personalizacion_especifica")
public class PersonalizacionEspecifica extends Persistente{
    @ManyToOne
    @JoinColumn(name = "personalizacion_id",referencedColumnName = "id")
    private Personalizacion personalizacion;

    @ManyToOne
    @JoinColumn(name = "posible_personalizacion_id",referencedColumnName = "id")
    private PosiblePersonalizacion posiblePersonalizacion;

    @ManyToOne
    @JoinColumn(name = "tienda_id",referencedColumnName = "id")
    private Tienda tienda;
}
