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
@ApiModel(value = "PosiblePersonalizacionCreateDTO", description = "DTO utilizado para que un gestor genere una posible personalizacion")
public class PosiblePersonalizacionCreateDTO {
    @Min(1)
    @ApiModelProperty(value = "areaId",dataType = "Long" ,example = "1",position = 0)
    private Long areaId;

    @Min(1)
    @ApiModelProperty(value = "tipoId",dataType = "Long" ,example = "1",position = 1)
    private Long tipoId;

}
