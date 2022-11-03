package com.softtek.ECommerce.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Entity
@Table(name="productos_personalizados")
public class ProductoPersonalizado extends Producto{

    @Setter
    @OneToOne
    @JoinColumn(name = "producto_base_id",referencedColumnName = "id")
    private ProductoBase productoOrigen;

    @Setter
    @OneToOne
    @JoinColumn(name = "tienda_id",referencedColumnName = "id")
    private Tienda tienda;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_personalizado_personalizacion_especifica",
            joinColumns = @JoinColumn(name = "producto_personalizado_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="personalizacion_especifica_id", nullable = false)
    )
    private List<PersonalizacionEspecifica> personalizacionesAplicadas;

    public ProductoPersonalizado(){
        this.personalizacionesAplicadas=new ArrayList<>();
    }

    //verifico que la personalizacion pertenezca a las personalizaciones disponibles en el producto base
    public boolean validarPersonalizacionEspecificaDeProductoBase(PersonalizacionEspecifica personalizacion){
        for (PosiblePersonalizacion p: productoOrigen.getPosiblesPersonalizaciones()) {
            if(p.getId()==personalizacion.getPosiblePersonalizacion().getId())
                return true;
            }
        return false;
    }

    public boolean existePersonalizacionEspecifica(PersonalizacionEspecifica personalizacion){
        for (PersonalizacionEspecifica p: this.personalizacionesAplicadas) {
            if(p.getId()==personalizacion.getPosiblePersonalizacion().getId())
                return true;
        }
        return false;
    }


    public void agregarPersonalizacionAplicada(PersonalizacionEspecifica personalizacion) {
        personalizacionesAplicadas.add(personalizacion);
    }

    public void eliminarPersonalizacionAplicada(PersonalizacionEspecifica personalizacion) {
        personalizacionesAplicadas.remove(personalizacion);
    }

    public void agregarPersonalizacionesAplicadas(PersonalizacionEspecifica... personalizaciones){
        Collections.addAll(personalizacionesAplicadas,personalizaciones);
    }

    public Double calcularPrecio(){
        double total=productoOrigen.getPrecio();
        /*for (PersonalizacionEspecifica p : this.personalizacionesAplicadas){
            total+=p.getPersonalizacion().getPrecio();
        }*/
        total += this.personalizacionesAplicadas.stream().mapToDouble(p -> p.getPersonalizacion().getPrecio()).sum();
        return total;
    }

    @Override
    public String toString() {
        return "ProductoPersonalizado{" +
                "nombre='" + this.getNombre() + '\n' +
                ", precio Base=" + productoOrigen.getPrecio() + '\n' +
                ", precio Venta=" + this.calcularPrecio() + '\n' +
                ", descripcion='" + this.getDescripcion() + '\n' +
                ", productoOrigen=" + productoOrigen +'\n' +
                ", personalizacionesAplicadas=" + personalizacionesAplicadas +
                '}';
    }
}
