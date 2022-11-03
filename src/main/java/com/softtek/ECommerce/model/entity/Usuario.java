package com.softtek.ECommerce.model.entity;

import com.softtek.ECommerce.model.Enums.TipoRol;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Entity
@Table(name = "usuario")
public class Usuario extends Persistente{
    @NotBlank
    @Column(name="nombre")
    private String nombre;

    @NotBlank
    @Column(name="apellido")
    private String apellido;

    @NotBlank
    @Column(name="nombre_usuario")
    private String nombreUsuario;

    @NotBlank
    @Column(name="email")
    private String email;

    @NotBlank
    @Column(name="password")
    private String password;

    //@NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name="tipoRol")
    private TipoRol tipoRol;
}
