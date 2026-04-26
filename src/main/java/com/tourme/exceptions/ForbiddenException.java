package com.tourme.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Access forbidden");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
