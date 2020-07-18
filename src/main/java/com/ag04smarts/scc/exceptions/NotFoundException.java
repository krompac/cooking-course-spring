package com.ag04smarts.scc.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Data not found");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
