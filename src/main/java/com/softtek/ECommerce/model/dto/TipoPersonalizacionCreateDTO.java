package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "TipoPersonalizacionCreateDTO", description = "DTO utilizado para que un gestor genere un tipo de personalizacion")
public class TipoPersonalizacionCreateDTO {
    @NotEmpty
    @Size(min=3,message="El nombre debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Imagen",position = 0)
    private String nombre;
}
