package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "ProductoPersonalizadoDTO", description = "DTO utilizado para mostrar un producto personalizado")
public class ProductoPersonalizadoDTO {
    @ApiModelProperty(value = "id",dataType = "Long",example = "1",position = 0)
    private Long id;

    @ApiModelProperty(value = "nombre",dataType = "String",example = "Remera Blanca UTN",position = 1)
    private String nombre;

    @ApiModelProperty(value = "descripcion",dataType = "String",example = "Remera Blanca Lisa con logo de UTN",position = 2)
    private String descripcion;

    //@ApiModelProperty(value = "productoBase",dataType = "ProductoBaseDTO" /*,example = "Diego"*/,position = 3)
    private ProductoBaseDTO productoBase;

    @ApiModelProperty(value = "precio",dataType = "Double" ,example = "400.00",position = 4)
    private Double precio;

    //@ApiModelProperty(value = "personalizacionesAplicadas",dataType = "List" /*,example = "Diego"*/,position = 5)
    private List<PersonalizacionEspecificaDTO> personalizacionesAplicadas;
}
