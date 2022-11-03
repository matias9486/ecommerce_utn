package com.softtek.ECommerce.model.entity;

import com.softtek.ECommerce.adapters.IAdapterGeneradorFactura;
import com.softtek.ECommerce.model.Enums.EstadoCompra;
import com.softtek.ECommerce.model.Enums.MediosDePago;
import com.softtek.ECommerce.model.dto.CompraDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="compras")
public class Compra extends Persistente{

    @OneToOne
    @JoinColumn(name = "comprador_id",referencedColumnName = "id")
    private Comprador comprador;

    @OneToOne
    @JoinColumn(name = "carro_compra_id",referencedColumnName = "id")
    private CarroCompras carroCompras;

    @Enumerated(EnumType.STRING)
    @Column(name="medio_pago")
    private MediosDePago medioDePago;

    @Column(name = "estado")
    private EstadoCompra estado;

    public Object generarFactura(CompraDTO compra, IAdapterGeneradorFactura adapter){
        return adapter.generarFactura(compra);
    }
}
