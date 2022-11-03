package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ApiModel(value = "AreaPersonalizacionCreateDTO", description = "DTO para crear un Area donde se podra aplicar una personalizacion")
public class AreaPersonalizacionCreateDTO {
    @NotEmpty
    @Size(min=3,message="El nombre debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Frente-Arriba",position = 0)
    private String nombre;
}
