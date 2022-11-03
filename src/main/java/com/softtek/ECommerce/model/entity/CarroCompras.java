package com.softtek.ECommerce.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Entity
@Table(name="carro_compras")
public class CarroCompras extends Persistente{

    @OneToMany(mappedBy = "carroCompras")
    private List<Item> items;

    public CarroCompras(){
        this.items=new ArrayList<>();
    }

    public void agregarItemACarro(Item i){
        items.add(i);
        i.setCarroCompras(this);
    }

    public boolean existeItem(Item i){
        return items.stream().anyMatch(aux -> i.getPublicacion().getId() == aux.getPublicacion().getId());
    }

    public boolean comprobarMismaTienda(Publicacion p){
        Long tiendaId;
        if(!items.isEmpty()){
            tiendaId=items.get(0).getPublicacion().getTienda().getId();
            if(p.getTienda().getId()==tiendaId) {
                return true;
            }
        }
        return false;
    }

    public Double calcularTotalCompra(){
        Double total=0.00;
        for (Item i: items) {
            //total=total+ i.getPublicacion().getProducto().calcularPrecio()* i.getCantidad();
            total=total+ i.calcularTotal();
        }
        return  total;
    }
}
