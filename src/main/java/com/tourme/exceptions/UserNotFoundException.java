package com.tourme.exceptions;

// Custom exception class for user not found errors
public class UserNotFoundException extends RuntimeException {

    // Default constructor with predefined message
    public UserNotFoundException() {
        super("User not found");
    }
    // Constructor that accepts a custom error message
    public UserNotFoundException(String message) {
        super(message);
    }
}
