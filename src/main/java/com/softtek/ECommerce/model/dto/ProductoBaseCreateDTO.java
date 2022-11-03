package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "ProductoBaseCreateDTO", description = "DTO utilizado para que un gestor genere un producto base")
public class ProductoBaseCreateDTO {
    @NotEmpty
    @Size(min=3,message="El nombre debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Remera Blanca",position = 0)
    private String nombre;

    @NotEmpty
    @Size(min=10,message="La descripcion debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "descripcion",dataType = "String",example = "Remera lisa blanca, talle S",position = 1)
    private String descripcion;

    @NotNull
    @ApiModelProperty(value = "precio",dataType = "Double",example = "200.00",position = 2)
    private Double precio;

    @NotNull
    @ApiModelProperty(value = "tiempoFabricacion",dataType = "Integer",example = "5",position = 3)
    private Integer tiempoFabricacion;
}
