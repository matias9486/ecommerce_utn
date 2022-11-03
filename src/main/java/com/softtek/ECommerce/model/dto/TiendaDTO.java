package com.softtek.ECommerce.model.dto;

import com.softtek.ECommerce.model.Enums.MediosDePago;
import com.softtek.ECommerce.model.entity.ProductoPersonalizado;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@AllArgsConstructor
@Builder
@ApiModel(value = "TiendaDTO", description = "DTO utilizado para mostrar una tienda")
public class TiendaDTO {
    @ApiModelProperty(value = "id",dataType = "Long.class",example = "1",position = 0)
    private Long id;

    @ApiModelProperty(value = "nombre",dataType = "String.class",example = "Mi Tienda",position = 1)
    private String nombre;

    @ApiModelProperty(value = "vendedor",dataType = "String.class",example = "Apu Nahasapametilan",position = 2)
    private String vendedor;

    //@ApiModelProperty(value = "mediosPago",dataType = "List" /*,example = "Diego"*/,position = 3)
    private List<MediosDePago> mediosPago;

    //@ApiModelProperty(value = "productos",dataType = "List" /*,example = "Diego"*/,position = 4)
    private List<ProductoPersonalizadoDTO> productos;

    public TiendaDTO(){
        this.mediosPago=new ArrayList<>();
        this.productos=new ArrayList<>();
    }

}
