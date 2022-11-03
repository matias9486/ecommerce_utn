package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "CompraDTO", description = "DTO utilizado para mostrar datos de la compra")
public class CompraDTO {
    @ApiModelProperty(value = "id",dataType = "Long",example = "1",position = 0)
    private Long id;

    //@ApiModelProperty(value = "fecha",dataType = "LocalDate" /*,example = "Diego"*/,position = 1)
    private LocalDate fecha;

    //@ApiModelProperty(value = "comprador",dataType = "CompradorDTO" /*,example = "Diego"*/,position = 2)
    private CompradorDTO comprador;

    @ApiModelProperty(value = "estado",dataType = "String",example = "Activa",position = 3)
    private String estado;

    @ApiModelProperty(value = "medioPago",dataType = "String",example = "TARJETA_DE_CREDITO",position = 4)
    private String medioPago;

    //@ApiModelProperty(value = "items",dataType = "List" /*,example = "Diego"*/,position = 5)
    private List<ItemDTO> items;

    @ApiModelProperty(value = "total",dataType = "Double",example = "1000.00",position = 6)
    private Double total;
}
