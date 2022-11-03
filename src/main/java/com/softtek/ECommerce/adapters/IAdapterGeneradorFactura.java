package com.softtek.ECommerce.adapters;

import com.softtek.ECommerce.model.dto.CompraDTO;

public interface IAdapterGeneradorFactura {
    public Object generarFactura(CompraDTO compra);
}
