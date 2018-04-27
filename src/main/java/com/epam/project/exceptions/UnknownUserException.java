package com.epam.project.exceptions;

public class UnknownUserException extends Exception {
    public UnknownUserException() {
        super("Unknown user");
    }
    public UnknownUserException(String message) {
        super(message);
    }
}
