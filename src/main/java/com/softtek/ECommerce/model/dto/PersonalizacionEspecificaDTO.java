package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "PersonalizacionEspecificaDTO", description = "DTO utilizado para mostrar una personalizacion especifica")
public class PersonalizacionEspecificaDTO {
    @ApiModelProperty(value = "id",dataType = "Long",example = "1",position = 0)
    private Long id;

    //@ApiModelProperty(value = "personalizacion",dataType = "PersonalizacionDTO" /*,example = "Diego"*/,position = 1)
    private PersonalizacionDTO personalizacion;

    //@ApiModelProperty(value = "posiblePersonalizacion",dataType = "PosiblePersonalizacionDTO" /*,example = "Diego"*/,position = 2)
    private PosiblePersonalizacionDTO posiblePersonalizacion;
}
