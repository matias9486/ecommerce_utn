package com.softtek.ECommerce.model.dto;

import com.softtek.ECommerce.model.Enums.EstadoPublicacion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
@ApiModel(value = "EstadoPublicacionDTO", description = "DTO utilizado para mostrar el estado de una publicacion")
public class EstadoPublicacionDTO {
    @NotNull
    //@ApiModelProperty(value = "estado",dataType = "EstadoPublicacion" /*,example = "Diego"*/,position = 0)
    private EstadoPublicacion estado;
}
