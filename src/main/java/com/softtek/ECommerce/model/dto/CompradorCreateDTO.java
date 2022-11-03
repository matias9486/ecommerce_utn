package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "CompradorCreateDTO", description = "DTO utilizado para generar el comprador")
public class CompradorCreateDTO {

    @NotEmpty
    @Size(min=3,message="El nombre debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Diego",position = 0)
    private String nombre;

    @NotEmpty
    @Size(min=3,message="El apellido debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "apellido",dataType = "String",example = "Perez",position = 1)
    private String apellido;

    @NotEmpty
    @Size(min=3,message="El telefono debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "telefono",dataType = "String",example = "2235943355",position = 2)
    private String telefono;

    @NotEmpty
    @Size(min=3,message="La dirección debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "direccion",dataType = "String",example = "Independencia 123",position = 3)
    private String direccion;
}
