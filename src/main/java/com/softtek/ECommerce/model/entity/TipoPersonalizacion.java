package com.softtek.ECommerce.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "tipo_personalizacion")
public class TipoPersonalizacion extends Persistente{

    @Column(name="nombre")
    private String nombre;

    @OneToOne
    @JoinColumn(name="usuario_id", referencedColumnName = "id")
    private Usuario usuario;

}
