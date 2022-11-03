package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "ItemPublicacionDTO", description = "DTO utilizado para mostrar la cantidad adquirida de una publicacion")
public class ItemPublicacionDTO {
    @NotNull @Min(1)
    @ApiModelProperty(value = "publicacionId",dataType = "Long" ,example = "1",position = 0)
    private Long publicacionId;


    @NotNull @Min(1)
    @ApiModelProperty(value = "cantidad",dataType = "Long" ,example = "1",position = 1)
    private int cantidad;
}
