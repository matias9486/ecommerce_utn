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
@AllArgsConstructor @NoArgsConstructor
@Builder
@ApiModel(value = "PublicacionCreateDTO", description = "DTO utilizado para que un vendedor genere una publicacion")
public class PublicacionCreateDTO {
    @NotEmpty
    @Size(min=10,message="La descripcion debe contener al menos 10 caracteres.")
    @ApiModelProperty(value = "descripcion",dataType = "String",example = "Remera Blanca Lisa con logo de UTN",position = 0)
    private String descripcion;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "productoId",dataType = "Long",example = "1",position = 0)
    private Long productoId;

}
