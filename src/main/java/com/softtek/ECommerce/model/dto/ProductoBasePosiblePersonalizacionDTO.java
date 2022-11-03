package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "ProductoBasePosiblePersonalizacionDTO", description = "DTO utilizado para que un gestor agregue una posible personalizacion a un producto base")
public class ProductoBasePosiblePersonalizacionDTO {
    @Min(1)
    @ApiModelProperty(value = "posiblePersonalizacionId",dataType = "Long",example = "1",position = 0)
    private Long posiblePersonalizacionId;
}
