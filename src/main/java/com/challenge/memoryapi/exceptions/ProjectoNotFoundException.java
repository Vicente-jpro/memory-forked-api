package com.challenge.memoryapi.exceptions;

public class ProjectoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProjectoNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
