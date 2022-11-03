/*
package com.softtek.ECommerce.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Entity
@Table(name="Gestor")
public class Gestor extends Persistente{
    @Setter
    @OneToOne
    @JoinColumn(name="usuarioId", referencedColumnName = "id")
    private Usuario usuario;

    @OneToMany(mappedBy = "gestor")
    private List<TipoPersonalizacion> tiposPersonalizaciones;

    @OneToMany(mappedBy = "gestor")
    private List<AreaPersonalizacion> areasPersonalizadas;

    @OneToMany(mappedBy = "gestor")
    private List<ProductoBase> productosBases;
    /*
    private List<Vendedor> clientes;
    */
/*
    public Gestor(){
        tiposPersonalizaciones=new ArrayList<>();
        areasPersonalizadas=new ArrayList<>();
        productosBases=new ArrayList<>();
        /*
        clientes=new ArrayList<>();

        */
//      }

    //metodos para colecciones Tipo de  personalizacion
  /*  public void agregarTipoPersonalizacion(TipoPersonalizacion personalizacion){
        tiposPersonalizaciones.add(personalizacion);
        personalizacion.setGestor(this); //bidireccionalidad
    }

    public void agregarTiposPersonalizaciones(TipoPersonalizacion ... personalizaciones){
        Collections.addAll(tiposPersonalizaciones, personalizaciones);
    }

    public void eliminarTipoPersonalizacion(TipoPersonalizacion personalizacion){
        tiposPersonalizaciones.remove(personalizacion);
    }


    //metodos para colecciones areas personalizadas
    public void agregarAreaPersonalizada(AreaPersonalizacion area){
        areasPersonalizadas.add(area);
        area.setGestor(this);
    }

    public void agregarAreasPersonalizadas(AreaPersonalizacion... areas){
        Collections.addAll(areasPersonalizadas, areas);
    }

    public void eliminarAreaPersonalizada(AreaPersonalizacion area){
        areasPersonalizadas.remove(area);
    }
/*
    //metodos para colecciones clientes
    public void agregarCliente(Vendedor vendedor){
        clientes.add(vendedor);
    }

    public void agregarClientes(Vendedor ... vendedores){
        Collections.addAll(clientes, vendedores);
    }

    public void eliminarCliente(Vendedor vendedor){
        clientes.remove(vendedor);
    }
*/
/*
    //metodos para colecciones productos bases
    public void agregarProductoBase(ProductoBase producto){
        productosBases.add(producto);
    }

    public void agregarProductosBases(ProductoBase ... productos){
        Collections.addAll(productosBases, productos);
    }

    public void eliminarProductoBase(ProductoBase producto){
        productosBases.remove(producto);
    }

}
*/