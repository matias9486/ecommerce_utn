package com.softtek.ECommerce.model.entity;

import com.softtek.ECommerce.model.dto.PosiblePersonalizacionDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Entity
@Table(name="producto_base")
public class ProductoBase extends Producto{
    @Setter
    @Column(name = "tiempo_fabricacion")
    private int tiempoFabricacion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "producto_base_posibles_personalizaciones",
            joinColumns = @JoinColumn(name = "producto_base", nullable = false),
            inverseJoinColumns = @JoinColumn(name="posible_personalizacion", nullable = false)
    )
    private List<PosiblePersonalizacion> posiblesPersonalizaciones;

    @Setter
    @OneToOne
    @JoinColumn(name="usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public ProductoBase(){
        super();
        this.posiblesPersonalizaciones=new ArrayList<>();
    }

    public void agregarPersonalizaciones(PosiblePersonalizacion...posiblesPersonalizaciones){
        Collections.addAll(this.posiblesPersonalizaciones,posiblesPersonalizaciones);
    }

    public void agregarPosiblePersonalizacion(PosiblePersonalizacion personalizacion){
        this.posiblesPersonalizaciones.add(personalizacion);
    }

    public boolean comprobarExistenciaPosiblePersonalizacion(Long personalizacionId){
        /*for (PosiblePersonalizacion aux: this.posiblesPersonalizaciones) {
            if(aux.getId()==p.getId())
                return true;
        }
        return false;*/
        return this.posiblesPersonalizaciones.stream().anyMatch(aux -> aux.getId() == personalizacionId);
    }
}
