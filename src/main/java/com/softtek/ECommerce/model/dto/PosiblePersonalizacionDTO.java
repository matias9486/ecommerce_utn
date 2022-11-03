package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "PosiblePersonalizacionDTO", description = "DTO utilizado para mostrar una posible personalizacion")
public class PosiblePersonalizacionDTO {
    @ApiModelProperty(value = "area",dataType = "String",example = "Frente-Arriba",position = 0)
    private String area;

    @ApiModelProperty(value = "tipo",dataType = "String",example = "Imagen",position = 1)
    private String tipo;

    @ApiModelProperty(value = "usuairo",dataType = "String",example = "Matias Alancay",position = 2)
    private String usuario;
}
