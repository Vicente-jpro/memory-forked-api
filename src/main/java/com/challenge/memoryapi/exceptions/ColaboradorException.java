package com.challenge.memoryapi.exceptions;

public class ColaboradorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ColaboradorException(String erroMessage) {
        super(erroMessage);
    }
}
