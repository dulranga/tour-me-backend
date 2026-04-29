package com.tourme.exceptions;

// Custom exception class for forbidden access errors
public class ForbiddenException extends RuntimeException {

    // Default constructor with predefined message
    public ForbiddenException() {
        super("Access forbidden");
    }

     // Constructor that accepts a custom error message
    public ForbiddenException(String message) {
        super(message);
    }
}
