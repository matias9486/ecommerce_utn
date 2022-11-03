package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "ProductoPersonalizadoCreateDTO", description = "DTO utilizado para que un vendedor genere un producto personalizado")
public class ProductoPersonalizadoCreateDTO {
    @NotNull
    @Min(1)
    @ApiModelProperty(value = "productoBaseId",dataType = "Long",example = "1",position = 0)
    private Long productoBaseId;

    @NotEmpty
    @Size(min=3,message="El nombre debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Remera Blanca UTN",position = 1)
    private String nombre;

    @NotEmpty
    @Size(min=3,message="Descripcion debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "descripcion",dataType = "String",example = "Remera Blanca Lisa con logo de UTN",position = 3)
    private String descripcion;
}
