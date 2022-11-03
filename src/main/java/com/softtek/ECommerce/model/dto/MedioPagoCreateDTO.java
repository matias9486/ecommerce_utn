package com.softtek.ECommerce.model.dto;

import com.softtek.ECommerce.model.Enums.MediosDePago;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "MedioPagoCreateDTO", description = "DTO utilizado para agregar un medio de Pago")
public class MedioPagoCreateDTO {
    //@ApiModelProperty(value = "medioPago",dataType = "MediosDePago" ,example = "TARJETA_DE_CREDITO",position = 0)
    private MediosDePago medioPago;
}
