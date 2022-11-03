package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "PersonalizacionEspecificaCreateDTO", description = "DTO utilizado para que un vendedor genere una personalizacion especifica")
public class PersonalizacionEspecificaCreateDTO {
    @NotNull
    @Min(1)
    @ApiModelProperty(value = "personalizacionId",dataType = "Long",example = "1",position = 0)
    private Long personalizacionId;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "posiblePersonalizacionId",dataType = "Long",example = "1",position = 1)
    private Long posiblePersonalizacionId;
}
