package com.softtek.ECommerce.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Getter
public abstract class Persistente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name="bajaLogica")
    private Boolean bajaLogica;

    @Setter
    @Column(name="fechaCreacion", columnDefinition = "DATE")
    private LocalDate fechaCreacion;

    @Setter
    @Column(name="fechaModificacion", columnDefinition = "DATE")
    private LocalDate fechaModificacion;
}
