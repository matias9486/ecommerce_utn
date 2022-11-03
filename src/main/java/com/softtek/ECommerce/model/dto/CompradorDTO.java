package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "CompradorDTO", description = "DTO utilizado para mostrar los datos del comprador")
public class CompradorDTO {

    @ApiModelProperty(value = "id",dataType = "Long",example = "1",position = 0)
    private Long id;

    @ApiModelProperty(value = "nombre",dataType = "String",example = "Diego",position = 1)
    private String nombre;
    @ApiModelProperty(value = "apellido",dataType = "String",example = "Perez",position = 2)
    private String apellido;
    @ApiModelProperty(value = "telefono",dataType = "String",example = "2235943355",position = 3)
    private String telefono;
    @ApiModelProperty(value = "direccion",dataType = "String",example = "Independencia 123",position = 4)
    private String direccion;
}
