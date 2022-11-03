package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ApiModel(value = "PersonalizacionCreateDTO", description = "DTO utilizado para generar una personalizacion")
public class PersonalizacionCreateDTO {
    @NotEmpty
    @Size(min=3,message="El nombre debe contener al menos 5 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Logo UTN",position = 0)
    private String nombre;

    @NotEmpty
    @Size(min=10,message="El contenido debe contener al menos 10 caracteres.")
    @ApiModelProperty(value = "contendio",dataType = "String",example = "utn.jpg",position = 1)
    private String contenido;

    @ApiModelProperty(value = "precio",dataType = "Double",example = "200.00",position = 2)
    private Double precio;
}
