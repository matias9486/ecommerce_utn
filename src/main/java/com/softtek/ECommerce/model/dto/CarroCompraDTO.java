package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ApiModel(value = "CarroCompraDTO", description = "DTO Carro de compra, que contiene los items comprados")
public class CarroCompraDTO {
    @ApiModelProperty(value = "id",dataType = "Long",example = "1",position = 0)
    private Long id;

    //@ApiModelProperty(dataType="List", value = "items",position=1)
    private List<ItemDTO> items;

    public CarroCompraDTO(){
        items=new ArrayList<>();
    }
}
