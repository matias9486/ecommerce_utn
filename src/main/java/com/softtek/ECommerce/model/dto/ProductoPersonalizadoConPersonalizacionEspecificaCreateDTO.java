package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "ProductoPersonalizadoConPersonalizacionEspecificaCreateDTO", description = "DTO utilizado para que un vendedor agregue personalizacion especifica a un producto personalizado")
public class ProductoPersonalizadoConPersonalizacionEspecificaCreateDTO {
    @NotNull
    @Min(1)
    @ApiModelProperty(value = "personalizacionEspecificaId",dataType = "Long",example = "1",position = 0)
    private Long personalizacionEspecificaId;
}
