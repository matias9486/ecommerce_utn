package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "ItemDTO", description = "Representa un item de una compra")
public class ItemDTO {
    @ApiModelProperty(value = "id",dataType = "Long" ,example = "1",position = 0)
    private Long id;

    @ApiModelProperty(value = "cantidad",dataType = "int" ,example = "2",position = 1)
    private int cantidad;

    @ApiModelProperty(value = "producto",dataType = "String" ,example = "Remera UTN",position = 2)
    private String producto;

    @ApiModelProperty(value = "precioUnitario",dataType = "Double" ,example = "200.00",position = 3)
    private Double precioUnitario;

    @ApiModelProperty(value = "precioTotal",dataType = "Double" ,example = "400.00",position = 4)
    private Double precioTotal;

}
