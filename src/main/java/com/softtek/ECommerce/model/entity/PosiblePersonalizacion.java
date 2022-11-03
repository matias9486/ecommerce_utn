package com.softtek.ECommerce.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="posible_personalizacion")
@Setter @Getter
public class PosiblePersonalizacion extends Persistente{
    @ManyToOne
    @JoinColumn(name = "area_personalizacion",referencedColumnName = "id")
    private AreaPersonalizacion area;

    @ManyToOne
    @JoinColumn(name = "tipo_personalizacion",referencedColumnName = "id")
    private TipoPersonalizacion tipo;

    @OneToOne
    @JoinColumn(name="usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}
