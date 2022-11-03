package com.softtek.ECommerce.model.entity;

import com.softtek.ECommerce.model.Enums.EstadoPublicacion;
import com.softtek.ECommerce.model.Enums.MediosDePago;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name="publicacion")
public class Publicacion extends Persistente{

    @Column(name = "descripcion")
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "tienda_id",referencedColumnName = "id")
    private Tienda tienda;

    @OneToOne
    @JoinColumn(name = "producto_personalizado_id",referencedColumnName = "id")
    private ProductoPersonalizado producto;

    @Column(name="fecha_publicacion", columnDefinition = "DATE")
    private LocalDate fechaPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(name="estado_publicacion")
    private EstadoPublicacion estado;

    @Column(name="fecha_cambio_estado", columnDefinition = "DATE")
    private LocalDate fecha_cambio_estado;

    @Override
    public String toString() {
        return "Publicacion{" +
                ", tienda=" + tienda +
                ", producto=" + producto +
                ", fechaPublicacion=" + fechaPublicacion +
                ", estado=" + estado +
                '}';
    }
}
