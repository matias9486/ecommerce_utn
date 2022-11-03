package com.softtek.ECommerce.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Vendedor{

    @Setter
    private Usuario usuario;

    private List<ProductoPersonalizado> productosPersonalizados;
    private List<PersonalizacionEspecifica> personalizaciones;
    @Setter
    private Tienda tienda;
    @Setter
    private Usuario gestor;

    public Vendedor(){
        productosPersonalizados=new ArrayList<>();
        personalizaciones=new ArrayList<>();
    }

    public void agregarProductoPersonalizado(ProductoPersonalizado producto){
        productosPersonalizados.add(producto);
    }

    public void agregarProductosPersonalizados(ProductoPersonalizado ...productos){
        Collections.addAll(productosPersonalizados, productos);
    }

    public void eliminarProductoPersonalizado(ProductoPersonalizado producto){
        productosPersonalizados.remove(producto);
    }

    public void agregarPersonalizacionAplicada(PersonalizacionEspecifica personalizacion){
        personalizaciones.add(personalizacion);
    }

    public void agregarPersonalizacionesAplicadas(PersonalizacionEspecifica...personalizaciones){
        Collections.addAll(this.personalizaciones, personalizaciones);
    }

    public void eliminarPersonalizacionAplicada(PersonalizacionEspecifica personalizacion){
        personalizaciones.remove(personalizacion);
    }

}
