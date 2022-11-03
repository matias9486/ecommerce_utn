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
@ApiModel(value = "ItemCreateDTO", description = "DTO utilizado para generar un item de compra. Representa la cantidad comprada de un producto")
public class ItemCreateDTO {
    @NotNull
    @Min(1)
    @ApiModelProperty(value = "cantidad",dataType = "int" ,example = "1",position = 0,notes = "Representa la cantidad")
    private int cantidad;
}
