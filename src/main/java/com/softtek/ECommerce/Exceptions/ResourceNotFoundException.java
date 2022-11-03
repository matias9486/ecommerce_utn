package com.softtek.ECommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;

public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID=1L;

    @Getter @Setter
    private String nombreDelRecurso;
    @Getter @Setter
    private String nombreDelCampo;
    @Getter @Setter
    private Long valorDelCampo;

    public ResourceNotFoundException(String nombreDelRecurso,String nombreDelCampo,Long valorDelCampo){
        super(String.format("%s no encontrado con : %s : '%s' ",nombreDelRecurso,nombreDelCampo,valorDelCampo));
        this.nombreDelRecurso=nombreDelRecurso;
        this.nombreDelCampo=nombreDelCampo;
        this.valorDelCampo=valorDelCampo;
    }

    public ResourceNotFoundException(String nombreDelRecurso,String nombreDelCampo){
        super(String.format("%s no encontrado con : %s",nombreDelRecurso,nombreDelCampo));
        this.nombreDelRecurso=nombreDelRecurso;
        this.nombreDelCampo=nombreDelCampo;
    }



}
