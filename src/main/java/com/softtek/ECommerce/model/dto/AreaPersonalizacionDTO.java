package com.softtek.ECommerce.model.dto;

import com.softtek.ECommerce.model.entity.Usuario;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "AreaPersonalizacionDTO", description = "DTO utilizado para devolver una Area creada")
public class AreaPersonalizacionDTO {
    private Long id;

    @NotEmpty
    @Size(min=3,message="El nombre debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Frente-Arriba",position = 0)
    private String nombre;

    @ApiModelProperty(value = "usuairo",dataType = "String",example = "Matias Alancay",position = 1)
    private String usuario;
}
