package com.softtek.ECommerce.model.dto;

import com.softtek.ECommerce.model.Enums.EstadoPublicacion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ApiModel(value = "PublicacionDTO", description = "DTO utilizado para mostrar una publicacion")
public class PublicacionDTO {
    @ApiModelProperty(value = "id",dataType = "Long",example = "1",position = 0)
    private Long id;

    @ApiModelProperty(value = "descripcion",dataType = "String",example = "Remera Blanca Lisa con logo de UTN",position = 1)
    private String descripcion;

    //@ApiModelProperty(value = "producto",dataType = "ProductoPersonalizadoDTO" /*,example = "Diego"*/,position = 2)
    private ProductoPersonalizadoDTO producto;

    //@ApiModelProperty(value = "fechaCreacion",dataType = "LocalDate" /*,example = "Diego"*/,position = 2)
    private LocalDate fechaCreacion;

    //@ApiModelProperty(value = "estadoPublicacion",dataType = "EstadoPublicacion" /*,example = "Diego"*/,position = 3)
    private EstadoPublicacion estadoPublicacion;

    //@ApiModelProperty(value = "fechaCambioEstado",dataType = "LocalDate" /*,example = "Diego"*/,position = 4)
    private LocalDate fechaCambioEstado;
}
