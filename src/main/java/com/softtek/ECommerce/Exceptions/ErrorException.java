package com.softtek.ECommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ErrorException(String error) {
        super(String.format("%s", error));
    }

}