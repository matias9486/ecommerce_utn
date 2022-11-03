package com.softtek.ECommerce.adapters;

import com.softtek.ECommerce.model.dto.CompraDTO;
import org.springframework.stereotype.Component;

@Component
public class AdapterGeneradorFactura implements IAdapterGeneradorFactura {
    @Override
    public Object generarFactura(CompraDTO compra) {
        //TODO implementar metodo con un servicio que genere factura
        return "se genero factura...";
    }
}
