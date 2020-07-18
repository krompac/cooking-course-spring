package com.ag04smarts.scc.exceptions;

public class NotValidException extends RuntimeException {
    public NotValidException() {
        super("You didn't entered valid data");
    }

    public NotValidException(String message) {
        super(message);
    }
}
