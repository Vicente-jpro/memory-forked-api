package com.challenge.memoryapi.exceptions;

public class FuncionarioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FuncionarioException(String errorMessage) {
        super(errorMessage);
    }

}
