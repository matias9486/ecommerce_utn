package com.softtek.ECommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceInvalidException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String nombreDelRecurso;
    private String nombreDelCampo;
    private String valorDelCampo;

    public ResourceInvalidException(String nombreDelRecurso, String nombreDelCampo, String valorDelCampo) {
        super(String.format("%s con %s : '%s' ya existe.", nombreDelRecurso, nombreDelCampo, valorDelCampo));
        this.nombreDelRecurso = nombreDelRecurso;
        this.nombreDelCampo = nombreDelCampo;
        this.valorDelCampo = valorDelCampo;
    }

    public ResourceInvalidException(String nombreDelRecurso) {
        super(String.format("%s ya existe.", nombreDelRecurso));
        this.nombreDelRecurso = nombreDelRecurso;
    }

}