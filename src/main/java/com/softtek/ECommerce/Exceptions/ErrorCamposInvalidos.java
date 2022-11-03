package com.softtek.ECommerce.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCamposInvalidos {
    private Date marcaDeTiempo;
    private String mensaje;
    private Map<String,String> detalles;
}
