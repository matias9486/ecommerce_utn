package com.softtek.ECommerce.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class ErrorDetalles {
    private Date marcaDeTiempo;
    private String mensaje;
    private String detalles;
}
