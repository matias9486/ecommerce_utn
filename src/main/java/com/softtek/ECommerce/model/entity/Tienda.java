package com.softtek.ECommerce.model.entity;


import com.softtek.ECommerce.model.Enums.MediosDePago;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
@Entity
@Table(name = "tiendas")
public class Tienda extends Persistente{
    @Setter
    @Column(name="nombre")
    private String nombre;

    @Setter
    @OneToOne
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
    private Usuario vendedor;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection(targetClass =MediosDePago.class, fetch=FetchType.LAZY)
    @Column(name="medio_pago")
    private List<MediosDePago> mediosDePago;

    @OneToMany(mappedBy = "tienda")
    private List<ProductoPersonalizado> productos;
/*
    private List<Publicacion> publicaciones;
    private List<Compra> ventas;
    //private List<Factura> facturas; no se guardan porque resultan de las compras
    private List<Pago> pagos;
*/
    public Tienda(){
        this.mediosDePago=new ArrayList<>();
        this.productos=new ArrayList<>();
        /*
        this.publicaciones=new ArrayList<>();

        this.mediosDePago=new ArrayList<>();
        this.ventas=new ArrayList<>();
        this.facturas=new ArrayList<>();
        this.pagos=new ArrayList<>();

         */
    }

    /*
    public void agregarPublicacion(Publicacion publicacion){
        publicaciones.add(publicacion);
    }

    public void agregarPublicaciones(Publicacion ... publicaciones){
        Collections.addAll(this.publicaciones,publicaciones);
    }

    public void eliminarPublicacion(Publicacion publicacion){
        //TODO
    }

    //TODO otros metodos de las listas
*/
    public void agregarMedioPago(MediosDePago medioDePago){
        this.mediosDePago.add(medioDePago);
    }

    public void eliminarMedioPago(Long medioDePago){
        this.mediosDePago.removeIf(aux -> aux.ordinal() == medioDePago);
    }

    public boolean existeMedioPago(MediosDePago medioDePago){
        return this.mediosDePago.stream().anyMatch(aux -> medioDePago == aux);
    }

    public void agregarProductoPersonalizado(ProductoPersonalizado p){
        this.productos.add(p);
        p.setTienda(this);
    }
}
