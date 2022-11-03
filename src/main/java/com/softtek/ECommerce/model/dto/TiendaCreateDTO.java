package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ApiModel(value = "TiendaCreateDTO", description = "DTO utilizado para que un vendedor genere una tienda")
public class TiendaCreateDTO {
    @NotEmpty
    @Size(min=3,message="El nombre debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Mi Tienda",position = 0)
    private String nombre;
}
