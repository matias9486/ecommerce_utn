package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "TipoPersonalizacionDTO", description = "DTO utilizado para mostrar un tipo de personalizacion")
public class TipoPersonalizacionDTO {
    private Long id;

    @NotEmpty
    @Size(min=3,message="El nombre debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Imagen",position = 0)
    private String nombre;

    @ApiModelProperty(value = "usuario",dataType = "String",example = "Matias Alancay",position = 1)
    private String usuario;
}
